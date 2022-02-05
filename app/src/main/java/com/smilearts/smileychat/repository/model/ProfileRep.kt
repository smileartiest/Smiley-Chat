package com.smilearts.smileychat.repository.model

import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.smilearts.smileychat.main.callback.CheckCallBack
import com.smilearts.smileychat.main.callback.ValueCallBack
import com.smilearts.smileychat.main.model.ChatListModel
import com.smilearts.smileychat.main.model.RegisterModel
import com.smilearts.smileychat.repository.RepositoryUtil
import com.smilearts.smileychat.utils.AppUtil
import com.smilearts.smileychat.utils.Constant

class ProfileRep(private val util: RepositoryUtil) {

    private val profileListLiveData: MutableLiveData<List<RegisterModel>> by lazy {
        MutableLiveData<List<RegisterModel>>().also {
            loadProfileList()
        }
    }

    val friendsCountLive: MutableLiveData<Int> = MutableLiveData()

    private val profileData: MutableLiveData<RegisterModel> by lazy {
        MutableLiveData<RegisterModel>().also {
            loadProfile()
        }
    }

    val registerStatus: MutableLiveData<Boolean> = MutableLiveData()

    private fun loadProfileList() {
        util.getDataRef(Constant.regRef).addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.value != null) {
                        var tempList: ArrayList<RegisterModel> = ArrayList()
                        snapshot.children.forEach {
                            val temp = it.getValue<RegisterModel>()
                            if (temp != null) tempList.add(temp)
                        }
                        if (tempList.isNotEmpty()) {
                            profileListLiveData.postValue(tempList)
                            friendsCountLive.postValue(tempList.size)
                        } else {
                            profileListLiveData.postValue(null)
                            friendsCountLive.postValue(0)
                        }
                    } else {
                        profileListLiveData.postValue(null)
                        friendsCountLive.postValue(0)
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    util.errorStatus.postValue(error.message)
                    profileListLiveData.postValue(null)
                    friendsCountLive.postValue(0)
                }
            }
        )
    }

    private fun loadProfile() {
        util.getDataRef(Constant.regRef).child(util.tempData.getProfileID()).addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.value != null) {
                        val temp = snapshot.getValue<RegisterModel>()
                        if (temp != null) {
                            profileData.postValue(temp)
                        } else {
                            profileData.postValue(null)
                        }
                    } else {
                        profileData.postValue(null)
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    util.errorStatus.postValue(error.message)
                }
            }
        )
    }

    fun getProfile() : LiveData<RegisterModel> {
        return profileData
    }

    fun getFriendsList() : LiveData<List<RegisterModel>> {
        return profileListLiveData
    }

    fun registerDetail(model: RegisterModel) {
        checkUserName(model.userID , object : CheckCallBack {
            override fun status(status: Boolean) {
                if (status) {
                    model.userRegisterDate = AppUtil().getDate()
                    registerNew(model)
                }
            }
        })
    }

    private fun checkUserName(id: String , callBack: CheckCallBack) {
        util.getDataRef(Constant.regRef).child(id).addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.value != null) {
                        callBack.status(true)
                    } else {
                        callBack.status(true)
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    util.errorStatus.postValue(error.message)
                }
            }
        )
    }

    fun updatePic(uri: Uri , imgName: String ,callBack: ValueCallBack) {
        val storePath = util.getImageFolder(Constant.proPicPath).child(System.currentTimeMillis().toString() + "." + imgName)
        storePath.putFile(uri).addOnSuccessListener {
            storePath.downloadUrl.addOnSuccessListener { uri ->
                if (uri != null) {
                    callBack.getValue(uri.toString())
                    util.errorStatus.postValue("Image Upload Successful")
                } else callBack.getValue("")
            }
        }
    }

    private fun registerNew(model: RegisterModel) {
        util.getDataRef(Constant.regRef).child(model.userID).setValue(model)
        util.tempData.loginComplete(model.userID , model.userPin , true)
        util.errorStatus.postValue("Register Successful")
    }

}