package com.smilearts.smileychat.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.smilearts.smileychat.databinding.LoginScreenBinding

class LoginScreen : AppCompatActivity() {

    private lateinit var binding: LoginScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}