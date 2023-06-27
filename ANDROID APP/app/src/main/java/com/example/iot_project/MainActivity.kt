package com.example.iot_project

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.iot_pr.SharedViewModel
import com.example.iot_project.databinding.ActivityMainBinding
import org.json.JSONObject
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity() {
    private var requestTimer:Timer?=null
    private var handler=Handler()
    private var requestTimerTask: TimerTask?=null
    private lateinit var ip:String
    lateinit var  model:SharedViewModel
    private  var Tp:Int=500

    lateinit var queue:RequestQueue

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_Table, R.id.nav_Graphs, R.id.nav_Pixels,R.id.nav_Options
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        model = ViewModelProvider(this).get(SharedViewModel::class.java)
        queue = Volley.newRequestQueue(this)
        model.IP.observe(this, Observer {
            // updating data in displayMsg
            ip= it
            requestTimer?.cancel()
            requestTimer?.purge()
            requestTimer=null
            startRequestTimer()
        })
        model.Tp.observe(this, Observer {
            Tp=it
            requestTimer?.cancel()
            requestTimer?.purge()
            requestTimer=null
            startRequestTimer()
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    private fun sendGetRequest()
    {
        var url="http://"+ip+"/get_all.php"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response -> responseHandler(response)}
            ,{err-> Log.d("err",err.toString())})
        queue.add(stringRequest)
    }
    private fun responseHandler(response:String) {
        var json = JSONObject(response)
        model.add_temperature(json.getString("t").toFloat())
        model.add_humidity(json.getString("h").toFloat())
        model.add_preasure(json.getString("p").toFloat())
        model.add_pitch(json.getString("pitch").toFloat())
        model.add_yaw(json.getString("yaw").toFloat())
        model.add_roll(json.getString("roll").toFloat())
        model.set_pixels(json.getJSONArray("pixels"))
        var Joy=json.getJSONObject("joy")
        model.set_Joy(listOf<Int>(Joy.getString("x").toInt(),Joy.getString("y").toInt()))
    }
    private fun startRequestTimer() {
        if (requestTimer == null) {
            // set a new Timer
            requestTimer = Timer()

            // initialize the TimerTask's job
            initializeRequestTimerTask()
            requestTimer!!.schedule(requestTimerTask, 0, Tp.toLong())

        }
    }

    private fun initializeRequestTimerTask() {
        requestTimerTask = object : TimerTask() {
            override fun run() {
                handler.post(Runnable { sendGetRequest() })
            }
        }
    }
}