package cz.upce.vetalmael.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.upce.vetalmael.core.viewmodel.data.Event
import cz.upce.vetalmael.data.source.login.LoginRepository
import cz.upce.vetalmael.login.viewmodel.data.FinishLogin
import cz.upce.vetalmael.login.viewmodel.data.LoginEvent
import cz.upce.vetalmael.login.viewmodel.data.LoginViewData
import cz.upce.vetalmael.login.viewmodel.data.LoginViewModelData
import cz.upce.vetalmael.login.viewmodel.mapper.LoginViewDataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel(
    private val mapper: LoginViewDataMapper,
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _viewData = MutableLiveData<LoginViewData>()
    val viewData: LiveData<LoginViewData>
        get() = _viewData

    private val _events = MutableLiveData<Event<LoginEvent>>()
    val events: LiveData<Event<LoginEvent>>
        get() = _events

    private var viewModelData: LoginViewModelData = LoginViewModelData()
        set(value) {
            field = value
            _viewData.postValue(mapper.toViewData(value))
        }

    fun onTextChanged(username: String, password: String) {
        viewModelData = viewModelData.copy(
            username = username,
            password = password,
            error = null
        )
    }

    fun login() {
        if (viewModelData.username.isBlank() || viewModelData.password.isBlank()) {
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            viewModelData = viewModelData.copy(
                isLoading = true,
                error = null
            )
            try {
                loginRepository.login(
                    username = viewModelData.username,
                    password = viewModelData.password
                )
                _events.postValue(Event(FinishLogin))
            } catch (exception: Throwable) {
                Timber.e(exception)
                viewModelData = viewModelData.copy(
                    error = exception
                )
            } finally {
                viewModelData = viewModelData.copy(
                    isLoading = false
                )
            }
        }
    }
}