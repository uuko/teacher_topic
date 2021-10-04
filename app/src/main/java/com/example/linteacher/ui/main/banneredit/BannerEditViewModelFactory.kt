package com.example.linteacher.ui.main.banneredit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BannerEditViewModelFactory(private val dataModel: BannerEditRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(BannerEditViewModel::class.java)) {
            return BannerEditViewModel(dataModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}