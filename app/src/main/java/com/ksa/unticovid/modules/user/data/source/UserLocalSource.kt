package com.ksa.unticovid.modules.user.data.source

import android.content.SharedPreferences
import com.google.gson.Gson
import com.ksa.unticovid.modules.user.domain.entity.UserEntity
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber
import javax.inject.Inject

class UserLocalSource @Inject constructor(
    private val sharedPref: SharedPreferences,
    private val gson: Gson
) {

    fun getUser() = callbackFlow {
        val json = sharedPref.getString(USER, null)
        val user: UserEntity? = gson.fromJson(json, UserEntity::class.java)
        if (user == null)
            throw Throwable("user is Null Value ")
        else
            trySend(user)
        awaitClose { Timber.d("Suspending flow until methods aren't invoked") }
    }

    fun saveUser(params: UserEntity) {
        val json = gson.toJson(params)
        with(sharedPref.edit()) {
            putString(
                USER,
                json
            )
            apply()
        }
    }

    fun deleteUser() {
        with(sharedPref.edit()) {
            putString(USER, null)
            commit()
        }
    }

    companion object {
        private const val USER = "USER"
    }
}
