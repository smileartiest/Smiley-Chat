package com.smilearts.smileychat.repository.model

import androidx.lifecycle.MutableLiveData
import com.smilearts.smileychat.main.model.ChatListModel
import com.smilearts.smileychat.repository.RepositoryUtil

class ChatMainListRep(private val util: RepositoryUtil) {

    private val chatListLiveData: MutableLiveData<List<ChatListModel>> by lazy {
        MutableLiveData<List<ChatListModel>>().also {
            loadFullChatListDetails()
        }
    }

    private fun loadFullChatListDetails() {

    }

}