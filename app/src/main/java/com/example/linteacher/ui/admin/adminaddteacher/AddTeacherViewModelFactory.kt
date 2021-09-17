package com.example.linteacher.ui.admin.adminaddteacher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AddTeacherViewModelFactory ( private val dataModel: AddTeacherRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(AddTeacherViewModel::class.java)) {
            return AddTeacherViewModel(dataModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}