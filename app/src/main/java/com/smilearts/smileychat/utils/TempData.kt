package com.smilearts.smileychat.utils

import android.content.Context

class TempData(val context: Context) {

    private val tempData = context.getSharedPreferences(Constant.sharedName , Context.MODE_PRIVATE)
    private val tempEdit = tempData.edit()

    fun loginComplete(userName: String , pass: String , status: Boolean) {
        tempEdit.putString(Constant.userName , userName)
        tempEdit.putString(Constant.pass , pass)
        tempEdit.putBoolean(Constant.loginStatus , status)
        tempEdit.commit()
    }

    fun getLoginStatus() : Boolean {
        return tempData.getBoolean(Constant.loginStatus , false)
    }

    fun getProfileID() : String {
        return tempData.getString(Constant.userName , "null").toString()
    }

}