package cz.upce.vetalmael.registration

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import cz.upce.vetalmael.MainNavigationDirections
import cz.upce.vetalmael.R
import cz.upce.vetalmael.extensions.safeSetError
import cz.upce.vetalmael.extensions.setVisibleOrInvisible
import cz.upce.vetalmael.registration.domain.RegistrationUseCase
import kotlinx.android.synthetic.main.fragment_registration.*
import timber.log.Timber
import java.util.regex.Pattern

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

        fullNameEditText.doOnTextChanged { _, _, _, _ ->
            validateInput()
        }

        emailEditText.doAfterTextChanged {
            validateInput()
        }

        usernameEditText.doOnTextChanged { _, _, _, _ ->
            validateInput()
        }

        passwordEditText.doAfterTextChanged {
            validateInput()
        }

        password2EditText.doAfterTextChanged {
            validateInput()
        }

        addressEditText.doAfterTextChanged {
            validateInput()
        }

        phoneEditText.doAfterTextChanged {
            validateInput()
        }

        registerButton.setOnClickListener {
            register()
        }
    }

    private fun validateInput() {
        var isValid = true

        if (fullNameEditText.text.isNullOrEmpty()) {
            isValid = false
        } else {
            fullNameTextLayout.safeSetError(null)
        }

        if (!isValidEmail(emailEditText.text)) {
            isValid = false
            if (emailEditText.text?.isNotEmpty() == true) {
                emailTextLayout.safeSetError(getString(R.string.email_error))
            } else {
                passwordTextLayout.safeSetError(null)
            }
        } else {
            emailTextLayout.safeSetError(null)
        }

        if (usernameEditText.text.isNullOrEmpty()) {
            isValid = false
        } else {
            usernameTextLayout.safeSetError(null)
        }

        if (passwordEditText.text.isNullOrEmpty()) {
            isValid = false
        }

        if ((passwordEditText.text?.length ?: 0) < MIN_PASSWORD_LENGTH
            && !passwordEditText.text.isNullOrEmpty()
        ) {
            isValid = false
            passwordTextLayout.safeSetError(getString(R.string.password_too_short_error))
        } else {
            passwordTextLayout.safeSetError(null)
        }

        if (password2EditText.text.isNullOrEmpty()) {
            isValid = false
        }

        if (!passwordEditText.text.isNullOrEmpty() && !password2EditText.text.isNullOrEmpty()
            && passwordEditText.text.toString() != password2EditText.text.toString()
        ) {
            isValid = false
            password2TextLayout.error = getString(R.string.different_passwords_error)
        } else {
            password2TextLayout.safeSetError(null)
        }

        if (!isValidPhone(phoneEditText.text)) {
            isValid = false
            if (phoneEditText.text?.isNotEmpty() == true) {
                phoneTextLayout.safeSetError(getString(R.string.phone_error))
            } else {
                phoneTextLayout.safeSetError(null)
            }
        } else {
            phoneTextLayout.safeSetError(null)
        }

        if (addressEditText.text.isNullOrEmpty()) {
            isValid = false
        }

        registerButton.isEnabled = isValid
    }

    private fun isValidEmail(target: CharSequence?): Boolean {
        return !target.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    private fun isValidPhone(target: CharSequence?): Boolean {
        return !target.isNullOrEmpty() && PHONE_PATTERN.matcher(target).matches()
    }

    private fun register() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            try {
                showLoading(true)
                registrationUseCase.register(
                    username = usernameEditText.text.toString(),
                    fullName = fullNameEditText.text.toString(),
                    email = emailEditText.text.toString(),
                    password = passwordEditText.text.toString(),
                    address = addressEditText.text.toString(),
                    phoneNumber = phoneEditText.text.toString()
                )

                // Use logout action to pop the whole navigation stack.
                findNavController().navigate(MainNavigationDirections.actionGlobalLogout())
            } catch (error: Throwable) {
                Timber.e(error)
                val emptyError = getString(R.string.space)
                fullNameTextLayout.error = emptyError
                emailTextLayout.error = emptyError
                usernameTextLayout.error = emptyError
                passwordTextLayout.error = emptyError
                phoneTextLayout.error = emptyError
                addressTextLayout.error = emptyError
                password2TextLayout.error = getString(R.string.general_error)
            } finally {
                showLoading(false)
            }
        }
    }

    private fun showLoading(show: Boolean) {
        registerButton.setVisibleOrInvisible(!show)
        progressBar.setVisibleOrInvisible(show)
    }

    companion object {

        private const val MIN_PASSWORD_LENGTH = 8

        private val PHONE_PATTERN = Pattern.compile( // sdd = space, dot, or dash
            "(\\+[0-9]{1,3})? ?[0-9]{3} ?[0-9]{3} ?[0-9]{3}"
        )
//        "(\\+[0-9]+[\\- \\.]*)?" // +<digits><sdd>*
//        + "(\\([0-9]+\\)[\\- \\.]*)?" // (<digits>)<sdd>*
//        + "([0-9][0-9\\- \\.]+[0-9])"

    }
}