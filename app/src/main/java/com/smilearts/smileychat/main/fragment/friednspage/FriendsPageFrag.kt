package com.smilearts.smileychat.main.fragment.friednspage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.smilearts.smileychat.databinding.FragmentFriendsPageBinding
import com.smilearts.smileychat.main.adapter.FriendsPageAdapter
import com.smilearts.smileychat.main.viewmodel.MainViewModel

class FriendsPageFrag(
    val viewModel: MainViewModel,
    private val friendsPageAdapter: FriendsPageAdapter
) : Fragment() {

    private lateinit var binding: FragmentFriendsPageBinding

    private val pageArray = arrayOf(
        "Friends Request",
        "Friends List"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFriendsPageBinding.inflate(layoutInflater)
        binding.friendsPageViewpage.adapter = friendsPageAdapter
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        TabLayoutMediator(binding.friendsPageTabLayout , binding.friendsPageViewpage) { tab , position ->
            when (position) {
                0 -> {
                    val badge = tab.orCreateBadge
                    viewModel.repositoryUtil.requestRepository.newRequestCount.observe(viewModel.lifecycleOwner , {
                        if (it != null) {
                            badge.number = it
                        }
                    })
                }
                1 -> {
                    val badge = tab.orCreateBadge
                    viewModel.repositoryUtil.profileRepository.friendsCountLive.observe(viewModel.lifecycleOwner , {
                        if (it != 0) {
                            badge.number = it
                        }
                    })
                }
            }
            tab.text = pageArray[position]
        }.attach()

    }

}