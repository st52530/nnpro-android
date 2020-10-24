package cz.upce.vetalmael.data.source.di

import cz.upce.vetalmael.base.repository.ErasableRepository
import cz.upce.vetalmael.data.source.application.ApplicationRepository
import cz.upce.vetalmael.data.source.application.ApplicationRepositoryImpl
import cz.upce.vetalmael.data.source.login.LoginRepository
import cz.upce.vetalmael.data.source.login.LoginRepositoryImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val dataSourceModule = module {

    single<ApplicationRepository> { ApplicationRepositoryImpl(get()) } bind ErasableRepository::class

    single<LoginRepository> { LoginRepositoryImpl(get(), get()) }
}