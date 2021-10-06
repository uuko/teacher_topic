package com.example.linteacher.ui.teacherdata.ui.journal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class JournalViewModelFactory(private val dataModel: JournalRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(JournalViewModel::class.java)) {
            return JournalViewModel(dataModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}