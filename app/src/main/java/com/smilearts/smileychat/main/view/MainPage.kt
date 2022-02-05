package com.smilearts.smileychat.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.smilearts.smileychat.R
import com.smilearts.smileychat.databinding.MainPageBinding
import com.smilearts.smileychat.main.adapter.FriendsPageAdapter
import com.smilearts.smileychat.main.common.MainPageBase
import com.smilearts.smileychat.main.fragment.MainChatFrag
import com.smilearts.smileychat.main.fragment.ProfileFrag
import com.smilearts.smileychat.main.fragment.friednspage.FriendsPageFrag
import com.smilearts.smileychat.main.viewmodel.MainViewModel
import com.smilearts.smileychat.repository.RepositoryUtil
import com.smilearts.smileychat.utils.AppUtil
import com.smilearts.smileychat.utils.TempData

class MainPage : AppCompatActivity() {

    private lateinit var binding: MainPageBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var repositoryUtil: RepositoryUtil
    private lateinit var friendsPageAdapter: FriendsPageAdapter

    private val navigationClick = object: BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.bot_main_chat_menu -> {
                    viewModel.pageStateLive.postValue(1)
                    return true
                }
                R.id.bot_main_friend_menu -> {
                    viewModel.pageStateLive.postValue(2)
                    return true
                }
                R.id.bot_main_profile_menu -> {
                    viewModel.pageStateLive.postValue(3)
                    return true
                }
                R.id.bot_main_settings_menu -> {
                    viewModel.pageStateLive.postValue(4)
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
        setSupportActionBar(binding.mainChatToolbar)
        binding.mainPageBottomNavigationView.setOnNavigationItemSelectedListener(navigationClick)
        repositoryUtil = RepositoryUtil(this, TempData(this),AppUtil().scope)
        viewModel = ViewModelProvider(this , MainPageBase(this,repositoryUtil,this)).get(MainViewModel::class.java)
        viewModel.pageStateLive.postValue(1)
    }

    override fun onStart() {
        super.onStart()


    }

    override fun onResume() {
        super.onResume()

        viewModel.pageStateLive.observe(this , {
            if (it == 2) {
                supportActionBar?.hide()
            } else {
                supportActionBar?.show()
            }
            when(it) {
                1 -> {
                    viewModel.pagePosition = 1
                    startFrag(MainChatFrag(viewModel))
                }
                2 -> {
                    viewModel.pagePosition = 2
                    friendsPageAdapter = FriendsPageAdapter(supportFragmentManager , lifecycle , viewModel)
                    startFrag(FriendsPageFrag(viewModel , friendsPageAdapter))
                }
                3 -> {
                    viewModel.pagePosition = 3
                    startFrag(ProfileFrag(viewModel))
                }
                4 -> {
                    viewModel.pagePosition = 4
                }
            }
        })

    }

    private fun startFrag(fragment: Fragment) {
        val frag: FragmentTransaction = supportFragmentManager.beginTransaction()
        frag.replace(R.id.main_page_frameLayout, fragment)
        frag.addToBackStack(null)
        frag.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_page_menu , menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.main_menu_enquiry -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (viewModel.pagePosition != 1) {
            viewModel.pageStateLive.postValue(1)
            binding.mainPageBottomNavigationView.selectedItemId = R.id.bot_main_chat_menu
        } else {
            finish()
        }
    }

}