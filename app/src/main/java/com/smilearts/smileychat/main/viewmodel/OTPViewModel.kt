package com.smilearts.smileychat.main.viewmodel

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class OTPViewModel(
    private val activity: Activity
) : ViewModel() {

    private val TAG = "OTP Page"
    private var mContext: Context = activity.applicationContext
    private var auth: FirebaseAuth = Firebase.auth
    var phNo: String = ""
    var uiState: MutableLiveData<String> = MutableLiveData()
    var exceptionMsg : MutableLiveData<String> = MutableLiveData()
    var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    private var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Log.d(TAG, "onVerificationCompleted:$credential")
            uiState.postValue(OTP_VERIFY_SUCCESS)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Log.w(TAG, "onVerificationFailed", e)
            uiState.postValue(SEND_OTP_FAILED)
            exceptionMsg.postValue(e.message!!)
            if (e is FirebaseAuthInvalidCredentialsException) {
                Log.w(TAG, "onVerificationFailed", e)
            } else if (e is FirebaseTooManyRequestsException) {
                Log.w(TAG, "onVerificationFailed", e)
            }
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            Log.d(TAG, "onCodeSent:$verificationId")
            storedVerificationId = verificationId
            resendToken = token
        }

    }

    fun sendOTP(phNo: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phNo)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        uiState.postValue(SEND_OTP_WAIT)
    }

    fun resendVerificationCode(
        phoneNumber: String) {
        val optionsBuilder = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
        if (resendToken != null) {
            optionsBuilder.setForceResendingToken(resendToken)
        }
        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
    }

    fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    uiState.postValue(OTP_VERIFY_SUCCESS)
                    Log.d(TAG, "signInWithCredential:success")
                    val user = task.result?.user
                } else {
                    uiState.postValue(OTP_VERIFY_FAILED)
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    exceptionMsg.postValue(task.exception.toString())
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                    }
                }
            }
    }


    companion object {
        const val SEND_OTP_BTN = "Send OTP"
        const val RE_SEND_OTP_BTN = "Resend OTP"
        const val VERIFY_OTP_BTN = "Verify"

        const val SEND_OTP_STATE = "SEND OTP STATE"
        const val SEND_OTP_WAIT = "SEND OTP WAIT"
        const val SEND_OTP_FAILED = "OTP SEND FAILED STATE"
        const val RE_SEND_OTP_STATE = "RE SEND OTP STATE"
        const val OTP_VERIFY_SUCCESS = "OTP VERIFY SUCCESS"
        const val OTP_VERIFY_FAILED = "OTP VERIFY FAILED"
    }

}