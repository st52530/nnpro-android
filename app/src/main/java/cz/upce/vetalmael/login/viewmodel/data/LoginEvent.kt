package cz.upce.vetalmael.login.viewmodel.data

sealed class LoginEvent

object FinishLogin : LoginEvent()