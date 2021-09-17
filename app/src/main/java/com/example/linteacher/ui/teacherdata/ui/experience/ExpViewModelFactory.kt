package com.example.linteacher.ui.teacherdata.ui.experience

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.linteacher.ui.main.teacherline.TeacherLineRepository
import com.example.linteacher.ui.main.teacherline.TeacherLineViewModel

class ExpViewModelFactory ( private val dataModel: ExpRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ExpViewModel::class.java)) {
            return ExpViewModel(dataModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}