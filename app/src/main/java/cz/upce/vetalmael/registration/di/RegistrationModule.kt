package cz.upce.vetalmael.registration.di

import cz.upce.vetalmael.registration.domain.RegistrationUseCase
import cz.upce.vetalmael.registration.domain.RegistrationUseCaseImpl
import org.koin.dsl.module

val registrationModule = module {

    factory<RegistrationUseCase> { RegistrationUseCaseImpl(get(), get()) }
}