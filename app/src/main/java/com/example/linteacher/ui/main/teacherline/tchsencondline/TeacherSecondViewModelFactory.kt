package com.example.linteacher.ui.main.teacherline.tchsencondline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TeacherSecondViewModelFactory(private val dataModel: TeacherSecondRepository, val response: Int) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(TeacherSecondViewModel::class.java)) {
            return TeacherSecondViewModel(dataModel,response) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}