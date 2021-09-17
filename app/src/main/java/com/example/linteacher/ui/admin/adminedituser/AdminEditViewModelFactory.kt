package com.example.linteacher.ui.admin.adminedituser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AdminEditViewModelFactory ( private val dataModel: AdminEditRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(AdminEditViewModel::class.java)) {
            return AdminEditViewModel(dataModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}