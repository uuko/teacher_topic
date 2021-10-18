package com.example.linteacher.ui.teacherdata.ui.technologytrans

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TechTransViewModelFactory  (private val dataModel: TechTransRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(TechTransViewModel::class.java)) {
            return TechTransViewModel(dataModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}