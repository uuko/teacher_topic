package com.example.linteacher.ui.addarticle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AddArticleViewModelFactory(private val dataModel: AddArticleRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(AddArticleViewModel::class.java)) {
            return AddArticleViewModel(dataModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}