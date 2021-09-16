package com.example.linteacher.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.linteacher.ui.main.teacherline.TeacherLineRepository
import com.example.linteacher.ui.main.teacherline.TeacherLineViewModel

class LoginViewModelFactory( private val dataModel: LoginRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(dataModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}