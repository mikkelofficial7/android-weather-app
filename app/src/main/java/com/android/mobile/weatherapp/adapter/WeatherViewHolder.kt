package com.android.mobile.weatherapp.adapter

import androidx.recyclerview.widget.RecyclerView
import com.android.mobile.weatherapp.R
import com.android.mobile.weatherapp.constant.Base
import com.android.mobile.weatherapp.databinding.ItemViewWeatherBinding
import com.android.mobile.weatherapp.model.TemperatureData
import com.bumptech.glide.Glide

class WeatherViewHolder (private val binding: ItemViewWeatherBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bindHolder(tempData: TemperatureData) {
        val firstWeather = tempData.listWeather?.first()
        val imgUrl = binding.root.context.getString(R.string.path_image, Base.BASE_IMAGE_URL, firstWeather?.icon)

        Glide.with(binding.root.context).load(imgUrl).into(binding.ivWeather)
        binding.tvWeather.text = "${firstWeather?.main} (${firstWeather?.description})"
        binding.tvTemp.text = binding.root.context.getString(R.string.temp_format, tempData.detailWeather?.temp.toString())
        binding.tvHumidity.text = binding.root.context.getString(R.string.humidity_format, tempData.detailWeather?.humidity.toString())

        binding.tvWind.text = binding.root.context.getString(R.string.wind_format_detail,
            tempData.wind?.speed.toString(),
            tempData.wind?.deg.toString(),
            tempData.wind?.gust.toString(),
        )
    }
}