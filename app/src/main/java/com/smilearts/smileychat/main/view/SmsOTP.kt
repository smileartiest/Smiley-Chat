package com.smilearts.smileychat.main.view

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.smilearts.smileychat.R
import com.smilearts.smileychat.databinding.SmsotpPageBinding
import com.smilearts.smileychat.main.common.OTPBase
import com.smilearts.smileychat.main.viewmodel.OTPViewModel
import com.smilearts.smileychat.utils.*
import java.util.concurrent.TimeUnit

class SmsOTP : AppCompatActivity() {

    private lateinit var binding: SmsotpPageBinding
    private lateinit var viewModel: OTPViewModel
    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SmsotpPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this , OTPBase(this)).get(OTPViewModel::class.java)

        viewModel.uiState.observe(this , {
            when(it) {
                OTPViewModel.SEND_OTP_STATE -> {
                    binding.smsotpPhnoTxt.hint = "Phone Number"
                    binding.smsotpSendBtn.text = OTPViewModel.SEND_OTP_BTN
                }
                OTPViewModel.SEND_OTP_WAIT -> {
                    showMessage("OTP Send Successful")
                    binding.smsotpPhnoTxt.hint = "OTP"
                    binding.smsotpPhnoTxt.editText?.setText("")
                    binding.smsotpSendBtn.text = OTPViewModel.VERIFY_OTP_BTN
                    startTimeOut()
                }
                OTPViewModel.RE_SEND_OTP_STATE -> {
                    binding.smsotpPhnoTxt.hint = "Phone Number"
                    binding.smsotpPhnoTxt.editText?.setText(viewModel.phNo)
                    binding.smsotpSendBtn.text = OTPViewModel.RE_SEND_OTP_BTN
                }
                OTPViewModel.OTP_VERIFY_SUCCESS -> {
                    cancelTimeOut()
                    if (viewModel.phNo.isNotEmpty()) {
                        startActivity(
                            Intent(this, RegisterPage::class.java)
                            .putExtra(Constant.userID , viewModel.phNo))
                        finish()
                    }
                    showMessage("OTP matched successful")
                }
            }
        })

        viewModel.exceptionMsg.observe(this , {
            showMessage(it)
        })

    }

    override fun onStart() {
        super.onStart()

        TempData(applicationContext).loginComplete("8344864479" , "" , true)

        if (TempData(applicationContext).getLoginStatus()) {
            startActivity(Intent(this, MainPage::class.java));finish()
        }

    }

    override fun onResume() {
        super.onResume()

        binding.smsotpSendBtn.setOnClickListener {
            when(binding.smsotpSendBtn.text.toString()) {
                OTPViewModel.SEND_OTP_BTN -> {
                    val tempStr: String? = binding.smsotpPhnoTxt.editText?.text.toString()
                    if (tempStr != null) {
                        viewModel.phNo = tempStr
                        viewModel.sendOTP("${CountryName.UAE} $tempStr")
                    }
                }
                OTPViewModel.RE_SEND_OTP_BTN -> {
                    val tempStr: String? = binding.smsotpPhnoTxt.editText?.text.toString()
                    if (tempStr != null) {
                        viewModel.resendVerificationCode("${CountryName.UAE} ${viewModel.phNo}")
                    }
                }
                OTPViewModel.VERIFY_OTP_BTN -> {
                    val tempStr: String? = binding.smsotpPhnoTxt.editText?.text.toString()
                    if (tempStr != null) {
                        cancelTimeOut()
                        viewModel.verifyPhoneNumberWithCode(viewModel.storedVerificationId,tempStr)
                    }
                }
            }
        }

    }

    private fun startTimeOut() {
        countDownTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.smsotpTimingTxt.text = "remaining " + millisUntilFinished / 1000
            }
            override fun onFinish() {
                binding.smsotpTimingTxt.text = "If not received OTP Try again!"
                viewModel.uiState.value = OTPViewModel.RE_SEND_OTP_STATE
            }
        }.start()
    }

    private fun cancelTimeOut() {
        if (countDownTimer != null)
            countDownTimer.cancel()
        binding.smsotpTimingTxt.text = " "
    }

    private fun showMessage(msg: String) {
        Snackbar.make(binding.constraintLayout , msg , Snackbar.LENGTH_SHORT).show()
    }

}