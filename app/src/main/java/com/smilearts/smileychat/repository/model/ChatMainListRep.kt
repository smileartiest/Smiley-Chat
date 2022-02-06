package com.smilearts.smileychat.repository.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.smilearts.smileychat.main.model.ChatListModel
import com.smilearts.smileychat.main.model.RegisterModel
import com.smilearts.smileychat.repository.RepositoryUtil
import com.smilearts.smileychat.utils.Constant

class ChatMainListRep(private val util: RepositoryUtil) {

    private val chatListLiveData: MutableLiveData<List<ChatListModel>> by lazy {
        MutableLiveData<List<ChatListModel>>().also {
            loadFullChatListDetails()
        }
    }

    private fun loadFullChatListDetails() {
        util.getDataRef(Constant.chatListRef).child(util.tempData.getProfileID()).addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.value != null) {
                        var tempList: ArrayList<ChatListModel> = ArrayList()
                        snapshot.children.forEach {
                            val temp = it.getValue<ChatListModel>()
                            if (temp != null) tempList.add(temp)
                        }
                        if (tempList.isNotEmpty()) {
                            chatListLiveData.postValue(tempList)
                        } else {
                            chatListLiveData.postValue(null)
                        }
                    } else {
                        chatListLiveData.postValue(null)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    util.errorStatus.postValue(error.message)
                    chatListLiveData.postValue(null)
                }
            }
        )
    }

    fun getChatList() : LiveData<List<ChatListModel>> {
        return chatListLiveData
    }

}