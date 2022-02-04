package com.smilearts.smileychat.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.smilearts.smileychat.R
import com.smilearts.smileychat.databinding.MainPageBinding
import com.smilearts.smileychat.main.viewmodel.MainViewModel

class MainPage : AppCompatActivity() {

    private lateinit var binding: MainPageBinding
    private lateinit var viewModel: MainViewModel

    private val navigationClick = object: BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.bot_main_chat_menu -> {
                    return true
                }
                R.id.bot_main_friend_menu -> {
                    return true
                }
                R.id.bot_main_profile_menu -> {
                    return true
                }
                R.id.bot_main_settings_menu -> {
                    return true
                }
            }
            return false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun startFrag(fragment: Fragment) {
        val frag: FragmentTransaction = supportFragmentManager.beginTransaction()
        frag.replace(R.id.main_page_frameLayout, fragment)
        frag.addToBackStack(null)
        frag.commit()
    }

}