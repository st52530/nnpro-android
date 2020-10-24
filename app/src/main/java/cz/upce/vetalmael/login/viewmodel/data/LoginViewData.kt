package cz.upce.vetalmael.login.viewmodel.data

data class LoginViewData(
    val errorMessage: String?,
    val isLoading: Boolean,
    val isButtonEnabled: Boolean,
    val textFieldsEnabled: Boolean
)