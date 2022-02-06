package com.smilearts.smileychat.repository.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.smilearts.smileychat.main.model.ChatListModel
import com.smilearts.smileychat.main.model.RegisterModel
import com.smilearts.smileychat.main.model.RequestModel
import com.smilearts.smileychat.repository.RepositoryUtil
import com.smilearts.smileychat.utils.Constant

class RequestRep(private val util: RepositoryUtil) {

    private val  newRequestLiveData: MutableLiveData<List<RegisterModel>> by lazy {
        MutableLiveData<List<RegisterModel>>().also {
            loadNewRequestList()
        }
    }

    val newRequestCount: MutableLiveData<Int> = MutableLiveData()

    private val  sendRequestLiveData: MutableLiveData<List<RegisterModel>> by lazy {
        MutableLiveData<List<RegisterModel>>().also {
            loadSendRequestList()
        }
    }

    val sendRequestCount: MutableLiveData<Int> = MutableLiveData()

    private fun loadNewRequestList() {
        util.getDataRef(Constant.requestRef).child(util.tempData.getProfileID())
            .child(Constant.newRequestRef)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.value != null) {
                        var tempList: ArrayList<RegisterModel> = ArrayList()
                        snapshot.children.forEach {
                            val temp = it.getValue<RegisterModel>()
                            if (temp != null) tempList.add(temp)
                        }
                        if (tempList.isNotEmpty()) {
                            newRequestLiveData.postValue(tempList)
                            newRequestCount.postValue(tempList.size)
                        } else {
                            newRequestLiveData.postValue(null)
                            newRequestCount.postValue(null)
                        }
                    } else {
                        newRequestLiveData.postValue(null)
                        newRequestCount.postValue(null)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    util.errorStatus.postValue(error.message)
                    newRequestLiveData.postValue(null)
                    newRequestCount.postValue(null)
                }
            })
    }

    private fun loadSendRequestList() {
        util.getDataRef(Constant.requestRef).child(util.tempData.getProfileID())
            .child(Constant.sendRequestRef)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.value != null) {
                        var tempList: ArrayList<RegisterModel> = ArrayList()
                        snapshot.children.forEach {
                            val temp = it.getValue<RegisterModel>()
                            if (temp != null) tempList.add(temp)
                        }
                        if (tempList.isNotEmpty()) {
                            sendRequestLiveData.postValue(tempList)
                            sendRequestCount.postValue(tempList.size)
                        } else {
                            sendRequestLiveData.postValue(null)
                            sendRequestCount.postValue(null)
                        }
                    } else {
                        sendRequestLiveData.postValue(null)
                        sendRequestCount.postValue(null)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    util.errorStatus.postValue(error.message)
                    sendRequestLiveData.postValue(null)
                    sendRequestCount.postValue(null)
                }
            })
    }

    fun getNewRequestList() : LiveData<List<RegisterModel>> {
        return newRequestLiveData
    }

    fun getSendRequestList() : LiveData<List<RegisterModel>> {
        return sendRequestLiveData
    }

    fun sendRequest(model: RegisterModel) {
        val myProfile = util.tempData.getProfile()
        val mySendRequest = RegisterModel()
        mySendRequest.userID = model.userID
        mySendRequest.userName = model.userName
        util.getDataRef(Constant.requestRef).child(myProfile.userID).child(Constant.sendRequestRef).child(model.userID).setValue(mySendRequest)
        util.getDataRef(Constant.requestRef).child(model.userID).child(Constant.newRequestRef).child(myProfile.userID).setValue(myProfile)
    }

    fun acceptRequest(model: RegisterModel) {
        val myProfile = util.tempData.getProfile()
        //Set My Chat List
        val myChat = ChatListModel()
        myChat.chatID = model.userID
        myChat.chatName = model.userName
        myChat.chatPersonID = model.userID
        myChat.chatProfilePic = model.userProPicUrl
        //Set Opposite person chat
        val setChat = ChatListModel()
        setChat.chatID = myProfile.userID
        setChat.chatName = myProfile.userName
        setChat.chatPersonID = myProfile.userID
        setChat.chatProfilePic = myProfile.userProPicUrl

        util.getDataRef(Constant.chatListRef).child(myProfile.userID).child(model.userID).setValue(myChat)
        util.getDataRef(Constant.chatListRef).child(model.userID).child(myProfile.userID).setValue(setChat)

        //Remove Request from list
        util.getDataRef(Constant.requestRef).child(myProfile.userID).child(Constant.sendRequestRef).child(model.userID).removeValue()
        util.getDataRef(Constant.requestRef).child(model.userID).child(Constant.newRequestRef).child(myProfile.userID).removeValue()
    }

    fun cancelRequest(model: RegisterModel) {
        val myProfile = util.tempData.getProfile()
        //Remove Request from list
        util.getDataRef(Constant.requestRef).child(myProfile.userID).child(Constant.sendRequestRef).child(model.userID).setValue(null)
        util.getDataRef(Constant.requestRef).child(model.userID).child(Constant.newRequestRef).child(myProfile.userID).setValue(null)
    }

}