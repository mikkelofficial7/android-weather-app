package com.android.mobile.weatherapp.adapter

import androidx.recyclerview.widget.RecyclerView
import com.android.mobile.weatherapp.R
import com.android.mobile.weatherapp.constant.Base
import com.android.mobile.weatherapp.databinding.ItemViewWeatherFavoriteBinding
import com.android.mobile.weatherapp.model.CityData
import com.bumptech.glide.Glide

class WeatherFavoriteViewHolder (private val binding: ItemViewWeatherFavoriteBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bindHolder(cityData: CityData, onGotoDetail: (CityData) -> Unit = {}) {
        binding.include.tvCityName.text = cityData.cityName
        binding.include.tvLatLong.text = binding.root.context.getString(R.string.lat_long, cityData.coord?.lat.toString(), cityData.coord?.lon.toString())
        binding.include.tvTemp.text = binding.root.context.getString(R.string.temp_format, cityData.detailWeather?.temp.toString())
        binding.include.tvTempDetail.text = binding.root.context.getString(
            R.string.temp_format_detail,
            cityData.detailWeather?.temp_min.toString(),
            cityData.detailWeather?.temp_max.toString(),
            cityData.detailWeather?.pressure.toString(),
            cityData.detailWeather?.humidity.toString()
        )

        val firstWeather = cityData.listWeather?.first()

        binding.include.tvWeather.text = "${firstWeather?.main} (${firstWeather?.description})"

        val imgUrl = binding.root.context.getString(R.string.path_image, Base.BASE_IMAGE_URL, firstWeather?.icon)

        Glide.with(binding.root.context)
            .load(imgUrl)
            .into(binding.include.ivWeather)

        binding.include.ivFavorite.setImageResource(R.drawable.ic_add_favorite)

        binding.include.tvCityName.setOnClickListener {
            onGotoDetail(cityData)
        }
    }
}