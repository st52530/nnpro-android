package cz.upce.vetalmael.login.view

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.text.buildSpannedString
import androidx.core.text.underline
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import cz.upce.vetalmael.R
import cz.upce.vetalmael.core.viewmodel.data.Event
import cz.upce.vetalmael.extensions.setVisibleOrInvisible
import cz.upce.vetalmael.login.viewmodel.data.LoginEvent
import cz.upce.vetalmael.login.viewmodel.data.LoginViewData
import cz.upce.vetalmael.login.viewmodel.LoginViewModel
import cz.upce.vetalmael.login.viewmodel.data.FinishLogin
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.passwordEditText
import kotlinx.android.synthetic.main.fragment_login.passwordTextLayout
import kotlinx.android.synthetic.main.fragment_login.progressBar
import kotlinx.android.synthetic.main.fragment_login.registerButton
import kotlinx.android.synthetic.main.fragment_login.usernameEditText
import kotlinx.android.synthetic.main.fragment_login.usernameTextLayout
import kotlinx.android.synthetic.main.fragment_registration.*

class LoginFragment(
    private val viewModel: LoginViewModel
) : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        underlineRegisterNow()
        setupUiListeners()
        observeViewModel()
    }

    private fun underlineRegisterNow() {
        val text = registerButton.text.toString()
        // Underline the part after ?
        val underlineIndex = text.indexOf("?")

        registerButton.text = buildSpannedString {
            append(text.substring(0, underlineIndex + 2))
            underline {
                append(text.substring(underlineIndex + 2))
            }
        }
    }

    private fun setupUiListeners() {
        usernameEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.onTextChanged(text.toString(), passwordEditText.text.toString())
        }
        passwordEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.onTextChanged(usernameEditText.text.toString(), text.toString())
        }

        loginButton.setOnClickListener {
            viewModel.login()
        }

        passwordEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.login()
            }
            false
        }

        registerButton.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginToRegistration())
        }
    }

    private fun observeViewModel() {
        viewModel.viewData.observe(viewLifecycleOwner, ::onNext)
        viewModel.events.observe(viewLifecycleOwner, ::onEvent)
    }

    private fun onNext(viewData: LoginViewData) {
        // Text fields enabled.
        usernameTextLayout.isEnabled = viewData.textFieldsEnabled
        usernameEditText.isEnabled = viewData.textFieldsEnabled
        passwordTextLayout.isEnabled = viewData.textFieldsEnabled
        passwordEditText.isEnabled = viewData.textFieldsEnabled

        // Errors.
        if (viewData.errorMessage == null) {
            usernameTextLayout.error = null
            passwordTextLayout.error = null
        } else {
            usernameTextLayout.error = getString(R.string.space)
            passwordTextLayout.error = viewData.errorMessage
        }

        // Loading visible.
        loginButton.setVisibleOrInvisible(!viewData.isLoading)
        progressBar.setVisibleOrInvisible(viewData.isLoading)

        // Button enabled.
        loginButton.isEnabled = viewData.isButtonEnabled
    }

    private fun onEvent(event: Event<LoginEvent>) {
        when (val data = event.consume()) {
            is FinishLogin -> findNavController().navigateUp()
        }
    }
}