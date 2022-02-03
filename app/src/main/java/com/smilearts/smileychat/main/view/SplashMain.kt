package com.smilearts.smileychat.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.smilearts.smileychat.R

class SplashMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activty_splase)

        Handler(Looper.myLooper()!!).postDelayed({
            startActivity(Intent(this , SmsOTP::class.java))
            finish()
        }, 3000)

    }
}