package cz.upce.vetalmael.data.source.di

import cz.upce.vetalmael.base.repository.ErasableRepository
import cz.upce.vetalmael.data.source.animal.AnimalRepository
import cz.upce.vetalmael.data.source.animal.AnimalRepositoryImpl
import cz.upce.vetalmael.data.source.reservation.ReservationRepository
import cz.upce.vetalmael.data.source.reservation.ReservationRepositoryImpl
import cz.upce.vetalmael.data.source.application.ApplicationRepository
import cz.upce.vetalmael.data.source.application.ApplicationRepositoryImpl
import cz.upce.vetalmael.data.source.clinic.ClinicRepository
import cz.upce.vetalmael.data.source.clinic.ClinicRepositoryImpl
import cz.upce.vetalmael.data.source.login.LoginRepository
import cz.upce.vetalmael.data.source.login.LoginRepositoryImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val dataSourceModule = module {

    single<ApplicationRepository> { ApplicationRepositoryImpl(get()) } bind ErasableRepository::class

    single<LoginRepository> { LoginRepositoryImpl(get(), get()) }

    single<AnimalRepository> { AnimalRepositoryImpl(get()) } bind ErasableRepository::class

    single<ReservationRepository> { ReservationRepositoryImpl(get()) } bind ErasableRepository::class

    single<ClinicRepository> { ClinicRepositoryImpl(get()) } bind ErasableRepository::class
}