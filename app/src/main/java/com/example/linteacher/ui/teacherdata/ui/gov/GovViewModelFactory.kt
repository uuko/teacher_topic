package com.example.linteacher.ui.teacherdata.ui.gov

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.linteacher.ui.teacherdata.ui.gov.GovRepository
import com.example.linteacher.ui.teacherdata.ui.gov.GovViewModel

class GovViewModelFactory(private val dataModel: GovRepository): ViewModelProvider.Factory{
    //重點(?)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GovViewModel::class.java)) {
            return GovViewModel(dataModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}