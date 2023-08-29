package com.pochi.nogletest.ui.a

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import org.json.JSONObject


class AViewModel : ViewModel() {

    data class Data(
        val name: String,
        var price: String,
    )

    companion object {
        private lateinit var _aFragment: AFragment

        private val _spotData = MediatorLiveData<ArrayList<Data>>().apply {
            value = ArrayList()
        }
        private val _futureData = MediatorLiveData<ArrayList<Data>>().apply {
            value = ArrayList()
        }
    }

    fun setAFragment(aFragment: AFragment) {
        _aFragment = aFragment
    }

    fun setData(data: List<Map<String, Any>>) {
        _spotData.value?.clear()
        _futureData.value?.clear()

        val newSpotData = ArrayList<Data>()
        val newFutureData = ArrayList<Data>()
        for (d in data) {
            if (d["future"] != null && d["future"] is Boolean) {
                val isFuture = d["future"] as Boolean
                if (!isFuture) {
                    newSpotData.add(Data(d["symbol"] as String, "0"))
                } else {
                    newFutureData.add(Data(d["symbol"] as String, "0"))
                }
            }
        }
        _spotData.value?.addAll(newSpotData)
        _futureData.value?.addAll(newFutureData)
    }

    fun setPrice(priceStr: String) {
        if (priceStr.isEmpty()) {
            return
        }
        val newSpotData = _spotData.value?.let { ArrayList<Data>(it) }
        val newFutureData = _futureData.value?.let { ArrayList<Data>(it) }

        val jsonObj = JSONObject(priceStr)
        if (jsonObj.has("data")) {
            val dataObj = jsonObj.getJSONObject("data")
            //
            for (d in newSpotData!!) {
                val key = "${d.name}_1"
                if (dataObj.has(key)) {
                    val keyObj = dataObj.optJSONObject(key)
                    val price = keyObj!!.optString("price")
                    d.price = price
                }
            }
            _spotData.value = newSpotData

            for (d in newFutureData!!) {
                val key = "${d.name}_1"
                if (dataObj.has(key)) {
                    val keyObj = dataObj.optJSONObject(key)
                    val price = keyObj!!.getString("price")
                    d.price = price
                }
            }
            _futureData.value = newFutureData
        }
    }

    fun getSpotData() = _spotData

    fun getFutureData() = _futureData
}