package com.revolve44.mywindturbine.storage

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {
    private const val NAME = "SpinKotlin"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    // list of app specific preferences
    private val IS_FIRST_RUN_PREF = Pair("is_first_run", false)

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    /**
     * SharedPreferences extension function, so we won't need to call edit() and apply()
     * ourselves on every SharedPreferences operation.
     */
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var temp: Float
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getFloat("temp",1f )

        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putFloat("temp", value)
        }

    var wind: Float
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getFloat("wind",1f )

        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putFloat("wind", value)
        }

    var direction: String
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getString("direction","Suk" ).toString()

        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putString("direction", value)
        }

    var directionAngle: Float
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getFloat("directionAngle",0f )

        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putFloat("directionAngle", value)
        }

    var currentPower: Float
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getFloat("currentpower",0f )

        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putFloat("currentpower", value)
        }

    var correction : Float
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getFloat("correction",1f )

        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putFloat("correction", value)
        }

    var radius : Float
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getFloat("radius",100f )

        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putFloat("radius", value)
        }

    var angleanim : Float
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getFloat("angleanim",1f )

        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putFloat("angleanim", value)
        }

    var cityX: String
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getString("city","error" ).toString()

        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putString("city", value)
        }

//    var calibrationdata : Float
//        // custom getter to get a preference of a desired type, with a predefined default value
//        get() = preferences.getFloat("calibrationdata",1f )
//
//        // custom setter to save a preference back to preferences file
//        set(value) = preferences.edit {
//            it.putFloat("calibrationdata", value)
//        }


}