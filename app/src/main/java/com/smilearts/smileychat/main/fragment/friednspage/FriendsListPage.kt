package com.smilearts.smileychat.main.fragment.friednspage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.smilearts.smileychat.databinding.FragmentFriendListBinding
import com.smilearts.smileychat.main.adapter.ProfileListAdapter
import com.smilearts.smileychat.main.callback.ProfileCallBack
import com.smilearts.smileychat.main.model.RegisterModel
import com.smilearts.smileychat.main.viewmodel.MainViewModel

class FriendsListPage(
    private val viewModel: MainViewModel
) : Fragment() {

    private lateinit var binding: FragmentFriendListBinding
    private lateinit var adapter: ProfileListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFriendListBinding.inflate(layoutInflater)
        binding.friendslist.setHasFixedSize(true)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.repositoryUtil.profileRepository.getFriendsList().observe(viewModel.lifecycleOwner , {
            if (it != null) {
                adapter = ProfileListAdapter(viewModel.activity , it , object : ProfileCallBack {
                    override fun chooseProfile(model: RegisterModel) {
                        viewModel.repositoryUtil.requestRepository.sendRequest(model)
                    }
                })
                binding.friendslist.adapter = adapter
            }
        })

    }

}