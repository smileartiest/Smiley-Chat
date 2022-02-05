package com.smilearts.smileychat.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.smilearts.smileychat.main.fragment.friednspage.FriendsListPage
import com.smilearts.smileychat.main.fragment.friednspage.RequestPageFrag
import com.smilearts.smileychat.main.viewmodel.MainViewModel

private const val tabCount = 2

class FriendsPageAdapter(
    fragment: FragmentManager,
    lifeCycle: Lifecycle,
    val viewModel: MainViewModel
) : FragmentStateAdapter(fragment,lifeCycle) {

    override fun getItemCount(): Int {
        return tabCount
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> { return RequestPageFrag(viewModel) }
            1 -> { return FriendsListPage(viewModel) }
        }
        return RequestPageFrag(viewModel)
    }

}