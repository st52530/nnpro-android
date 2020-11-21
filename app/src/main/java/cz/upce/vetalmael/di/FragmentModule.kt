package cz.upce.vetalmael.di

import cz.upce.vetalmael.animals.add.AddAnimalFragment
import cz.upce.vetalmael.animals.list.AnimalsFragment
import cz.upce.vetalmael.login.view.LoginFragment
import cz.upce.vetalmael.registration.RegistrationFragment
import cz.upce.vetalmael.reservations.add.AddReservationFragment
import cz.upce.vetalmael.reservations.list.ReservationsFragment
import cz.upce.vetalmael.user.UserFragment
import org.koin.androidx.fragment.dsl.fragment
import org.koin.dsl.module

val fragmentModule = module {

    fragment { LoginFragment(get()) }

    fragment { AnimalsFragment(get()) }

    fragment { ReservationsFragment(get()) }

    fragment { UserFragment(get(), get()) }

    fragment { RegistrationFragment(get()) }

    fragment { AddAnimalFragment(get()) }

    fragment { AddReservationFragment(get(), get()) }
}