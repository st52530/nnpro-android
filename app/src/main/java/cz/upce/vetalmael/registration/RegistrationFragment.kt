package cz.upce.vetalmael.registration

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import cz.upce.vetalmael.R
import cz.upce.vetalmael.registration.domain.RegistrationUseCase
import kotlinx.android.synthetic.main.fragment_registration.*

class RegistrationFragment(
    private val registrationUseCase: RegistrationUseCase
) : Fragment(R.layout.fragment_registration) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUiListeners()
    }

    private fun setupUiListeners() {
        crossImageView.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}