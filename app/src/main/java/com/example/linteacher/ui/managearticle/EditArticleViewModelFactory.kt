package com.example.linteacher.ui.managearticle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EditArticleViewModelFactory(private val dataModel: EditArticleRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(EditArticleViewModel::class.java)) {
            return EditArticleViewModel(dataModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}