package cz.upce.vetalmael.main.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import cz.upce.vetalmael.MainNavigationDirections
import cz.upce.vetalmael.R
import cz.upce.vetalmael.data.source.application.ApplicationRepository
import cz.upce.vetalmael.extensions.hideKeyboard
import cz.upce.vetalmael.extensions.setVisibleOrGone
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.fragment.android.setupKoinFragmentFactory

class MainActivity : AppCompatActivity(R.layout.activity_main),
    NavController.OnDestinationChangedListener {

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
        }
    }

    private val applicationRepository: ApplicationRepository by inject()

    private val navController: NavController
        get() = navHostFragment.findNavController()

    private val publicSectionIds = setOf(
        R.id.login,
        R.id.registration
    )

    private val destinationsWithBottomNavigation: Set<Int> = setOf(
        R.id.animals,
        R.id.reservations,
        R.id.user
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_VetAlmael)
        setupKoinFragmentFactory()
        super.onCreate(savedInstanceState)

        navController.addOnDestinationChangedListener(this)
        bottomNavigationView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    // region  NavController.OnDestinationChangedListener.

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        val isLoggedIn = applicationRepository.getAccessToken() != null
        val requiresAuthentication = requiresAuthentication(destination.id)

        if (requiresAuthentication && !isLoggedIn) {
            // We need to hide the bottom navigation view explicitly because
            // this listener won't get called for conditional navigation.
            bottomNavigationView.visibility = View.GONE
            controller.navigate(MainNavigationDirections.actionGlobalLogin())

            // We don't want to run through the rest of this method as we are navigating away.
            return
        } else if (!requiresAuthentication && isLoggedIn) {
            // Do not allow logged in user into public sections.
            controller.navigateUp()
            return
        }

        val showBottomNavigation = destinationsWithBottomNavigation.contains(destination.id)
        bottomNavigationView.setVisibleOrGone(showBottomNavigation)

        hideKeyboard()
    }

    // endregion.

    private fun requiresAuthentication(@IdRes destinationId: Int): Boolean {
        return !publicSectionIds.contains(destinationId)
    }
}