package com.example.linteacher.ui.teacherdata.ui.patent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.linteacher.ui.teacherdata.ui.patent.PatentRepository
import com.example.linteacher.ui.teacherdata.ui.patent.PatentViewModel

class PatentViewModelFactory (private val dataModel: PatentRepository): ViewModelProvider.Factory{
    //重點(?)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PatentViewModel::class.java)) {
            return PatentViewModel(dataModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}