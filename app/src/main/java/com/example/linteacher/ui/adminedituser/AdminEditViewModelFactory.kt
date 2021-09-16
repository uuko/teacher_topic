package com.example.linteacher.ui.adminedituser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.linteacher.ui.login.LoginRepository
import com.example.linteacher.ui.login.LoginViewModel

class AdminEditViewModelFactory ( private val dataModel: AdminEditRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(AdminEditViewModel::class.java)) {
            return AdminEditViewModel(dataModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}