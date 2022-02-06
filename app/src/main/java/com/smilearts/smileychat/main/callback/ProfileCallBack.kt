package com.smilearts.smileychat.main.callback

import com.smilearts.smileychat.main.model.RegisterModel

interface ProfileCallBack {
    fun chooseProfile(model: RegisterModel)
}