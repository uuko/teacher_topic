package com.example.linteacher.ui.teacherdata.ui.off

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OffCampusViewModelFactory ( private val dataModel: OffCampusRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(OffCampusViewModel::class.java)) {
            return OffCampusViewModel(dataModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}