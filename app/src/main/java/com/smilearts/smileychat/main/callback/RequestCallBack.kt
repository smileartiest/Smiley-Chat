package com.smilearts.smileychat.main.callback

import com.smilearts.smileychat.main.model.RegisterModel

interface RequestCallBack {
    fun accept(model: RegisterModel)
    fun cancel(model: RegisterModel)
}