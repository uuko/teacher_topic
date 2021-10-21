package com.example.linteacher.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.linteacher.databinding.ActivityLoginBinding
import com.example.linteacher.ui.forgetPassword.ForgetPasswordAcitivity
import com.example.linteacher.ui.main.MainActivity
import com.example.linteacher.util.ActivityNavigator
import com.example.linteacher.util.preference.LoginPreferences
import java.util.*


class LoginActivity : AppCompatActivity() {
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
        logiSharePreferences= LoginPreferences(this)


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

        binding.loginBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if (emailValidator(binding.account.toString())) {
                    viewModel
                        .postLogin(binding.account.text.toString(), binding.passwd.text.toString())
                        .observe(this@LoginActivity,
                            { t ->
                                viewModel.isLoading.value = (false)
                                if (!(t.error.isNotEmpty())) {
                                    logiSharePreferences.setTeacherGrade(t?.grade.toString())
                                    logiSharePreferences.setTeacherId(t?.tchNumber.toString())
                                    ActivityNavigator.startActivity(
                                        MainActivity::class.java,
                                        this@LoginActivity
                                    )
                                } else {

                                }


                            })
                }


            }

        })
    }
    fun emailValidator(emailToText: String):Boolean {
        if (!emailToText.isEmpty() ) {
            Toast.makeText(this, "Email Verified !", Toast.LENGTH_SHORT).show()
            return true

        } else {
            Toast.makeText(this, "Enter valid Email address !", Toast.LENGTH_SHORT).show()
            return false

        }
    }
}