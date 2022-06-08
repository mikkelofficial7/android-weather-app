package com.android.mobile.weatherapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.android.mobile.weatherapp.DetailActivity
import com.android.mobile.weatherapp.R
import com.android.mobile.weatherapp.constant.Base
import com.android.mobile.weatherapp.databinding.FragmentHomeBinding
import com.android.mobile.weatherapp.model.CityData
import com.android.mobile.weatherapp.model.CommonError
import com.android.mobile.weatherapp.room.RoomDbBuilder
import com.android.mobile.weatherapp.state.FindWeatherState
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private var searchTimer: Timer = Timer()
    private var searchDelay = 2000L

    private var cityData: CityData? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        showCityList()
        setupListener()
        return root
    }

    private fun showCityList() {
        homeViewModel.findCityLiveData.observe(viewLifecycleOwner, Observer { state ->
            hideLoading()

            when(state) {
                is FindWeatherState.successLoad -> {
                    setupCityData(state.dataCity)
                }
                is FindWeatherState.failedLoad -> {
                    setupError(state.commonErrorCity)
                }
            }
        })
    }

    private fun setupListener() {
        _binding?.etSearch?.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                if(s == null || s.length < 2) {
                    setupError()
                    return
                }

                searchTimer.cancel()
                searchTimer = Timer()
                searchTimer.schedule(
                    object : TimerTask() {
                        override fun run() {
                            lifecycleScope.launch {
                                showLoading()
                                searchCity(s.toString())
                            }
                        }
                    },
                    searchDelay
                )
            }

        })

        _binding?.include?.ivFavorite?.setOnClickListener {
            context?.let { context ->
                val lastCityData = getLatestCityOnFavorite()

                lastCityData?.let {
                    setFavorite(R.drawable.ic_remove_favorite)
                    RoomDbBuilder.init(context)?.cityDao()?.deleteFavoriteById(cityData?.cityId)
                } ?: kotlin.run {
                    setFavorite(R.drawable.ic_add_favorite)
                    RoomDbBuilder.init(context)?.cityDao()?.insertCity(cityData)
                }
            }
        }

        _binding?.include?.tvCityName?.setOnClickListener {
            cityData?.let { city -> goToDetail(city) }
        }
    }

    suspend fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    suspend fun searchCity(lastCityName: String) {
        homeViewModel.findCity(cityName = lastCityName)
    }

    private fun setupCityData(cityData: CityData) {
        context?.let { context ->
            this.cityData = cityData

            binding.tvError.alpha = 0f
            binding.viewCity.alpha = 1f

            binding.include.tvCityName.text = cityData.cityName
            binding.include.tvLatLong.text = getString(R.string.lat_long, cityData.coord?.lat.toString(), cityData.coord?.lon.toString())
            binding.include.tvTemp.text = getString(R.string.temp_format, cityData.detailWeather?.temp.toString())
            binding.include.tvTempDetail.text = getString(
                R.string.temp_format_detail,
                cityData.detailWeather?.temp_min.toString(),
                cityData.detailWeather?.temp_max.toString(),
                cityData.detailWeather?.pressure.toString(),
                cityData.detailWeather?.humidity.toString()
            )

            val firstWeather = cityData.listWeather?.first()

            binding.include.tvWeather.text = "${firstWeather?.main} (${firstWeather?.description})"

            val imgUrl = getString(R.string.path_image, Base.BASE_IMAGE_URL, firstWeather?.icon)
            Glide.with(context)
                .load(imgUrl)
                .into(binding.include.ivWeather)

            val lastCityData = getLatestCityOnFavorite()

            lastCityData?.let {
                setFavorite(R.drawable.ic_add_favorite)
            } ?: kotlin.run {
                setFavorite(R.drawable.ic_remove_favorite)
            }
        }
    }

    private fun setupError(commonError: CommonError = CommonError()) {
        binding.viewCity.alpha = 0f
        binding.tvError.alpha = 1f
        binding.tvError.text = commonError.errorMessage.orEmpty()
    }

    private fun setFavorite(drawable: Int) {
        binding.include.ivFavorite.setImageResource(drawable)
    }

    private fun getLatestCityOnFavorite() : CityData? {
        return context?.let {
            RoomDbBuilder.init(it)?.cityDao()?.getFavoriteCityById(cityData?.cityId)
        }
    }

    private fun goToDetail(cityData: CityData) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.TAG_WEATHER_DETAIL_LAT, cityData.coord?.lat)
        intent.putExtra(DetailActivity.TAG_WEATHER_DETAIL_LONG, cityData.coord?.lon)
        startActivityForResult(intent, DetailActivity.TAG_WEATHER_DETAIL_CODE)
    }

    override fun onDestroyView() {
        searchTimer.cancel()
        super.onDestroyView()
        _binding = null
    }
}