package com.example.linteacher.ui.teacherdata.ui.technologytrans.editinner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TechEditInnerViewModelFactory (private val dataModel: TechEditInnnerRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(TechEditInnerViewModel::class.java)) {
            return TechEditInnerViewModel(dataModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}