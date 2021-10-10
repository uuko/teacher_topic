package com.example.linteacher.ui.teacherdata.ui.adamic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AdemicEventViewModelFactory(private val dataModel: AdemicEventRepository): ViewModelProvider.Factory{
    //重點(?)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdemicEventViewModel::class.java)) {
            return AdemicEventViewModel(dataModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

