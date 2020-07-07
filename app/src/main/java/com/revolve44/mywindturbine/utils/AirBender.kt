package com.revolve44.mywindturbine.utils

import android.util.Log
import com.revolve44.mywindturbine.storage.AppPreferences
import kotlin.math.PI
import kotlin.math.pow

class AirBender{


    fun aang(){
        AppPreferences.currentPower = ((1.23* PI*(AppPreferences.radius).pow(2)*((AppPreferences.wind).pow(3))*AppPreferences.correction).toFloat())
        Log.d("Aang check","")
        Log.d("Result of work Aang", AppPreferences.currentPower.toString())
    }

    fun direction(){
        var directionangle = AppPreferences.directionAngle
        var anglename: String = "-"

        when (directionangle){
           in 0..23 -> anglename = "N"
            in 24..45 -> anglename = "NNE"
            in 46..68 -> anglename = "NE"
            in 69..90 -> anglename = "ENE"
            in 91..113 -> anglename = "E"
            in 114..135 -> anglename = "ESE"
            in 136..158 -> anglename = "SE"
            in 159..180 -> anglename = "S"
            in 181..203 -> anglename = "SSW"
            in 204..225 -> anglename = "SW"
            in 226..248 -> anglename = "WSW"
            in 249..270 -> anglename = "W"
            in 271..293 -> anglename = "WNW"
            in 294..315 -> anglename = "NW"
            in 316..338 -> anglename = "NNW"
            in 339..360 -> anglename = "N"
        }
//        when (directionwind){
//            "N" -> angleanim = 22.5f
//            "NNE" -> angleanim = 22.5f*2
//            "NE" -> angleanim = 22.5f*3
//            "ENE" -> angleanim = 22.5f*4
//            "E" -> angleanim = 22.5f*5
//            "ESE" -> angleanim = 22.5f*6
//            "SE" -> angleanim = 22.5f*7
//            "SSE" -> angleanim = 22.5f*8
//            "S" -> angleanim = 22.5f*9
//            "SSW" -> angleanim = 22.5f*10
//            "SW" -> angleanim = 22.5f*11
//        }

        Log.d("Angle name ", ""+anglename)
        AppPreferences.direction =anglename
    }


}