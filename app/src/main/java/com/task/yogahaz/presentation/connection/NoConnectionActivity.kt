package com.task.paymob.ui.no_connection

import android.graphics.Color
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import com.task.yogahaz.R
import com.task.yogahaz.databinding.ActivityNoConnectionBinding
import com.task.yogahaz.utils.network.ConnectivityObserver
import com.task.yogahaz.utils.network.NetworkConnectivityObserver
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NoConnectionActivity : AppCompatActivity() {

    private val networkConnectivityObserver by lazy { NetworkConnectivityObserver(this) }
    private lateinit var binding: ActivityNoConnectionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoConnectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        lifecycleScope.launch {
            checkNetworkObserver()
        }
        initClick(binding)
        handleBars()
        updateStatusBarColor()
    }

    private fun initClick(binding: ActivityNoConnectionBinding) {
        binding.btnRetry.setOnClickListener {
            //checkConnection(networkUtils)
            lifecycleScope.launch {
                checkNetworkObserver()
            }
        }
    }

    private suspend fun checkNetworkObserver() {
        networkConnectivityObserver.observe().collectLatest {
            when (it) {
                ConnectivityObserver.Status.Available -> {
                    finish()
                }

                else -> {}
            }
        }
    }


    private fun handleBars() {


        val window = this.window
        val decorView = window.decorView

        WindowInsetsControllerCompat(window, decorView).apply {
            // Show the status bar
            show(WindowInsetsCompat.Type.statusBars())
            // Show the navigation bar
            show(WindowInsetsCompat.Type.navigationBars())
        }


    }


    private fun updateStatusBarColor() {
        val hexColor = Integer.toHexString(ContextCompat.getColor(this, R.color.white))
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.parseColor("#$hexColor")
    }
}