package com.smilearts.smileychat.main.common

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.smilearts.smileychat.main.viewmodel.OTPViewModel

class OTPBase(val activity: Activity) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OTPViewModel(activity) as T
    }
}