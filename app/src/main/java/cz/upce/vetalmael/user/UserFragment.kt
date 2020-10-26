package cz.upce.vetalmael.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import cz.upce.vetalmael.MainNavigationDirections
import cz.upce.vetalmael.R
import cz.upce.vetalmael.data.source.application.ApplicationRepository
import cz.upce.vetalmael.login.domain.LogoutUsecase
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment(
    private val applicationRepository: ApplicationRepository,
    private val logoutUsecase: LogoutUsecase
) : Fragment(R.layout.fragment_user) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showCurrentUserInfo()

        contentLoadinglayout.setTryAgainListener { showCurrentUserInfo() }

        logoutButton.setOnClickListener {
            logout()
        }
    }

    private fun showCurrentUserInfo() {
        val user = applicationRepository.getCurrentUser()
        if (user == null) {
            contentLoadinglayout.showError()
            return
        }

        nameTextView.text = user.fullName
        emailTextView.text = user.email
        usernameTextView.text = user.username

        contentLoadinglayout.showData()
    }

    private fun logout() {
        lifecycleScope.launchWhenStarted {
            logoutUsecase.logout()

            // Navigate out after logout.
            findNavController().navigate(MainNavigationDirections.actionGlobalLogout())
        }
    }
}