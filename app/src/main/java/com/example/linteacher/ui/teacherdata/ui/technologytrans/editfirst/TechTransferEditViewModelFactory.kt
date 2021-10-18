package com.example.linteacher.ui.teacherdata.ui.technologytrans.editfirst

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.linteacher.ui.teacherdata.ui.technologytrans.TechTransRepository

class TechTransferEditViewModelFactory (private val dataModel: TechTransFerEditRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(TechTransFerEditViewModel::class.java)) {
            return TechTransFerEditViewModel(dataModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}