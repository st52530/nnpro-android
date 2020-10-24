package cz.upce.vetalmael.login.viewmodel.mapper

import cz.upce.vetalmael.R
import cz.upce.vetalmael.data.exception.UserRoleUnsupportedException
import cz.upce.vetalmael.login.viewmodel.data.LoginViewData
import cz.upce.vetalmael.login.viewmodel.data.LoginViewModelData
import cz.upce.vetalmael.utils.ResourceProvider

class LoginViewDataMapperImpl(
    private val resourceProvider: ResourceProvider
) : LoginViewDataMapper {

    override fun toViewData(data: LoginViewModelData): LoginViewData {
        return LoginViewData(
            errorMessage = getErrorMessage(data.error),
            isLoading = data.isLoading,
            textFieldsEnabled = !data.isLoading,
            isButtonEnabled = isButtonEnabled(data.username, data.password)
        )
    }

    private fun isButtonEnabled(username: String, password: String): Boolean {
        return username.isNotBlank() && password.isNotBlank()
    }

    private fun getErrorMessage(error: Throwable?): String? {
        return when {
            error is UserRoleUnsupportedException -> error.message
            error != null -> resourceProvider.getString(R.string.general_error)
            else -> null
        }
    }
}