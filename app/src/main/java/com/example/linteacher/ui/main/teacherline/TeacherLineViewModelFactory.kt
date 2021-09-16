package com.example.linteacher.ui.main.teacherline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TeacherLineViewModelFactory( private val dataModel: TeacherLineRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(TeacherLineViewModel::class.java)) {
            return TeacherLineViewModel(dataModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
