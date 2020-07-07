package com.revolve44.mywindturbine.ui.notifications

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.revolve44.mywindturbine.R
import com.revolve44.mywindturbine.storage.AppPreferences
import io.feeeei.circleseekbar.CircleSeekBar
import kotlinx.android.synthetic.main.fragment_notifications.*
import kotlin.math.roundToInt


class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var mSeekbar: CircleSeekBar
    private var curcalibrationTV: TextView? = null
    private var aftercalibrationpowerTV: TextView? = null
    private var calibrationdata: Float = 0f
    private var checker: Boolean = false
    private var curpow: Float = 0f

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
        mSeekbar.curProcess = 100



        Handler().postDelayed(
            {
                //aftercalibrationpowerTV?.setText(""+calibrationdata)
                curpow = AppPreferences.currentPower
            },3400
        )


        //Listener i get it YEEEY
        mSeekbar.setOnSeekBarChangeListener { seekbar, curValue ->

            //calibrationdata = ((mSeekbar.curProcess /100).toFloat())

            curcalibrationTV?.setText(""+mSeekbar.curProcess+" %")

            aftercalibrationpowerTV?.setText(""+(((mSeekbar.curProcess)/100f)*curpow).roundToInt())
            //aftercalibrationpowerTV?.setText(""+(((mSeekbar.curProcess)/100)*curpow))
            checker = true

        }






        //CircleSeekBar.

        return root
    }

    override fun onPause() {
        if (checker == true){
            AppPreferences.currentPower = calibrationdata.toFloat()
        }

        super.onPause()
    }



}


