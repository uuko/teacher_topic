package com.example.linteacher.ui.teacherdata.ui.paper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PaperViewModelFactory ( private val dataModel: PaperRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(PaperViewModel::class.java)) {
            return PaperViewModel(dataModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}