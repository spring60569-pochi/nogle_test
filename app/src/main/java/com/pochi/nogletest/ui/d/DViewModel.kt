package com.pochi.nogletest.ui.d

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "D"
    }
    val text: LiveData<String> = _text
}