package com.revolve44.mywindturbine.ui.home

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.revolve44.mywindturbine.R
import com.revolve44.mywindturbine.storage.AppPreferences
//import com.revolve44.mywindturbine.storage.AppPreferences.wind
import im.dacer.androidcharts.LineView
import kotlinx.android.synthetic.main.fragment_home.*
import lecho.lib.hellocharts.model.AxisValue
import lecho.lib.hellocharts.model.PointValue
import lecho.lib.hellocharts.view.LineChartView
import java.util.*
import kotlin.math.roundToInt

class HomeFragment : Fragment(){

    private lateinit var homeViewModel: HomeViewModel
    //private var mSwipeRefreshLayout: SwipeRefreshLayout? = null



//    var lineChart: LineChartView? = null
//    var cityView: TextView? = null

    var city = "Tokio"

    private val mPointValues: List<PointValue> =
        ArrayList()
    private val mAxisXValues: List<AxisValue> = ArrayList()
    var q = 0
    private var CurrentPowerTV: TextView? = null
    private var windTV: TextView? = null
    private var directionTV: TextView? = null
    private var cityTV: TextView? = null

    private var arrow: ImageView? = null
    private var fan: ImageView? = null
    //val text: TextView
    var CurrentPowerInt = 0


    private val mCatTextView: TextView? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        //val textView: TextView = root.findViewById(R.id.text_home)
        windTV = root.findViewById(R.id.windspeed)
        directionTV = root.findViewById(R.id.winddirection)
        CurrentPowerTV = root.findViewById(R.id.CurrentPowerX)
        cityTV= root.findViewById(R.id.city)
        arrow = root.findViewById(R.id.arrow)
        fan = root.findViewById(R.id.fan)

        refreshData()

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            //textView.text = it
        })
        return root
    }

    fun refreshData(){
        Handler().postDelayed(
            {
                rotateArrow()
                rotateFan()
                cityTV?.setText(AppPreferences.cityX.toString())
                CurrentPowerTV?.setText(AppPreferences.currentPower.roundToInt().toString())//*
                windTV?.setText(AppPreferences.wind.toString())
                directionTV?.setText(AppPreferences.direction.toString())


                //Log.d("HEY THERE", ""+ AppPreferences.temp)
            },3000
        )


    }

    fun rotateFan() {
        var wind  = AppPreferences.wind
        val rotate = RotateAnimation(
            360f,
            0f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.625f
        )
        rotate.duration = 500
        //from 0,61 to 0,64
// Bofort scale
        if (wind == 0f){
            rotate.setDuration(9000);
        }else if (wind>=1 && wind <= 3) {
            rotate.setDuration(5000);
        }else if (wind>=2 && wind <=5){
            rotate.setDuration(3000);
        }else if (wind>=6 && wind <= 10){
            rotate.setDuration(1500);
        }else if (wind>=11){
            rotate.setDuration(900);
        }

        rotate.setDuration(1500);


        rotate.repeatCount = Animation.INFINITE
        rotate.interpolator = LinearInterpolator()
        fan?.startAnimation(rotate)
    }

    fun rotateArrow() {

        Log.d("rotate arrow test", ""+AppPreferences.angleanim)
        val rotate = RotateAnimation(
            0f,
            AppPreferences.directionAngle-180f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        rotate.setFillAfter(true);
        rotate.setRepeatCount(0);
        //rotate.duration = 1000
        rotate.setDuration(6000);

        //rotate. = Animation.ABSOLUTE
        //rotate.interpolator = LinearInterpolator()
        arrow?.startAnimation(rotate)

        Handler().postDelayed(
            {
                //arrow?.clearAnimation()
            },5000
        )


    }

}
