package com.dev.gka.abda.offline

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.dev.gka.abda.model.User

class PrefManager private constructor() {

    fun saveUserInfo(user: User) {
        editor!!.putString("name", user.name)
        editor!!.putString("imageUrl", user.imageLink.toString())
        editor!!.putString("email", user.email)
        editor!!.putString("phone", user.phone)
        editor!!.commit()
    }

    fun getDisplayName(): String? {
        return sharedPreferences!!.getString("name", "")
    }

    fun getImageUrl(): String? {
        return sharedPreferences!!.getString("imageUrl", "")
    }

    fun getEmail(): String? {
        return sharedPreferences!!.getString("email", "")
    }

    fun getUserPhone(): String? {
        return sharedPreferences!!.getString("phone", "No Record Found")
    }

    companion object {
        private val prefManager = PrefManager()
        private var sharedPreferences: SharedPreferences? = null
        private var editor: SharedPreferences.Editor? = null

        @Synchronized
        fun getInstance(context: Context?): PrefManager {
            if (sharedPreferences == null) {
                sharedPreferences = context?.getSharedPreferences(
                    context.packageName,
                    Activity.MODE_PRIVATE
                )
                editor = sharedPreferences!!.edit()
            }
            return prefManager
        }
    }
}