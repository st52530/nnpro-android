package cz.upce.vetalmael.utils.di

import cz.upce.vetalmael.utils.*
import org.koin.dsl.module

val utilsModule = module {

    factory<ResourceProvider> { ResourceProviderImpl(get()) }
}