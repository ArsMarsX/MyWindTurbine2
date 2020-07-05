package com.revolve44.mywindturbine.utils

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.revolve44.mywindturbine.storage.AppPreferences
import com.revolve44.mywindturbine.storage.Prefs
//import com.revolve44.fragments22.storage.SharedPref
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*




class Api: AppCompatActivity() {
    //private val engine2: CalcEngine = CalcEngine()
    //Variables
    private var NominalPower = 0f//???????????????????????????????? = 0f
    var CurrentPower = 0f
    var CurrentPowerInt = 0
    var cloud = 0f
    var windF = 0f
    var windI = 0
    //public float temp;
    var desription: String? = null
    var temperature = 12f
    var humidity = 0f
    var tempScale = false
    var unixSunrise: Long = 0
    var unixSunset: Long = 0
    var city: String? = null
    var country: String? = null
    private val mContext: Context? = null
    private val dataMap =
        LinkedHashMap<Long, Float>()
    private var TimeHashMap: Long = 0
    private var CurrentPowerHashMap = 0f
    var z = 0 // for retrofit -

   // val s = Prefs(ctx)
//    lateinit var s: Prefs
     private var s: Prefs = Prefs(this)

    @RequiresApi(api = Build.VERSION_CODES.N)
    fun startcall() {
        try {
            currentData
        } catch (e: Exception) {
        }
//        NominalPower = SharedPref.getNominal(mContext).toFloat()
//        lon =
//            java.lang.String.valueOf(SharedPref.getLon(mContext))
//        lat =
//            java.lang.String.valueOf(SharedPref.getLat(mContext))
//        currentData
        //endprocess();
    }// чисто разницу лучше не оставлять, а домножанать на 0.8 например чтобы при макс. облач. не было нуля
    //convert to string using gso
    @get:RequiresApi(api = Build.VERSION_CODES.N)
    val currentData: Unit
        get() {
            Log.d("Lifecycle -> method ", " getCurrentdata ")
            Log.d(
                "Lifecycle -> method ",
                " latitude $lat$lon"
            )
            val okhttpClientBuilder = OkHttpClient.Builder() //for create a LOGs
            val logging = HttpLoggingInterceptor() //for create a LOGs
            logging.level = HttpLoggingInterceptor.Level.BODY //for create a LOGs
            okhttpClientBuilder.addInterceptor(logging) //for create a LOGs
            val retrofit = Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClientBuilder.build()) //for create a LOGs
                .build()
            val service = retrofit.create(WeatherService::class.java)
            //Call<WeatherResponse> call = service.getCurrentWeatherData(CITY, metric, AppId);
            val call = service.getCurrentWeatherData(
                lat,
                lon,
                metric,
                AppId
            )
            call.enqueue(object : Callback<WeatherResponse?> {
                override fun onResponse(
                    call: Call<WeatherResponse?>,
                    response: Response<WeatherResponse?>
                ) {
                    if (response.code() == 200) {


                        val weatherResponse = response.body()!!
                        //main variables
                        AppPreferences.temp =  (weatherResponse.main.temp.toString())
//                        SharedPref.setTemp(weatherResponse.main.temp, mContext)
//                        SharedPref.setSecondaryData(
//                            weatherResponse.name,
//                            weatherResponse.dt.toLong(),
//                            weatherResponse.sys.sunset,
//                            weatherResponse.sys.sunrise,
//                            weatherResponse.timezone,
//                            weatherResponse.clouds.all,
//                            weatherResponse.wind.speed,
//                            weatherResponse.main.pressure,
//                            weatherResponse.main.humidity, mContext
//                        )
                    }
                }

                override fun onFailure(
                    call: Call<WeatherResponse?>,
                    t: Throwable
                ) {
                    Log.e("ERROR", " On Retrofit")
                }
            })
            val forecastCall = service.getDailyData(
                lat,
                lon,
                metric,
                AppId
            )
//            forecastCall.enqueue(object : Callback<WeatherForecastResponse?> {
//                override fun onResponse(
//                    forecastCall: Call<WeatherForecastResponse?>,
//                    response: Response<WeatherForecastResponse?>
//                ) {
//                    if (response.code() == 200) {
//                        val weatherResponse = response.body()!!
//                        val list = weatherResponse.list
//                        if (dataMap.size == 0) {
////                            Log.d("z -1 ", "$z ")
////                            for (wr in list) {
////                                if (z <= 20) {
////                                    CurrentPowerHashMap =
////                                        NominalPower - NominalPower * (wr.clouds.all / 100) * 0.8f // чисто разницу лучше не оставлять, а домножанать на 0.8 например чтобы при макс. облач. не было нуля
////                                    TimeHashMap = wr.dt.toLong() * 1000
////                                    dataMap[TimeHashMap] = CurrentPowerHashMap
////                                    Log.d(
////                                        "Datamap 1->",
////                                        "$TimeHashMap $CurrentPowerHashMap"
////                                    )
////                                    z++
////                                }
////                            }
//                            Log.d("Datamap 1>>>>>", "" + dataMap)
//                        }
//                        //convert to string using gson
//                        val gson = Gson()
//                        //SharedPref.setjsonDataMap(gson.toJson(dataMap), mContext)
//                    }
//                }
//
//                override fun onFailure(
//                    forecastCall: Call<WeatherForecastResponse?>,
//                    t: Throwable
//                ) {
//                    Log.e("ERROR", "retrofit")
//                }
//            })
        }

    companion object {
        var BaseUrl = "https://api.openweathermap.org/"
        var CITY = "Moscow"
        var AppId = "4e78fa71de97f97aa376e42f2f1c99cf"
        var MC = "&units=metric&appid="
        //CalcEngine
        var lat = "55.78"
        var lon = "49.12"
        var metric = "metric"
    }
}