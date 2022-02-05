package com.smilearts.smileychat.main.fragment.friednspage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.smilearts.smileychat.databinding.FragmentFriendRequestListBinding
import com.smilearts.smileychat.main.viewmodel.MainViewModel

class RequestPageFrag(
    private val viewModel: MainViewModel
) : Fragment() {

    private lateinit var binding: FragmentFriendRequestListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFriendRequestListBinding.inflate(layoutInflater)
        return binding.root
    }

}