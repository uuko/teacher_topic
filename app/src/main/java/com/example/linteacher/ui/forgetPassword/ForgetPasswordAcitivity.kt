package com.example.linteacher.ui.forgetPassword

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.linteacher.api.pojo.forgetPawwrod.SavePasswordRequest
import com.example.linteacher.databinding.ActivityForgetpasswordBinding
import com.example.linteacher.databinding.ActivityLoginBinding
import com.example.linteacher.ui.login.LoginRepository
import com.example.linteacher.ui.login.LoginViewModel
import com.example.linteacher.ui.login.LoginViewModelFactory
import com.example.linteacher.ui.main.MainActivity
import com.example.linteacher.util.ActivityNavigator
import com.example.linteacher.util.Config
import com.example.linteacher.util.preference.LoginPreferences

class ForgetPasswordAcitivity: AppCompatActivity() {
    private lateinit var binding: ActivityForgetpasswordBinding
    private var token:String=""
    private val factory = ForgetPasswordModelFactory(ForgetPasswordRepository())
    private val viewModel: ForgetPasswordModel by viewModels {
        factory
    }
    private lateinit var logiSharePreferences: LoginPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetpasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        logiSharePreferences= LoginPreferences(this)


//        viewModel.isLoading.observe(this, Observer {
//            if (it) {
//                binding.progressBar.visibility = View.VISIBLE
//            } else binding.progressBar.visibility = View.GONE
//        })


        binding.nextBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var emailName = binding.emailEdit.text.toString()
                viewModel.postSendMail(emailName)
                    .observe(this@ForgetPasswordAcitivity, {
                        if (it.result == Config.RESULT_OK) {
                            //如果寄送email成功
                            Log.d("nextBtn", "onClick: a 寄送成功")
                            binding.sendEmail.visibility=View.GONE
                            binding.checkToken.visibility=View.VISIBLE
                            binding.emailText.visibility=View.VISIBLE
                            binding.emailText.text= emailName


                        } else {
                            Log.d("nextBtn", "onClick: a 無此信箱")
                            Toast.makeText(this@ForgetPasswordAcitivity, "無此信箱: "+it.result, Toast.LENGTH_SHORT).show();
                        }
                    })
            }

        })

        binding.checkTokenBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                token = binding.tokenEdit.text.toString()
                viewModel.getCheckToken(token)
                    .observe(this@ForgetPasswordAcitivity, {
                        if (it.result=="ok") {
                            //如果寄送email成功
                            Log.d("checkTokenBtn", "onClick: a token正確")
                            binding.checkToken.visibility=View.GONE
                            binding.changePassword.visibility=View.VISIBLE




                        } else {
                            Log.d("checkTokenBtn", "onClick: a token錯誤"+token+"error"+it)
                            Toast.makeText(this@ForgetPasswordAcitivity, "驗證碼錯誤,請重新輸入: "+it.result, Toast.LENGTH_SHORT).show();
                        }
                    })
            }

        })

        binding.changePasswordBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val request = SavePasswordRequest(binding.tokenEdit.text.toString(),binding.newPasswordEdit.text.toString())
                        viewModel.postSavaPassword(request)
                    .observe(this@ForgetPasswordAcitivity, {
                        if (it.result == Config.RESULT_OK) {
                            //如果寄送email成功
                            Log.d("nextBtn", "onClick: a 修改密碼成功")
                            Toast.makeText(this@ForgetPasswordAcitivity, "修改密碼成功,請重新登入", Toast.LENGTH_SHORT).show();
                            finish()
                        } else {
                            Log.d("nextBtn", "onClick: a 修改改密碼失敗")
                            Toast.makeText(this@ForgetPasswordAcitivity, "修改密碼失敗 錯誤: "+it.result, Toast.LENGTH_SHORT).show();

                        }
                    })
            }

        })


    }


}