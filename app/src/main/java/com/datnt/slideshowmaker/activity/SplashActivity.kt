package com.datnt.slideshowmaker.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.datnt.slideshowmaker.R
import com.datnt.slideshowmaker.SplashViewModel
import com.datnt.slideshowmaker.databinding.SplashScreenBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: SplashScreenBinding


    private var i: Int = 0
    private lateinit var viewModel: SplashViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.splash_screen)
        viewModel = ViewModelProvider(this)[SplashViewModel::class.java]
        setProgessbar()

    }

    fun setProgessbar() {
        Handler(Looper.getMainLooper()).postDelayed({
            i += 20
            binding.progressCircular.progress = i
            if (i == 100) {
                openHomeScreen()
            }
            setProgessbar()
        }, 200)
    }

    private fun openHomeScreen() {
       val intent = Intent(this, MainActivity::class.java).apply {
           addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
       }
        startActivity(intent)
    }


}