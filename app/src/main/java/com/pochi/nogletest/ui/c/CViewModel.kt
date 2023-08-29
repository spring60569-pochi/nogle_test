package com.pochi.nogletest.ui.c

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "C"
    }
    val text: LiveData<String> = _text
}