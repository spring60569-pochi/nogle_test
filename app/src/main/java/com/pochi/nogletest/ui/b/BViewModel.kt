package com.pochi.nogletest.ui.b

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "B"
    }
    val text: LiveData<String> = _text
}