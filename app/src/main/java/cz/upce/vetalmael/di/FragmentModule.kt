package cz.upce.vetalmael.di

import cz.upce.vetalmael.login.view.LoginFragment
import org.koin.androidx.fragment.dsl.fragment
import org.koin.dsl.module

val fragmentModule = module {

    fragment { LoginFragment(get()) }
}