package com.example.linteacher.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.linteacher.R
import com.example.linteacher.databinding.ActivityLoginBinding
import com.example.linteacher.ui.forgetPassword.ForgetPasswordAcitivity
import com.example.linteacher.ui.main.MainActivity
import com.example.linteacher.util.ActivityNavigator
import com.example.linteacher.util.BaseActivity
import com.example.linteacher.util.preference.LoginPreferences
import java.util.*


class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val factory = LoginViewModelFactory(LoginRepository())
    private val viewModel: LoginViewModel by viewModels {
        factory
    }
    private lateinit var logiSharePreferences: LoginPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        logiSharePreferences = LoginPreferences(this)

        logiSharePreferences.setTeacherGrade("")
        logiSharePreferences.setTeacherId("")
        //記號 //遊客 =""
        logiSharePreferences.setLoginId("")
        viewModel
            .response
            .observe(this@LoginActivity,
                { t ->
                    viewModel.isLoading.value = (false)
                    if (t.error.isEmpty()) {
                        logiSharePreferences.setTeacherGrade(t?.grade.toString())
                        logiSharePreferences.setTeacherId(t?.tchNumber.toString())
                        //記號
                        // 登入 loginId=使用者,teacherId = get 編輯資料 by teacherId,
                        // grade="A",teacherId會變動(按teacherMore時)
                        logiSharePreferences.setLoginId(t?.tchNumber.toString())
                        logiSharePreferences.setToken(t?.token.toString())
                        ActivityNavigator.startActivity(
                            MainActivity::class.java,
                            this@LoginActivity
                        )
                        finish()
                    } else {

                    }


                })

        viewModel.isLoading.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else binding.progressBar.visibility = View.GONE
        })


        binding.forgetPasswordBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val intent = Intent(this@LoginActivity, ForgetPasswordAcitivity::class.java)
                startActivity(intent)

            }

        })

        binding.guestBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                logiSharePreferences.setTeacherGrade("")
                logiSharePreferences.setTeacherId("")
                //記號 //遊客 =""
                logiSharePreferences.setLoginId("")
                handleLogin(
                    resources.getString(R.string.guest_login),
                    resources.getString(R.string.guest_pwd)
                )


            }

        })

        binding.loginBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if (emailValidator(binding.account.toString())) {
                    handleLogin(binding.account.text.toString(), binding.passwd.text.toString())
                }


            }

        })
    }

    private fun handleLogin(email: String, pwd: String) {
        viewModel.postLogin(email, pwd)

    }

    fun emailValidator(emailToText: String): Boolean {
        if (!emailToText.isEmpty()) {
            Toast.makeText(this, "Email Verified !", Toast.LENGTH_SHORT).show()
            return true

        } else {
            Toast.makeText(this, "Enter valid Email address !", Toast.LENGTH_SHORT).show()
            return false

        }
    }
}