package com.revolve44.mywindturbine

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.revolve44.mywindturbine.storage.AppPreferences
import com.revolve44.mywindturbine.storage.Prefs
import com.revolve44.mywindturbine.ui.dashboard.DashboardFragment
import com.revolve44.mywindturbine.ui.home.HomeFragment
import com.revolve44.mywindturbine.utils.Api


class MainActivity : AppCompatActivity() {
    val fragment1: Fragment = HomeFragment()
    val fragment2: Fragment = DashboardFragment()
    //final Fragment fragment3 = new NotificationsFragment();
    val fm = supportFragmentManager
    var active = fragment1

    private val api: Api = Api() // now is correct may response

   // private val engine: CalcEngine = CalcEngine()
    //private Context contextx;
    private val firstStart = false
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
        // fm.beginTransaction().replace(R.id.fragment_container, fragment3, "3").hide(fragment2).commit();
        // fm.beginTransaction().replace(R.id.fragment_container, fragment3, "3").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.nav_host_fragment, fragment2, "2").hide(fragment2).commit()
        fm.beginTransaction().add(R.id.nav_host_fragment, fragment1, "1").commit()
        AppPreferences.init(this)
        api.startcall()

        Handler().postDelayed(
            {
                Log.d("HEY THERE", AppPreferences.temp)
            },3000
        )


    }

    //switcher of fragmnets, he help for switching without loss filled form in fragments
    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    fm.beginTransaction().hide(active).show(fragment1).commit()
                    active = fragment1
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_dashboard -> {
                    fm.beginTransaction().hide(active).show(fragment2).commit()
                    active = fragment2
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
}
