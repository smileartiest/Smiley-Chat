package com.smilearts.smileychat.main.viewmodel

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smilearts.smileychat.repository.RepositoryUtil

class MainViewModel(
    val activity: Activity,
    val repositoryUtil: RepositoryUtil,
    val lifecycleOwner: LifecycleOwner
) : ViewModel() {

    val pageStateLive: MutableLiveData<Int> = MutableLiveData()
    var pagePosition = 1

}