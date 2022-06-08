package com.android.mobile.weatherapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.mobile.weatherapp.databinding.ItemViewWeatherBinding
import com.android.mobile.weatherapp.model.TemperatureData

class WeatherAdapter(val context: Context, var list: ArrayList<TemperatureData>) : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = ItemViewWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as WeatherViewHolder).bindHolder(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}