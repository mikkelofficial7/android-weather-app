package com.android.mobile.weatherapp

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.plusAssign
import androidx.navigation.ui.setupWithNavController
import com.android.mobile.weatherapp.databinding.ActivityMainBinding
import com.android.mobile.weatherapp.navigator.KeepStateNavigator
import com.android.mobile.weatherapp.receiver.NetworkReceiver


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val receiver: NetworkReceiver = NetworkReceiver(this)
    private var isRegisteredReceiver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        isRegisteredReceiver = true

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.title_home)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // get fragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)!!

        // setup custom navigator
        val navigator = KeepStateNavigator(this, navHostFragment.childFragmentManager, R.id.nav_host_fragment_activity_main)
        navController.navigatorProvider += navigator
        navController.setGraph(R.navigation.mobile_navigation)

        navView.setupWithNavController(navController)
    }

    fun showNoNetworkUI() {
        binding.viewNoNetwork.visibility = View.VISIBLE
    }

    fun hideNoNetworkUI() {
        binding.viewNoNetwork.visibility = View.GONE
    }

    override fun onDestroy() {
        if (isRegisteredReceiver) {
            unregisterReceiver(receiver)
            isRegisteredReceiver = false
        }

        super.onDestroy()
    }
}