package cz.upce.vetalmael.di

import cz.upce.vetalmael.login.di.loginModule
import org.koin.core.module.Module

val koinFeatureModules: Array<Module> = arrayOf(
    loginModule
)