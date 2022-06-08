package com.android.mobile.weatherapp

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.android.mobile.weatherapp.adapter.WeatherAdapter
import com.android.mobile.weatherapp.databinding.ActivityDetailBinding
import com.android.mobile.weatherapp.extension.next3DaysTimeStamp
import com.android.mobile.weatherapp.model.CommonError
import com.android.mobile.weatherapp.model.TemperatureData
import com.android.mobile.weatherapp.receiver.NetworkReceiver
import com.android.mobile.weatherapp.state.FindWeatherDetailState
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: ActivityViewModel
    private var adapterWeather: WeatherAdapter? = null

    private var cityLat: Double = 0.0
    private var cityLon: Double = 0.0

    private val receiver: NetworkReceiver = NetworkReceiver(this)
    private var isRegisteredReceiver = false

    companion object {
        const val TAG_WEATHER_DETAIL_LAT = "tag_weather_detail_lat"
        const val TAG_WEATHER_DETAIL_LONG = "tag_weather_detail_long"
        const val TAG_WEATHER_DETAIL_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        isRegisteredReceiver = true

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ActivityViewModel::class.java)

        supportActionBar?.title = getString(R.string.title_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        cityLat = intent.getDoubleExtra(TAG_WEATHER_DETAIL_LAT, 0.0)
        cityLon = intent.getDoubleExtra(TAG_WEATHER_DETAIL_LONG, 0.0)

        showCityDetail()
        getCityDetail(cityLat, cityLon)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        if (isRegisteredReceiver) {
            unregisterReceiver(receiver)
            isRegisteredReceiver = false
        }

        super.onDestroy()
    }

    private fun getCityDetail(cityLat: Double, cityLong: Double) {
        hideError()
        showLoading()
        viewModel.findCity(cityLat, cityLong)
    }

    private fun showCityDetail() {
        viewModel.findCityLiveData.observe(this, Observer { state ->
            hideError()
            hideLoading()

            when(state) {
                is FindWeatherDetailState.successLoad -> {
                    setupListWeather(state.dataCity.temperatureList)
                }
                is FindWeatherDetailState.failedLoad -> {
                    showError(state.commonErrorCity)
                }
            }
        })
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showError(error: CommonError? = CommonError()) {
        binding.tvError.visibility = View.VISIBLE
        binding.tvError.text = error?.errorMessage.orEmpty()
    }

    private fun hideError() {
        binding.tvError.visibility = View.GONE
    }

    private fun setupListWeather(listTemp: ArrayList<TemperatureData>?) {
        adapterWeather = WeatherAdapter(this, filterListTemp(listTemp ?: arrayListOf()))
        binding.rvWeather.layoutManager = GridLayoutManager(this, 2)
        binding.rvWeather.adapter = adapterWeather
    }

    private fun filterListTemp(listTemp: ArrayList<TemperatureData>?) : ArrayList<TemperatureData> {
        val c = Calendar.getInstance().time
        val df = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val formattedDate = "${df.format(c)} 00:00:00"

        val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        val date = formatter.parse(formattedDate) as Date

        return listTemp?.filter { it.timestamp <= date.time.next3DaysTimeStamp() } as ArrayList<TemperatureData>
    }

    fun showNoNetworkUI() {
        binding.viewNoNetwork.visibility = View.VISIBLE
    }

    fun hideNoNetworkUI() {
        binding.viewNoNetwork.visibility = View.GONE
    }
}