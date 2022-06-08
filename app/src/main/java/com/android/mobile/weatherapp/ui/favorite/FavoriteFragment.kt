package com.android.mobile.weatherapp.ui.favorite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.mobile.weatherapp.DetailActivity
import com.android.mobile.weatherapp.adapter.WeatherAdapter
import com.android.mobile.weatherapp.adapter.WeatherFavoriteAdapter
import com.android.mobile.weatherapp.databinding.FragmentFavoriteBinding
import com.android.mobile.weatherapp.model.CityData
import com.android.mobile.weatherapp.model.TemperatureData
import com.android.mobile.weatherapp.room.RoomDbBuilder

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private var adapterWeather: WeatherFavoriteAdapter? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        context?.let {
            hideEmptyList()

            val listFavorite = RoomDbBuilder.init(it)?.cityDao()?.getAllCity()
            loadAllFavorite(it, listFavorite)
        }

        return root
    }

    private fun loadAllFavorite(context: Context, listFav: List<CityData>?) {
        if(listFav == null || listFav.isEmpty()) {
            showEmptyList()
        }

        adapterWeather = WeatherFavoriteAdapter(context, listFav ?: listOf(), onGotoDetail = {
            goToDetail(it)
        })
        binding.rvFavorite.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rvFavorite.adapter = adapterWeather
    }

    private fun showEmptyList() {
        binding.tvNoFavorite.alpha = 1f
    }

    private fun hideEmptyList() {
        binding.tvNoFavorite.alpha = 0f
    }

    private fun goToDetail(cityData: CityData) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.TAG_WEATHER_DETAIL_LAT, cityData.coord?.lat)
        intent.putExtra(DetailActivity.TAG_WEATHER_DETAIL_LONG, cityData.coord?.lon)
        startActivityForResult(intent, DetailActivity.TAG_WEATHER_DETAIL_CODE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}