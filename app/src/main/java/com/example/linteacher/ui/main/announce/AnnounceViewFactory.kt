package com.example.linteacher.ui.main.announce

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.linteacher.ui.teacherdata.ui.experience.ExpRepository
import com.example.linteacher.ui.teacherdata.ui.experience.ExpViewModel

class AnnounceViewFactory(private val dataModel: AnnounceRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(AnnounceViewModel::class.java)) {
            return AnnounceViewModel(dataModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}