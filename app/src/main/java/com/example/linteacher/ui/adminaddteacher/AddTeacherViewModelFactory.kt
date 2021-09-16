package com.example.linteacher.ui.adminaddteacher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.linteacher.ui.login.LoginRepository
import com.example.linteacher.ui.login.LoginViewModel

class AddTeacherViewModelFactory ( private val dataModel: AddTeacherRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(AddTeacherViewModel::class.java)) {
            return AddTeacherViewModel(dataModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}