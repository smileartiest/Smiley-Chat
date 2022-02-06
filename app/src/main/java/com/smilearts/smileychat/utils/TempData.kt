package com.smilearts.smileychat.utils

import android.content.Context
import com.smilearts.smileychat.main.model.RegisterModel

class TempData(val context: Context) {

    private val tempData = context.getSharedPreferences(Constant.sharedName , Context.MODE_PRIVATE)
    private val tempEdit = tempData.edit()

    fun loginComplete(userName: String , pass: String , status: Boolean) {
        tempEdit.putString(Constant.profileID , userName)
        tempEdit.putString(Constant.pass , pass)
        tempEdit.putBoolean(Constant.loginStatus , status)
        tempEdit.commit()
    }

    fun getLoginStatus() : Boolean {
        return tempData.getBoolean(Constant.loginStatus , false)
    }

    fun getProfileID() : String {
        return tempData.getString(Constant.profileID , "null").toString()
    }

    fun setProfile(model: RegisterModel) {
        tempEdit.putString(Constant.userID , model.userID)
        tempEdit.putString(Constant.userName , model.userName)
        tempEdit.putString(Constant.userProPicUrl , model.userProPicUrl)
        tempEdit.putString(Constant.userCountryCode , model.userCountryCode)
        tempEdit.putString(Constant.userStatus , model.userStatus)
        tempEdit.putString(Constant.userPin , model.userPin)
        tempEdit.putString(Constant.userRegisterDate , model.userRegisterDate)
        tempEdit.putString(Constant.userModifiedDate , model.userModifiedDate)
        tempEdit.putBoolean(Constant.onlineStatus , model.onlineStatus)
        tempEdit.commit()
    }

    fun getProfile() : RegisterModel {
        var regModel = RegisterModel()
        regModel.userID = tempData.getString(Constant.userID , "null").toString()
        regModel.userName = tempData.getString(Constant.userName , "null").toString()
        regModel.userProPicUrl = tempData.getString(Constant.userProPicUrl , "null").toString()
        regModel.userCountryCode = tempData.getString(Constant.userCountryCode , "null").toString()
        regModel.userStatus = tempData.getString(Constant.userStatus , "null").toString()
        regModel.userPin = tempData.getString(Constant.userPin , "null").toString()
        regModel.userRegisterDate = tempData.getString(Constant.userRegisterDate , "null").toString()
        regModel.userModifiedDate = tempData.getString(Constant.userModifiedDate , "null").toString()
        regModel.onlineStatus = tempData.getBoolean(Constant.onlineStatus , false)
        return regModel
    }

}