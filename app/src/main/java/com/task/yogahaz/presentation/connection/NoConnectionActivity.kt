package com.task.yogahaz.presentation.connection

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
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





}