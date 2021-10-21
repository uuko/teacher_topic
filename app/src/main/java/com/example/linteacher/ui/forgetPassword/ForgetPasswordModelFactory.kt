package com.example.linteacher.ui.forgetPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.linteacher.ui.login.LoginRepository

class ForgetPasswordModelFactory(private val dataModel: ForgetPasswordRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ForgetPasswordModel::class.java)) {
            return ForgetPasswordModel(dataModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}