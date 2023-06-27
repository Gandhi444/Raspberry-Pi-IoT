package com.example.iot_project.ui.options

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OptionsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "test"
    }
    val text: LiveData<String> = _text
}