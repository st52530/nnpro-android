package cz.upce.vetalmael

import android.app.Application
import cz.upce.vetalmael.api.apiModule
import cz.upce.vetalmael.data.source.di.dataSourceModule
import cz.upce.vetalmael.di.fragmentModule
import cz.upce.vetalmael.di.koinFeatureModules
import cz.upce.vetalmael.utils.di.utilsModule
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.get
import org.koin.core.logger.Level

class VetalmaelApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        setupDependencyInjection()
    }

    private fun setupDependencyInjection() {
        startKoin {
            if (BuildConfig.DEBUG) {
                // Koin Android logger.
                // FIXME: Workaround for Koin crash introduced by using Kotlin 1.4
                // FIXME: Hopefully this gets fixed in a new Koin release.
                // FIXME: (We want to use the default logger, not just error).
                androidLogger(Level.ERROR)
            }

            // Inject Android context.
            androidContext(this@VetalmaelApplication)

            // Setup a KoinFragmentFactory instance.
            fragmentFactory()

            // Use modules
            modules(
                apiModule,
                fragmentModule,
                dataSourceModule,
                utilsModule,
                *koinFeatureModules
            )
        }
    }
}