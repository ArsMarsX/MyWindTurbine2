package com.revolve44.mywindturbine

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.revolve44.mywindturbine.storage.AppPreferences
import com.revolve44.mywindturbine.ui.dashboard.DashboardFragment
import com.revolve44.mywindturbine.ui.home.HomeFragment
import com.revolve44.mywindturbine.ui.notifications.NotificationsFragment
import com.revolve44.mywindturbine.ui.setturbine.LocationActivity
import com.revolve44.mywindturbine.utils.AirBender
import com.revolve44.mywindturbine.utils.Api
import io.feeeei.circleseekbar.CircleSeekBar
import kotlinx.android.synthetic.main.fragment_home.*


class MainActivity : AppCompatActivity() {
    val fragment1: Fragment = HomeFragment()
    val fragment2: Fragment = DashboardFragment()
    val fragment3: Fragment = NotificationsFragment()

    val fm = supportFragmentManager
    var active = fragment1

    private val api: Api = Api() // now is correct may response
    private val homefrag: HomeFragment = HomeFragment() // now is correct may response
    private val airbender: AirBender = AirBender()
   // private val engine: CalcEngine = CalcEngine()
    //private Context contextx;
    private val firstStart = false
    private lateinit var mSeekbar: CircleSeekBar
//    var instance: MainActivity? = null
    //val ctx: Context
    //var sharedPrefs: SharedPreferences? = null
   // private  var s: Prefs = Prefs(this)



    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)


        val navigation =
            findViewById<View>(R.id.nav_view) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        //I added this if statement to keep the selected fragment when rotating the device
        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(
                R.id.nav_host_fragment,
                HomeFragment()
            ).addToBackStack(null).commit()
        }

        //open and .hide fragment in one moment
        fm.beginTransaction().add(R.id.nav_host_fragment, fragment2, "2").hide( fragment2).commit()
        fm.beginTransaction().add(R.id.nav_host_fragment, fragment3, "3").hide(fragment3).commit()
        fm.beginTransaction().add(R.id.nav_host_fragment, fragment1, "1").commit()
        AppPreferences.init(this)
        //-----------------------Startup App--------------------------------
        LaunchPad()

    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun LaunchPad(){
        api.startcall()
        Handler().postDelayed(
            {
                airbender.aang()
                airbender.direction()
                airbender.TimeManipulation()
                //homefrag.refreshData()

                Handler().postDelayed(
                    {
                        homefrag.refreshData()
                    },1000
                )
            },1000
        )

    }

    //switcher of fragmnets, he help for switching without loss filled form in fragments
    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {

                    fm.beginTransaction().hide( active).show(fragment1).commit()
                    active = fragment1
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_dashboard -> {

                    fm.beginTransaction().hide(active).show(fragment2).commit()
                    active = fragment2
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_notifications -> {

                    fm.beginTransaction().hide(active).show(fragment3).commit()
                    active = fragment3
                    return@OnNavigationItemSelectedListener true
                }

            }
            false
        }

    fun directionWind(view: View) {

        Snackbar.make(
            hommes, // Parent view
            "Wind Direction: "+AppPreferences.directionAngle+"°", // Message to show
            Snackbar.LENGTH_LONG // How long to display the message.
        ).show()

    }

    fun tonewact(view: View) {
        val intent = Intent(this, LocationActivity::class.java)
        startActivity(intent)
    }


}
