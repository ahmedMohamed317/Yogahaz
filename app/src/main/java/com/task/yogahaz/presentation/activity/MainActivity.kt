package com.task.yogahaz.presentation.activity

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.task.yogahaz.presentation.connection.NoConnectionActivity
import com.task.yogahaz.R
import com.task.yogahaz.utils.network.ConnectivityObserver
import com.task.yogahaz.utils.network.NetworkConnectivityObserver
import com.task.yogahaz.utils.base.BaseActivity
import com.task.yogahaz.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var navHostFragment: NavHostFragment
    private val networkConnectivityObserver by lazy { NetworkConnectivityObserver(this) }
    override fun getViewBinding(inflater: LayoutInflater) = ActivityMainBinding.inflate(inflater)


    override fun onCreateInit() {
        initViews()
        // disable dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        lifecycleScope.launch {
            try {
                // check network connection
                checkNetworkObserver()
            }catch (e :Exception){
                Timber.tag("TAG").d(e.message.toString())
            }
        }
    }

    private fun initViews() {
        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
            ?: return
    }

    private suspend fun checkNetworkObserver() {
        networkConnectivityObserver.observe().collectLatest {
            when (it) {
                ConnectivityObserver.Status.UnAvailable, ConnectivityObserver.Status.Losing, ConnectivityObserver.Status.Lost
                -> {
                    val intent = Intent(this, NoConnectionActivity::class.java)
                    startActivity(intent)
                }

                else -> {
                }
            }
        }
    }
}

