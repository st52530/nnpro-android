package cz.upce.vetalmael.login.di

import cz.upce.vetalmael.login.domain.LogoutUsecase
import cz.upce.vetalmael.login.domain.LogoutUsecaseImpl
import cz.upce.vetalmael.login.viewmodel.LoginViewModel
import cz.upce.vetalmael.login.viewmodel.mapper.LoginViewDataMapper
import cz.upce.vetalmael.login.viewmodel.mapper.LoginViewDataMapperImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {

    factory<LoginViewDataMapper> { LoginViewDataMapperImpl(get()) }

    viewModel { LoginViewModel(get(), get()) }

    factory<LogoutUsecase> { LogoutUsecaseImpl(get()) }
}