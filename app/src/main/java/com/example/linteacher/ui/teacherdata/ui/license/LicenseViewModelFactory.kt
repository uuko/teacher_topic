package com.example.linteacher.ui.teacherdata.ui.license

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LicenseViewModelFactory ( private val dataModel: LicenseRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(LicenseViewModel::class.java)) {
            return LicenseViewModel(dataModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}