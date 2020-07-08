package com.revolve44.mywindturbine.ui.home

//import com.revolve44.mywindturbine.storage.AppPreferences.wind
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.graphics.Color
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
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.revolve44.mywindturbine.R
import com.revolve44.mywindturbine.storage.AppPreferences
import lecho.lib.hellocharts.gesture.ZoomType
import lecho.lib.hellocharts.model.*
import lecho.lib.hellocharts.view.LineChartView
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class HomeFragment : Fragment(){

    private lateinit var homeViewModel: HomeViewModel
    //private var mSwipeRefreshLayout: SwipeRefreshLayout? = null



//    var lineChart: LineChartView? = null
//    var cityView: TextView? = null

    var city = "Tokio"

    //-------------
    private var date2 = ArrayList<String>()
    private var score2 = ArrayList<Int>()

    private var mPointValues = ArrayList<PointValue>()
    private var mAxisXValues = ArrayList<AxisValue>()
    //------------
    private var lineChart: LineChartView? = null
    var q = 0
    private var CurrentPowerTV: TextView? = null
    private var windTV: TextView? = null
    private var directionTV: TextView? = null
    private var cityTV: TextView? = null

    private var arrow: ImageView? = null
    private var fan: ImageView? = null
    //val text: TextView
    var onResumeCounter = 0
    var CurrentPowerInt = 0

    var isruntime = 0

    //    String[] date2 = {"5-23","5-22","6-22","5-23","5-22","2-22","5-22","4-22","9-22","10-22","11-22","12-22","1-22","6-22","5-23","5-22","2-22","5-22","4-22","9-22","10-22","11-22","12-22","4-22","9-22","10-22","11-22","zxc"};//X轴的标注
//    Integer[] score2= {74,22,18,79,20,74,20,74,42,90,74,42,90,50,42,90,33,10,74,22,18,79,20,74,22,18,79,20};//图表的数据

    //    private String[] date2 = {"lol", "POL","lodl", "POwL"};
//    private Integer[] score2= {74,22,18,79};
//    private val mPointValues: List<PointValue> =
//        ArrayList()
//    private val mAxisXValues: List<AxisValue> = ArrayList()


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

        lineChart = root.findViewById(R.id.line_chart)



        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            //textView.text = it
        })

        return root
    }


    fun refreshData(){
        Handler().postDelayed(
            {
                Log.d("Load refresh", "<<input data>>"
                        + AppPreferences.cityX.toString() + " / "
                         + AppPreferences.currentPower.roundToInt().toString()+ " / "
                          + AppPreferences.direction.toString()
                )
                cityTV?.setText(AppPreferences.cityX.toString())
                CurrentPowerTV?.setText(AppPreferences.currentPower.roundToInt().toString())//*
                windTV?.setText(AppPreferences.wind.toString())
                directionTV?.setText(AppPreferences.direction.toString())

//                mPointValues.toMutableList().clear()
//                mAxisXValues.toMutableList().clear()
                rotateArrow()
                rotateFan()
                makeArray()

                Log.d("Load refresh", "<<>>")
                onResumeCounter++
                //Log.d("HEY THERE", ""+ AppPreferences.temp)
            },10
        )


    }

    fun switcherruntime(){

        cityTV?.setText(AppPreferences.cityX.toString())
        CurrentPowerTV?.setText(AppPreferences.currentPower.roundToInt().toString())//*
        windTV?.setText(AppPreferences.wind.toString())
        directionTV?.setText(AppPreferences.direction.toString())

        getAxisXLables() //获取x轴的标注
        getAxisPoints() //获取坐标点
        initLineChart() //初始化

    }


    override fun onResume() {
        Log.d("LIFECYCLE HOme", "onresume")
        Log.d("methods ", "methods -> OnResume")
        super.onResume()
//        if (onResumeCounter>0){
//
//        }

        refreshData()
//        if (mPointValues.size<22){
//            switcherruntime()
//        }else{
//
//
//        }

//        Handler().postDelayed(
//            {
//                Log.d(TAG, "LAUNCH")
//
//                makeArray()
//
//                Log.d(TAG, "LAUNCH" + mAxisXValues.size +" / "+ mPointValues.size)
//            },5000
//        )

        Handler().postDelayed(
            {
                Log.d(TAG, "LAUNCH")

//                makeArray()

                Log.d(TAG, "LAUNCH 2: " + mAxisXValues.size +" / "+ mPointValues.size)
            },9000
        )
        Log.d("qis>>>>>", "" + q)


//        rotateArrow()
//        rotateFan()
//        //makeArray()
//        cityTV?.setText(AppPreferences.cityX.toString())
//        CurrentPowerTV?.setText(AppPreferences.currentPower.roundToInt().toString())//*
//        windTV?.setText(AppPreferences.wind.toString())
//        directionTV?.setText(AppPreferences.direction.toString())



    }


    override fun onPause() {
        Log.d("LIFECYCLE HOme", "pause")
        super.onPause()
        q=0
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
        Log.d("methods ", "methods -> rotateArrow")
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




    }

    fun makeArray() {
        //this.mContext = context;
       //////////////////////////////////////////////////////////////////////////
      //                   Paste data from server to chart                    //
     //////////////////////////////////////////////////////////////////////////
     // i use delay for dont make error
        Log.d("methods ", "methods -> makeArray")
        val handler = Handler()
        handler.postDelayed({
            try {
                Log.d("Lifecycle -> method ", " build Graph ")
                //////////////////////////////////////////////////////////////////////////
                //                       GET FROM SHARED PREFERENCES                    //
                //////////////////////////////////////////////////////////////////////////
                val Legend: LinkedList<String>
                val Value: LinkedList<Int>
//                var Legend: Collection<String?>
//                var : Collection<Int?>
                val gson = Gson()
                val json: String = AppPreferences.chartLegend
                val json2: String = AppPreferences.chartValue
                val type =  object : TypeToken<LinkedList<String?>?>() {}.type
                val type2 = object : TypeToken<LinkedList<Int?>?>() {}.type


               //val listType: Type = object : TypeToken<LinkedList<>>() {}.type

//
//                Legend = Gson().fromJson(json, listType)
//                Value = gson.fromJson(json2, )
                Legend = gson.fromJson(json, type)
                Value = gson.fromJson(json2, type2)
                Log.d("Datamap 3>>>>>", "$Legend and Value >>>$Value")
                //////////////////////////////////////////////////////////
                //                  initialization                      //
                //////////////////////////////////////////////////////////and q <= 1
                if (Legend.size > 1) {
//                    mPointValues.toMutableList().clear()
//                    mAxisXValues.toMutableList().clear()
                    if(Legend.size <24){
                        if (q <= 1){

                            date2 = ArrayList(Legend)
                            score2 = ArrayList(Value)

                            //Value.toArray(score2);
                            //score2 = Value.toArray();
                            getAxisXLables() //获取x轴的标注
                            getAxisPoints() //获取坐标点
                            initLineChart() //初始化
                            Log.d("Datamap 5>>>>>", (date2).toString()+ score2.toString())
                            q++

                        }
                    }


                }
            } catch (e: Exception) {
                Toast.makeText(
                    getContext(),
                    "No Internet. Check Internet connection",
                    Toast.LENGTH_LONG
                ).show()
            }
        }, 1000)
    }
    private fun initLineChart() {
        Log.d("methods ", "methods -> initChart")
        var line = Line(mPointValues).setColor(Color.parseColor("#FFCD41")) //折线的颜色#FFCD41
        var lines: MutableList<Line> = ArrayList()
        line.shape = ValueShape.CIRCLE //折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.SQUARE）
        line.isCubic = true //曲线是否平滑
        //	    line.setStrokeWidth(3);//线条的粗细，默认是3
        line.isFilled = true //是否填充曲线的面积
        line.setHasLabels(true) //曲线的数据坐标是否加上备注
        //		line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true) //是否用直线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true) //是否显示圆点 如果为false 则没有原点只有点显示
        lines.add(line)
        val data = LineChartData()
        data.lines = lines
        //坐标轴
        val axisX = Axis() //X轴
        axisX.setHasTiltedLabels(true) //X轴下面坐标轴字体是斜的显示还是直的，true是斜的显示
        //	    axisX.setTextColor(Color.WHITE);  //设置字体颜色
        axisX.textColor = Color.parseColor("#FFFFFF") //灰色
        //	    axisX.setName("未来几天的天气");  //表格名称
        axisX.textSize = 11 //设置字体大小
        axisX.maxLabelChars = 1 //1<- more fit  // less fit -> 7
        axisX.values = mAxisXValues //填充X轴的坐标名称
        axisX.setHasTiltedLabels(false)
        data.axisXBottom = axisX //x 轴在底部
        //	    data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true) //x 轴分割线
        val axisY = Axis() //Y轴
        axisY.name = "power output [watts]" // Y axis name
        axisY.textSize = 11 //设置字体大小
        data.axisYLeft = axisY //Y轴设置在左边
        //data.setAxisYRight(axisY);  //y轴设置在右边
//设置行为属性，支持缩放、滑动以及平移
        lineChart?.setInteractive(true)
        lineChart?.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL) //缩放类型，水平
        lineChart?.setMaxZoom(1.toFloat()) //缩放比例
        lineChart?.setLineChartData(data)
        lineChart?.setVisibility(View.VISIBLE)
        /* ** Примечание: следующие 7 и 10 просто представляют число для аналогии
        * Это тяжелая работа для парня Нимы! ! ! Смотрите (http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2);
        * Следующие предложения могут установить количество отображаемых данных оси X (0-7 данных по оси X). Когда количество точек данных меньше (29), оно уменьшается до предельного гелограмма. По умолчанию все отображения. Когда количество точек данных больше (29),
        * Если вы не установите axisX.setMaxLabelChars (int count), он автоматически адаптирует количество данных, которое может отображаться на оси X, в максимально возможной степени.
        * Если установлен axisX.setMaxLabelChars (int count),
        * Проверка 33 точек данных, если axisX.setMaxLabelChars (10); 10 внутри больше, чем v.right = 7; 7 внутри, тогда
        * Вначале 7 данных отображаются на оси X, а затем при увеличении будет гарантировано, что количество осей X будет больше 7 и меньше 10
        * Если в v.right = 7 меньше 7, я чувствую, что эти два предложения кажутся недействительными!
        * И ось Y автоматически устанавливает верхний предел оси Y в соответствии с размером данных
        * Если вы не установите v.right = 7, в этом предложении диаграмма будет отображать все данные в максимально возможной степени в начале, а интерактивность слишком низкая
         */
        val v = Viewport(lineChart?.getMaximumViewport())
        v.left = 0f
        v.right = 14f
        lineChart?.setCurrentViewport(v)
    }

    /**
     * X 轴的显示
     */
    private fun getAxisXLables() {
        for (i in date2.indices) {
            if (mAxisXValues.size <21){
                mAxisXValues.add(AxisValue(i.toFloat()).setLabel(date2[i]))
            }

        }
    }

    /**
     * Y
     */
    private fun getAxisPoints() {
        for (i in score2.indices) {
            if (mPointValues.size <21){
                mPointValues.add(PointValue(i.toFloat(), score2[i].toFloat()))
            }

        }
    }



}


