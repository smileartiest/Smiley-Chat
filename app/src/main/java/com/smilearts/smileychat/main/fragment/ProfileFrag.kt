package com.smilearts.smileychat.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.smilearts.smileychat.databinding.FragmentProfilePageBinding
import com.smilearts.smileychat.main.viewmodel.MainViewModel
import com.smilearts.smileychat.utils.AppUtil
import com.smilearts.smileychat.utils.CountryName

class ProfileFrag(private val viewModel: MainViewModel) : Fragment() {

    private lateinit var binding: FragmentProfilePageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfilePageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.repositoryUtil.profileRepository.getProfile().observe(viewModel.lifecycleOwner , {
            if (it != null) {
                viewModel.repositoryUtil.tempData.setProfile(it)
                Glide.with(requireContext()).load(it.userProPicUrl).into(binding.profileProPic)
                binding.profileName.text = it.userName
                binding.profilePhno.text = "${AppUtil().getCountryCode(it.userCountryCode)} ${it.userID}"
            }
        })

    }

}