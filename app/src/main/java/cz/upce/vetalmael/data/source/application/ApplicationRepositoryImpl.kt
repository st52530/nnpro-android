package cz.upce.vetalmael.data.source.application

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import cz.upce.vetalmael.data.model.User

class ApplicationRepositoryImpl(
    context: Context
) : ApplicationRepository {

    companion object {

        const val PREFERENCES_NAME = "ApplicationPreferences"

        private const val ACCESS_TOKEN_KEY = "access_token"

        private const val USER_KEY = "user"
    }

    private val gson = Gson()

    private val sharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun getAccessToken(): String? {
        return sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
    }

    override fun setAccessToken(token: String?) {
        sharedPreferences.edit {
            putString(ACCESS_TOKEN_KEY, token)
        }
    }

    override fun getCurrentUser(): User? {
        val userJson = sharedPreferences.getString(USER_KEY, "")
        return if (userJson == "") {
            null
        } else {
            gson.fromJson(userJson, User::class.java)
        }
    }

    override fun setCurrentUser(user: User?) {
        sharedPreferences.edit {
            if (user == null) {
                remove(USER_KEY)
            } else {
                putString(USER_KEY, gson.toJson(user))
            }
        }
    }

    override fun erase() {
        sharedPreferences.edit(commit = true) {
            clear()
        }
    }
}