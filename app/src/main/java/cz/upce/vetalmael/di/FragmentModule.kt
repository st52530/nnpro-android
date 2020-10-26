package cz.upce.vetalmael.di

import cz.upce.vetalmael.animals.AnimalsFragment
import cz.upce.vetalmael.login.view.LoginFragment
import cz.upce.vetalmael.reservations.ReservationsFragment
import cz.upce.vetalmael.user.UserFragment
import org.koin.androidx.fragment.dsl.fragment
import org.koin.dsl.module

val fragmentModule = module {

    fragment { LoginFragment(get()) }

    fragment { AnimalsFragment() }

    fragment { ReservationsFragment() }

    fragment { UserFragment(get(), get()) }
}