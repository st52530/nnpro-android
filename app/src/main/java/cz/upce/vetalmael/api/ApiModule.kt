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
            .addConverterFactory(GsonConverterFactory.create())

        retrofitBuilder.client(get())

        retrofitBuilder.build()
    }

    factory {
        val builder = OkHttpClient.Builder()

        // Add interceptors.
        builder.addInterceptor(get<AuthenticationInterceptor>())
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor())
        }

        builder.build()
    }

    factory { AuthenticationInterceptor(get()) }
}