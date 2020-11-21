package cz.upce.vetalmael.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import cz.upce.vetalmael.BuildConfig
import cz.upce.vetalmael.api.interceptors.AuthenticationInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Converter
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
            .addConverterFactory(get())

        retrofitBuilder.build()
    }

    factory< Converter.Factory> {
        GsonConverterFactory.create(get())
    }

    factory {
        GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
            .create()
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