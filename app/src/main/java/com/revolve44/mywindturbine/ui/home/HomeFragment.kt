package com.revolve44.mywindturbine.ui.home

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.google.android.material.snackbar.Snackbar
import com.revolve44.mywindturbine.R
import im.dacer.androidcharts.LineView
import kotlinx.android.synthetic.main.fragment_home.*
import lecho.lib.hellocharts.model.AxisValue
import lecho.lib.hellocharts.model.PointValue
import lecho.lib.hellocharts.view.LineChartView
import java.util.*

class HomeFragment : Fragment(){

    private lateinit var homeViewModel: HomeViewModel
    //private var mSwipeRefreshLayout: SwipeRefreshLayout? = null

    var fan: ImageView? = null
    var arrow: ImageView? = null
    var lineView: LineView? = null
    var lineChart: LineChartView? = null
    var cityView: TextView? = null

    var city = "Tokio"

    private val mPointValues: List<PointValue> =
        ArrayList()
    private val mAxisXValues: List<AxisValue> = ArrayList()
    var q = 0
    var CurrentPower: TextView? = null
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

        //getActivity().setTitle(city);
        fan = root.findViewById<View>(R.id.fan) as ImageView
        arrow = root.findViewById<View>(R.id.arrow) as ImageView

        cityView = root.findViewById(R.id.city)

        lineChart = root.findViewById<View>(R.id.line_chart) as LineChartView
        CurrentPower = root.findViewById(R.id.CurrentPower)

//
         //mSwipeRefreshLayout = root.findViewById(R.id.swipecontainer)
//
//        mSwipeRefreshLayout.setOnRefreshListener {  }
//        mSwipeRefreshLayout.setOnRefreshListener(this as OnRefreshListener)
//
//        mSwipeRefreshLayout.getNestedScrollAxes()
//
//        mSwipeRefreshLayout.setColorSchemeResources(
//            android.R.color.holo_blue_bright,
//            android.R.color.holo_red_light
//        )
//        mSwipeRefreshLayout.setOnRefreshListener {
//            Snackbar.make(
//                requireActivity().findViewById(android.R.id.content),
//                "Swiping now " ,
//                Snackbar.LENGTH_SHORT
//            ).show()
//
//
//        }

        swiperefresh()






        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            //textView.text = it
        })
        return root
    }

    private fun swiperefresh(){
//        swipecontainer.setOnRefreshListener {
//            Log.d(TAG, "GGGGGGGGOOOOOOOOO")     // refresh your list contents somehow
//            swipecontainer.isRefreshing = false   // reset the SwipeRefreshLayout (stop the loading spinner)
//        }

    }

//    override fun onRefresh() {
//        Snackbar.make(
//            requireActivity().findViewById(android.R.id.content),
//            "Swiping now " ,
//            Snackbar.LENGTH_SHORT
//        ).show()
//        Log.d(TAG, "Hey There")
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        swipecontainer.isRefreshing = false
//    }


}
