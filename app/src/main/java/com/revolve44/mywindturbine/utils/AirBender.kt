package com.revolve44.mywindturbine.utils

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.revolve44.mywindturbine.storage.AppPreferences
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.PI
import kotlin.math.pow

/////////////////////////////////////////////
//        AirBender Engine mark 1          //
/////////////////////////////////////////////
//              09/07/2020
class AirBender{
    //---Time Manipulations------------------------
    var SunPeriod = 0
    var unixSunrise: Long = 0
    var unixSunset: Long = 0
    var GMT: Long = 0
    var UTCtime: Long = 0
    var UnixCurrentTime: Long = 0

    var CurrentPower = 0f
    var CurrentPowerInt = 0

    //public String jsonString;
    var Legendzero = LinkedList<String>()
    var Valuezero = LinkedList<Int>()
    var dataMap = LinkedHashMap<Long, Float>()
    val gson = Gson()

    var sunset: String? = null
    var sunrise: String? = null
    var hournowStr: String? = null

    var w = 0 // for timemanipulations
    var s = 0 // for savedata

    //------------------------------------------------


    fun aang(){
        // (1,23*3,14*1*1*0,5)/2
        var curpowx =(((1.23* PI*(AppPreferences.radius).pow(2)*((AppPreferences.wind).pow(3))* AppPreferences.powerefficiency)/2).toFloat())

        if (curpowx > AppPreferences.maxWind){
            AppPreferences.currentPower = AppPreferences.nominalPower
        }
        if (curpowx < AppPreferences.maxWind){
            AppPreferences.currentPower = curpowx
        }

        //AppPreferences.currentPower
        Log.d("Aang check","")
        Log.d("Result of work Aang", AppPreferences.currentPower.toString())
    }

    fun yangchen(windx: Float): Float{
        var answer = (((1.23* PI*(AppPreferences.radius).pow(2)*((windx).pow(3))* AppPreferences.powerefficiency)/2)*AppPreferences.coefficient).toFloat()

        if (answer>AppPreferences.maxWind){
            answer = AppPreferences.nominalPower
        }

        Log.d("Result of work Yangchen", answer.toString())
        return answer
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
        Log.d("Angle name ", ""+anglename)
        AppPreferences.direction =anglename
    }

     fun TimeManipulation() {
        Log.d("Lifecycle -> method ", " timemanipulations ")
        Log.d(
            "Timemaipulation method ",
            " UTC -> " + UnixCurrentTime + " unixSunrise " + unixSunrise + " unixSet " + unixSunset + "GMT " + GMT
        )
        if (UnixCurrentTime > 1 and unixSunrise.toInt() > 1) {
            ////////////////////////////////////////////////////
            //     Time zone & unix sunrise/sunset            //
            //      Here we define human time                 //
            ////////////////////////////////////////////////////
            val timestamp: Long = UnixCurrentTime + GMT
            val timestamp2: Long = unixSunrise + GMT
            val timestamp3: Long = unixSunset + GMT
            //UTCtime = System.currentTimeMillis(); // Here i have been problem coz i multipled on 1000L UTC time:(
            var zeroPlace1 = ""
            var zeroPlace2 = ""
            var zeroPlace3 = ""
            var zeroPlace4 = ""
            /////////////////////////////
            /////////////////////////////
            val day = timestamp / 86400
            //
            val hourinSec = timestamp - day * 86400 //hours in sec
            val hour = hourinSec / 3600 // hr
            //
            val minutesinSec = hourinSec - hour * 3600 // minutes in sec
            val minutes = minutesinSec / 60
            //
            hournowStr = hour.toString()
            /////////////////////////////////
            /////////////////////////////////
            val day2 = timestamp2 / 86400
            //
            val hourinSec2 = timestamp2 - day2 * 86400 //hours in sec
            val hour2 = hourinSec2 / 3600 // hr
            if (hour2 < 10) {
                zeroPlace1 = "0"
            }
            //
            val minutesinSec2 = hourinSec2 - hour2 * 3600 // minutes in sec
            val minutes2 = minutesinSec2 / 60
            if (minutes2 < 10) {
                zeroPlace2 = "0"
            }
            //
            sunrise = "$zeroPlace1$hour2:$zeroPlace2$minutes2"
            /////////////////////////////////////
            ////////////////////////////////////
            val day3 = timestamp3 / 86400
            //
            val hourinSec3 = timestamp3 - day3 * 86400 //hours in sec
            val hour3 = hourinSec3 / 3600 // hr
            if (hour3 < 10) {
                zeroPlace3 = "0"
            }
            //
            val minutesinSec3 = hourinSec3 - hour3 * 3600 // minutes in sec
            val minutes3 = minutesinSec3 / 60
            if (minutes3 < 10) {
                zeroPlace4 = "0"
            }
            //
            sunset = "$zeroPlace3$hour3:$zeroPlace4$minutes3"
            Log.d("TIMEST >", sunrise + "and sunset " + sunset)
            Log.d("TIMEST >", timestamp.toString() + "and  " + timestamp2)
            //////////////////////////////////
            val hournow = hour.toInt()
            val hourRise = hour2.toInt()
            val hourSet = hour3.toInt()
            val sector = (hourSet - hourRise) / 5
            //set Sun Position
            if (hournow > hourSet) {
                SunPeriod = 0
                //Toast.makeText(this, "NIGHT", Toast.LENGTH_SHORT).show();
            } else if (hournow > hourSet - sector) { //Toast.makeText(this, "sunset", Toast.LENGTH_SHORT).show();
                SunPeriod = 5
                //0.6
                //CurrentPower = (CurrentPower * 0.6) as Float
            } else if (hournow > hourSet - 2 * sector) { //Toast.makeText(this, "135", Toast.LENGTH_SHORT).show();
                SunPeriod = 4
                //0.8
                //CurrentPower = (CurrentPower * 0.8) as Float
            } else if (hournow > hourSet - 3 * sector) { //Toast.makeText(this, "90", Toast.LENGTH_SHORT).show();
                SunPeriod = 3
                //1
            } else if (hournow > hourSet - 4 * sector) { //Toast.makeText(this, "45", Toast.LENGTH_SHORT).show();
                SunPeriod = 2
                //0.8
                //CurrentPower = (CurrentPower * 0.8) as Float
            } else if (hournow > hourRise) { //Toast.makeText(this, "sunrise", Toast.LENGTH_SHORT).show();
                SunPeriod = 1
                //0.6
                //CurrentPower = (CurrentPower * 0.6) as Float
            } else {
                SunPeriod = 0
                //            Toast.makeText(this, "NIGHT", Toast.LENGTH_SHORT).show();
            }
            CurrentPowerInt = Math.round(CurrentPower)
            if (SunPeriod == 0) {
                CurrentPowerInt = 0
            }
            Log.d(
                "Timemanipulations END> ",
                "Sunperiod ->$SunPeriod hournow $hournow"
            )
            Log.d("Datamap 2>>>>>", "" + dataMap)
            ////////////////////////////////////////////////////
            //solarhoursString = (hourSet - hourRise).toString()
            //Log.d("@@@@@@@@@@@@", "0 Solarhour:  $solarhoursString")
            //Log.d("##########", " "+sunrise+" "+sunset + " unix -> " +unixSunrise + " GMT is ->" + GMT +" TZ is -> "+ finalblank);
            var zeroPlace5 = ""
            var zeroPlace6 = ""
            //set GMT in human view
            var timeGMT: Int = (GMT / 3600).toInt()
            var tz = TimeZone.getTimeZone("GMT$timeGMT")
            var a = 0
            //------------------------Get from sharedpreference-----------------------------------------------
            var storedHashMapString: String = AppPreferences.jsonDataMap // get HashMap, now is String, after that will be an hashmap

            val type = object : TypeToken<LinkedHashMap<Long?, Float?>?>() {}.type
            dataMap = gson.fromJson(storedHashMapString, type)
            Log.d("Check input data ", dataMap.toString())
            //------------------------------------------------------------------------------------------------
            for ((key1, value) in dataMap.entries) {
                val key: Long = key1 / 1000L + GMT
                zeroPlace5 = ""
                zeroPlace6 = ""
                // Set current GMT
                /* debug: is it local time? */Log.d("Time zone: ", tz.displayName)
                /* date formatter in local timezone */
                val sdf = SimpleDateFormat("dd-MMMM")
                sdf.timeZone = tz
                /* print your timestamp and double check it's the date you expect */
                val date = sdf.format(Date(key * 1000))
                //String date = DateFormat.format("dd-MMMM", key*1000L).toString();
                val day4 = key / 86400
                //
                val hourinSec4 = key - day4 * 86400 //hours in sec
                val hour4 = hourinSec4 / 3600 // hr
                if (hour4 < 10) {
                    zeroPlace5 = "0"
                }
                //
                val minutesinSec4 = hourinSec4 - hour4 * 3600 // minutes in sec
                val minutes4 = minutesinSec4 / 60
                if (minutes4 < 10) {
                    zeroPlace6 = "0"
                }
                //-2:-59 > 21:00
                val ModernTime = "$zeroPlace5$hour4:$zeroPlace6$minutes4"
                //Log.d("Hashmap test loop -> ", "key : "+ key + " value : "+ value);
                val transitTime = hour4.toInt()
                Log.d(
                    "Hashmap test loop -> ",
                    "transittime : $transitTime hoursunset : $hourSet"
                )
                Log.d("Loopz ", "$ModernTime and value $value")
                if (transitTime > hourSet) { //0

                    Legendzero.add("$ModernTime $date")
                    Valuezero.add((value ).toInt())
                } else if (transitTime > hourSet - sector) { //0.6

                    Legendzero.add("$ModernTime $date")
                    Valuezero.add((value ).toInt())
                } else if (transitTime > hourSet - 2 * sector) { //0.8

                    Legendzero.add("$ModernTime $date")
                    Valuezero.add((value ).toInt())
                } else if (transitTime > hourSet - 3 * sector) { //1

                    Legendzero.add("$ModernTime $date")
                    Valuezero.add(value.toInt())
                } else if (transitTime > hourSet - 4 * sector) { //0.8

                    Legendzero.add("$ModernTime $date")
                    Valuezero.add((value ).toInt())
                } else if (transitTime > hourSet - 5 * sector) { //0.6

                    Legendzero.add("$ModernTime $date")
                    Valuezero.add((value ).toInt())
                } else { //0

                    Legendzero.add("$ModernTime $date")
                    Valuezero.add((value ).toInt())
                }
                a++
                Log.d(" loop ", "$a times ")
                Log.d("Loop after of this", "$ModernTime and value $value")
            }
            w++
        }
         Log.d(" time manipulation ", "legend zero>>>"+Legendzero)
         Log.d(" time manipulation ", "Value zero>>> "+Valuezero )
         //////////////////////////////////////////////////////////////////////////
         //                    convert to gson and save                          //
         ////////////////////////////////////////////////////////////////////////
         // and s < 1
         if (Legendzero.size > 1 ) {
             //val gson = Gson()
             AppPreferences.chartLegend = gson.toJson(Legendzero)
             AppPreferences.chartValue = gson.toJson(Valuezero)

             s++
         }
    }


    private operator fun Boolean.compareTo(i: Int): Int {
       return i
    }



}


