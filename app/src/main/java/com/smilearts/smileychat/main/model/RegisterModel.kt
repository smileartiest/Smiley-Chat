package com.smilearts.smileychat.main.model

data class RegisterModel(
    var userID: String = "",
    var userName: String = "",
    var userProPicUrl: String = "",
    var userCountryCode: String = "",
    var userStatus: String = "",
    var userPin: String = "",
    var userRegisterDate: String = "",
    var userModifiedDate: String = "",
    var onlineStatus: Boolean = false
)
