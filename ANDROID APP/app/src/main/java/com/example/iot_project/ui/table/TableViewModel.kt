package com.example.iot_project.ui.table

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TableViewModel : ViewModel() {
    val IP = MutableLiveData<String>()
    fun Set_Ip(item: String) {
        IP.value = item
    }
}