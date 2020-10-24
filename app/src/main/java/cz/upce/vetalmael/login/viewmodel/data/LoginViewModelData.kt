package cz.upce.vetalmael.login.viewmodel.data

data class LoginViewModelData(
    val isLoading: Boolean = false,
    val username: String = "",
    val password: String = "",
    val error: Throwable? = null
)