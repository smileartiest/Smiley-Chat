package com.smilearts.smileychat.main.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.MimeTypeMap
import com.google.android.material.snackbar.Snackbar
import com.smilearts.smileychat.databinding.ActivityRegisterPageBinding
import com.smilearts.smileychat.main.callback.ValueCallBack
import com.smilearts.smileychat.main.model.RegisterModel
import com.smilearts.smileychat.repository.RepositoryUtil
import com.smilearts.smileychat.utils.AppUtil
import com.smilearts.smileychat.utils.Constant
import com.smilearts.smileychat.utils.TempData
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

class RegisterPage : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterPageBinding
    private lateinit var repository: RepositoryUtil
    private var picUri: Uri? = null
    private val REQUEST_CODE = 1000
    private var model = RegisterModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        repository = RepositoryUtil(this , TempData(this) , AppUtil().scope)
        if (intent != null) {
            if (intent.extras?.getString(Constant.uPhone)!!.isNotEmpty()) {
                model.userID = intent.extras?.getString(Constant.uPhone)!!
            }
        }
    }

    override fun onResume() {
        super.onResume()

        binding.registerContinue.setOnClickListener {
            var name = binding.registerName.editText?.text.toString()
            if (!name.isNullOrEmpty()) {
                model.userName = name
                if (picUri != null) {
                    repository.profileRepository.updatePic(picUri!! , imageExtension(picUri!!)!! , object : ValueCallBack {
                        override fun getValue(value: String) {
                            if (!value.isNullOrEmpty()) {
                                model.userProPicUrl = value
                                repository.profileRepository.registerDetail(model)
                            }
                        }
                    })
                } else {
                    showMessage("Please choose image")
                }
            } else {
                binding.registerName.editText?.error = "Please enter name"
            }
        }

        binding.registerPropic.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE)
        }

        repository.profileRepository.registerStatus.observe(this , {
            if (it) {
                startActivity(Intent(applicationContext , MainPage::class.java))
                finish()
            }
        })

        repository.errorStatus.observe(this , {
            showMessage(it)
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val imagePath = data.data
            CropImage.activity(imagePath).setGuidelines(CropImageView.Guidelines.ON).start(this)
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                val result = CropImage.getActivityResult(data)
                picUri = result.uri
                binding.registerPropic.setImageURI(picUri)
            }
        }
    }

    private fun imageExtension(uri: Uri): String? {
        val cr = contentResolver
        val mMap = MimeTypeMap.getSingleton()
        return mMap.getExtensionFromMimeType(cr.getType(uri))
    }

    private fun showMessage(message: String) {
        Snackbar.make(binding.registerScreen, message, Snackbar.LENGTH_SHORT).show()
    }

}