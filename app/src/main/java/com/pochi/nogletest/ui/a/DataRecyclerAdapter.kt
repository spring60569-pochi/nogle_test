package com.pochi.nogletest.ui.a

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pochi.nogletest.R

class DataRecyclerAdapter(
    private val viewModel: AViewModel,
    private val dataType: String
) : RecyclerView.Adapter<DataRecyclerAdapter.ResultViewHolder>() {

    private val TAG = "DataRecyclerAdapter"

    class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.name_text)
        val priceText: TextView = itemView.findViewById(R.id.price_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.market_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        if (dataType == "Spot") {
            holder.nameText.text = viewModel.getSpotData().value!![position].name
            holder.priceText.text = viewModel.getSpotData().value!![position].price
        } else {
            holder.nameText.text = viewModel.getFutureData().value!![position].name
            holder.priceText.text = viewModel.getFutureData().value!![position].price
        }
    }

    override fun getItemCount(): Int {
        if (dataType == "Spot") {
            return viewModel.getSpotData().value!!.size
        } else {
            return viewModel.getFutureData().value!!.size
        }
    }
}