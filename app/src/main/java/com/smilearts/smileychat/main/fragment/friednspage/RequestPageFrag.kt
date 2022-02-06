package com.smilearts.smileychat.main.fragment.friednspage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.smilearts.smileychat.databinding.FragmentFriendRequestListBinding
import com.smilearts.smileychat.main.adapter.RequestListAdapter
import com.smilearts.smileychat.main.callback.RequestCallBack
import com.smilearts.smileychat.main.model.RegisterModel
import com.smilearts.smileychat.main.model.RequestModel
import com.smilearts.smileychat.main.viewmodel.MainViewModel

class RequestPageFrag(
    private val viewModel: MainViewModel
) : Fragment() {

    private lateinit var binding: FragmentFriendRequestListBinding
    private lateinit var adapter: RequestListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFriendRequestListBinding.inflate(layoutInflater)
        binding.fragFridendReqlist.setHasFixedSize(true)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.repositoryUtil.requestRepository.getNewRequestList().observe(viewModel.lifecycleOwner , {
            if (it != null) {
                binding.fragFridendReqNoFriend.visibility = View.GONE
                adapter = RequestListAdapter(requireActivity() , it , object : RequestCallBack {
                    override fun accept(model: RegisterModel) {
                        viewModel.repositoryUtil.requestRepository.acceptRequest(model)
                    }

                    override fun cancel(model: RegisterModel) {
                        viewModel.repositoryUtil.requestRepository.cancelRequest(model)
                    }
                })
                binding.fragFridendReqlist.adapter = adapter
            } else {
                binding.fragFridendReqNoFriend.visibility = View.VISIBLE
            }
        })

        viewModel.repositoryUtil.requestRepository.getSendRequestList().observe(viewModel.lifecycleOwner , {
            if (it != null) {

            }
        })

    }

}