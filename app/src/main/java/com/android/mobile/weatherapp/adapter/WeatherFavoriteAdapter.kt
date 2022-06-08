package com.android.mobile.weatherapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.mobile.weatherapp.databinding.ItemViewWeatherFavoriteBinding
import com.android.mobile.weatherapp.model.CityData

class WeatherFavoriteAdapter(
    val context: Context,
    var list: List<CityData>,
    var onGotoDetail: (CityData) -> Unit = {}
) : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = ItemViewWeatherFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherFavoriteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as WeatherFavoriteViewHolder).bindHolder(list[position], onGotoDetail)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}