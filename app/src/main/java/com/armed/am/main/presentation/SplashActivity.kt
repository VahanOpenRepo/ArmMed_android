package com.armed.am.main.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.armed.am.base.BaseActivity
import com.armed.am.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity<ActivitySplashBinding>({ ActivitySplashBinding.inflate(it)}) {

    private val SPLASH_TIME_DURATION = 2000L

    override fun setupView(savedInstanceState: Bundle?, binding: ActivitySplashBinding) =Unit

    private val handler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }

    private val runnable: Runnable by lazy {
        Runnable {
            showMainScreen()
        }
    }

    override fun onResume() {
        super.onResume()
        startTimer()
    }

    override fun onPause() {
        super.onPause()
        stopTimer()
    }

    private fun startTimer() {
        handler.postDelayed(runnable, SPLASH_TIME_DURATION)
    }

    private fun stopTimer() {
        handler.removeCallbacks(runnable)
    }

    private fun showMainScreen() {
        // This method will be executed once the timer is over
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)

        finish()
    }

}