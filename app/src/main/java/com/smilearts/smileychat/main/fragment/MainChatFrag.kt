package com.smilearts.smileychat.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.smilearts.smileychat.databinding.FragmentChatPageBinding
import com.smilearts.smileychat.main.adapter.MainChatList
import com.smilearts.smileychat.main.viewmodel.MainViewModel

class MainChatFrag(private val viewModel: MainViewModel) : Fragment() {

    private lateinit var binding: FragmentChatPageBinding
    private lateinit var adapter: MainChatList

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatPageBinding.inflate(layoutInflater)
        binding.fragMainChatList.setHasFixedSize(true)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.repositoryUtil.chatListRepository.getChatList().observe(viewModel.lifecycleOwner , {
            if (it != null) {
                binding.fragMainNoChat.visibility = View.GONE
                adapter = MainChatList(requireActivity() , it )
                binding.fragMainChatList.adapter = adapter
            } else {
                binding.fragMainNoChat.visibility = View.VISIBLE
            }
        })

    }

}