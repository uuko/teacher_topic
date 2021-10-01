package com.example.linteacher.ui.main.announceinner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AnnounceInnerViewModelFactory(private val dataModel: AnnounceInnerRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(AnnounceInnerViewModel::class.java)) {
            return AnnounceInnerViewModel(dataModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}