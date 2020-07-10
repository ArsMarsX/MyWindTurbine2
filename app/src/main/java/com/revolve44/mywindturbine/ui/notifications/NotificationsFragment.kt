package com.revolve44.mywindturbine.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.revolve44.mywindturbine.R
import com.revolve44.mywindturbine.storage.AppPreferences
import com.revolve44.mywindturbine.ui.home.HomeFragment
import io.feeeei.circleseekbar.CircleSeekBar
import kotlin.math.roundToInt


class NotificationsFragment : Fragment() {

    //val mainAct: MainActivity = MainActivity()
    private val homeFragment: HomeFragment = HomeFragment()

    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var mSeekbar: CircleSeekBar
    private var curcalibrationTV: TextView? = null
    private var aftercalibrationpowerTV: TextView? = null
    private var calibrationdata: Float = 0f
    private var checker: Boolean = false
    private var curpow: Float = 0f
    private var chk = 0
    
    //private va

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)

        mSeekbar = root.findViewById(R.id.seekbar)
        curcalibrationTV = root.findViewById(R.id.count)
        aftercalibrationpowerTV = root.findViewById(R.id.aftercalibrationpower)


        mSeekbar.maxProcess = 200
        //mSeekbar.curProcess = 100


        curpow = AppPreferences.currentPower
//        Handler().postDelayed(
//            {
//                //aftercalibrationpowerTV?.setText(""+calibrationdata)
//
//            },3400
//        )


        //Listener i get it YEEEY
        mSeekbar.setOnSeekBarChangeListener { seekbar, curValue ->

            //calibrationdata = ((mSeekbar.curProcess /100).toFloat())




           // AppPreferences.currentPower = ((mSeekbar.curProcess)/100f)*curpow
            aftercalibrationpowerTV?.setText(""+(AppPreferences.currentPower*AppPreferences.coefficient).roundToInt())
            //aftercalibrationpowerTV?.setText(""+(((mSeekbar.curProcess)/100)*curpow))

            if ((AppPreferences.currentPower*AppPreferences.coefficient)> AppPreferences.nominalPower){
                view?.let { Snackbar.make(it, "Calibrated power cannot be greater than nominal power", Snackbar.LENGTH_LONG).show() };

                mSeekbar.curProcess = 100
            }
            if ((AppPreferences.currentPower*AppPreferences.coefficient)< AppPreferences.nominalPower){
                AppPreferences.progressinSeekBar = mSeekbar.curProcess
//                Thread(Runnable {
//
//                }).start()

//                val myThread = Thread( // создаём новый поток
//                    Runnable // описываем объект Runnable в конструкторе
//                    {
//
//                        // a potentially time consuming task
//                        Log.d("Threadx", "call refresh")
//                        homeFragment.refreshData()
//                    }
//                ).start()

                if (chk >0){
                    view?.let { Snackbar.make(it, "Now swipe down in Home - to update data with new settings ", Snackbar.LENGTH_LONG).show() };

                }
            }
            curcalibrationTV?.setText(""+mSeekbar.curProcess+" %")
            AppPreferences.coefficient = (mSeekbar.curProcess)/100f

            checker = true
            //clearSensordata(root)

            chk++


            //val MSB =  Snackbar.make(activity!!.findViewById(android.R.id.content),"This is a simple Snackbar",Snackbar.LENGTH_LONG)
        }

        //CircleSeekBar.
        return root
    }


    override fun onResume() {
        Log.d("LIFECYCLE Calibration", "onresume")
        super.onResume()
        mSeekbar.curProcess = AppPreferences.progressinSeekBar
        aftercalibrationpowerTV?.setText(""+(AppPreferences.currentPower*AppPreferences.coefficient).roundToInt())
    }


    override fun onPause() {
//        if (checker == true){
//            AppPreferences.currentPower = calibrationdata.toFloat()
//        }
        Log.d("LIFECYCLE Calibration", "pause")

        super.onPause()
    }



}


