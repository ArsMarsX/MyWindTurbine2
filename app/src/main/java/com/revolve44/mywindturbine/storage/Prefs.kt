package com.revolve44.mywindturbine.storage

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log

class Prefs( ctx: Context) {



    private var preferences: SharedPreferences? = null

    private fun SharedPref(context: Context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun initPreferences(context: Context?) {
        if (context != null) {
            Prefs(context)
        }
    }

//    var temp: String
//        get() = preferences.getString("name", "-");
//        set(value) {
//            val editor: SharedPreferences.Editor? = preferences.edit()
//            editor.putString("name", value)
//            editor.apply()
//
//
//        }
//
////    private val sharedPreference: SharedPreferences =
////        ctx.getSharedPreferences("app", Context.MODE_PRIVATE)
//
//
//
//
////    fun saveTemp(temp: String) {
////        //Timber.d("saveLogin")
////        val editor: SharedPreferences.Editor = sharedPreference.edit()
////        editor.putString("temp", temp)
////        editor.apply()
////        Log.d("HEY THERE", temp)
////
////    }
////    fun getTemp(): String {
////        return sharedPreference.getString("temp", "") ?: ""
////    }


}