package com.example.iot_pr

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.json.JSONArray
import org.json.JSONObject

class SharedViewModel : ViewModel() {
    val IP = MutableLiveData<String>("192.168.1.227")
    val temperature:MutableLiveData<MutableList<Float>> = MutableLiveData(mutableListOf<Float>())
    val preasure :MutableLiveData<MutableList<Float>> = MutableLiveData(mutableListOf<Float>())
    val humidity :MutableLiveData<MutableList<Float>> = MutableLiveData(mutableListOf<Float>())
    val pitch :MutableLiveData<MutableList<Float>> = MutableLiveData(mutableListOf<Float>())
    val roll :MutableLiveData<MutableList<Float>> = MutableLiveData(mutableListOf<Float>())
    val yaw :MutableLiveData<MutableList<Float>> = MutableLiveData(mutableListOf<Float>())
    val max_list_size:MutableLiveData<Int> =MutableLiveData(10)
    val pixels:MutableLiveData<JSONArray> = MutableLiveData(JSONArray())
    val Tp:MutableLiveData<Int> =MutableLiveData(500)
    val Joy:MutableLiveData<List<Int>> =MutableLiveData(listOf<Int>(0, 0))
    fun Set_Ip(item: String) {
        IP.value = item
    }
    fun set_max_list_size(int: Int)
    {
        max_list_size.value=int
    }
    fun set_pixels(json: JSONArray)
    {
        pixels.value=json
    }
    fun set_Tp(new_Tp:Int)
    {
        Tp.value=new_Tp
    }
    fun set_Joy(new_Joy:List<Int>)
    {
        Joy.value=new_Joy
    }
    fun add_temperature(item:Float)
    {
        temperature.value =temperature.value //bez tego paskudztwa nie dziala poniewaz teoretycznie .value sie nie zmienia wiec obserwatory nie dzialaja
        temperature.value?.add(item)
        while(temperature.value!!.size > max_list_size.value!! )
        {
            temperature.value!!.removeAt(0)
        }
    }
    fun add_preasure(item:Float)
    {
        preasure.value =preasure.value //bez tego paskudztwa nie dziala poniewaz teoretycznie .value sie nie zmienia wiec obserwatory nie dzialaja
        preasure.value?.add(item)
        while(preasure.value!!.size > max_list_size.value!!)
        {
            preasure.value!!.removeAt(0)
        }
    }
    fun add_humidity(item:Float)
    {
        humidity.value =humidity.value //bez tego paskudztwa nie dziala poniewaz teoretycznie .value sie nie zmienia wiec obserwatory nie dzialaja
        humidity.value?.add(item)
        while(humidity.value!!.size > max_list_size.value!!)
        {
            humidity.value!!.removeAt(0)
        }
    }
    fun add_pitch(item:Float)
    {
        pitch.value =pitch.value //bez tego paskudztwa nie dziala poniewaz teoretycznie .value sie nie zmienia wiec obserwatory nie dzialaja
        pitch.value?.add(item)
        while(pitch.value!!.size > max_list_size.value!!)
        {
            pitch.value!!.removeAt(0)
        }
    }
    fun add_roll(item:Float)
    {
        roll.value =roll.value //bez tego paskudztwa nie dziala poniewaz teoretycznie .value sie nie zmienia wiec obserwatory nie dzialaja
        roll.value?.add(item)
        while(roll.value!!.size > max_list_size.value!!)
        {
            roll.value!!.removeAt(0)
        }
    }
    fun add_yaw(item:Float)
    {
        yaw.value =yaw.value //bez tego paskudztwa nie dziala poniewaz teoretycznie .value sie nie zmienia wiec obserwatory nie dzialaja
        yaw.value?.add(item)
        while(yaw.value!!.size > max_list_size.value!!)
        {
            yaw.value!!.removeAt(0)
        }
    }
}