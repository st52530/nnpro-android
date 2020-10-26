package cz.upce.vetalmael.api

import cz.upce.vetalmael.BuildConfig
import cz.upce.vetalmael.api.interceptors.AuthenticationInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {

    single {
        val retrofit: Retrofit = get()
        retrofit.create(VetAlmaelApi::class.java)
    }

    factory {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())

        retrofitBuilder.build()
    }

    factory {
        val builder = OkHttpClient.Builder()

        // Add interceptors.
        builder.addInterceptor(get<AuthenticationInterceptor>())

        val logLevel = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = logLevel
        builder.addInterceptor(loggingInterceptor)

        builder.build()
    }

    factory { AuthenticationInterceptor(get()) }
}