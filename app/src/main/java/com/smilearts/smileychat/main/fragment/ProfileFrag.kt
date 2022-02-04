package com.smilearts.smileychat.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.smilearts.smileychat.databinding.FragmentProfilePageBinding
import com.smilearts.smileychat.main.viewmodel.MainViewModel

class ProfileFrag(viewModel: MainViewModel) : Fragment() {

    private lateinit var binding: FragmentProfilePageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfilePageBinding.inflate(layoutInflater)
        return binding.root
    }

}