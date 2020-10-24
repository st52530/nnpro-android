package cz.upce.vetalmael.api.interceptors

import android.content.Context
import cz.upce.vetalmael.data.source.application.ApplicationRepository
import cz.upce.vetalmael.login.domain.LogoutUsecase
import cz.upce.vetalmael.main.view.MainActivity
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.KoinComponent
import org.koin.core.get
import java.net.HttpURLConnection

/**
 * Custom implementation of [Interceptor] that handles injection of authentication token that is
 * required by majority of API calls.
 */
class AuthenticationInterceptor(
    private val applicationRepository: ApplicationRepository
) : Interceptor, KoinComponent {

    companion object {

        /**
         * Name of authentication token header that is required in protected requests.
         */
        const val AUTHENTICATION_TOKEN_NAME = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequestBuilder = originalRequest.newBuilder()

        val token = applicationRepository.getAccessToken()
        // Add proper authentication header.
        if (token != null) {
            newRequestBuilder.addHeader(AUTHENTICATION_TOKEN_NAME, "Bearer $token")
        }

        val response = chain.proceed(newRequestBuilder.build())

        // Logout when any request fails on authorization.
        if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            runBlocking {
                get<LogoutUsecase>().logout()

                // Re-start the app.
                val context = get<Context>()
                context.startActivity(MainActivity.newIntent(context))
            }
        }

        return response
    }
}