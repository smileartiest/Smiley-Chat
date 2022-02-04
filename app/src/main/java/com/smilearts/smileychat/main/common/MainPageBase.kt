package com.smilearts.smileychat.main.common

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.smilearts.smileychat.main.viewmodel.MainViewModel
import com.smilearts.smileychat.repository.RepositoryUtil

class MainPageBase(val activity: Activity , val repositoryUtil: RepositoryUtil) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(activity,repositoryUtil) as T
    }
}