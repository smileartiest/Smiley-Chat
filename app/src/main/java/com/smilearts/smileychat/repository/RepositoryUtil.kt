package com.smilearts.smileychat.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.smilearts.smileychat.repository.config.RoomConfig
import com.smilearts.smileychat.repository.model.ProfileRep
import com.smilearts.smileychat.utils.TempData
import kotlinx.coroutines.CoroutineScope

class RepositoryUtil(
    val context: Context,
    val tempData: TempData,
    private val scope: CoroutineScope
) {

    private val instanceData = Firebase.database
    private val instanceImage = FirebaseStorage.getInstance()
    val roomConfig = RoomConfig.getInstance(context,scope)!!

    val profileRepository = ProfileRep(this)

    var errorStatus: MutableLiveData<String> = MutableLiveData()

    fun getDataRef(reference: String) : DatabaseReference {
        return instanceData.getReference(reference)
    }

    fun getQuery(reference: String) : Query {
        return instanceData.getReference(reference)
    }

    fun getImageFolder(reference: String) : StorageReference {
        return instanceImage.getReference(reference)
    }

}