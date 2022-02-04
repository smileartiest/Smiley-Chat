package com.smilearts.smileychat.main.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.smilearts.smileychat.repository.RepositoryUtil

class MainViewModel(
    val activity: Activity,
    val repositoryUtil: RepositoryUtil
) : ViewModel() {

}