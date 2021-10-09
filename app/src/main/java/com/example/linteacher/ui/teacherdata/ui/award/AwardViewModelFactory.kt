package com.example.linteacher.ui.teacherdata.ui.award

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.linteacher.ui.teacherdata.ui.license.LicenseRepository
import com.example.linteacher.ui.teacherdata.ui.license.LicenseViewModel

class AwardViewModelFactory(private val dataModel: AwardRepository): ViewModelProvider.Factory{
    //重點(?)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(AwardViewModel::class.java)) {
        return AwardViewModel(dataModel) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
}
}
