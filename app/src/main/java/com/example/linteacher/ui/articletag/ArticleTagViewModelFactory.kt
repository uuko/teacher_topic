package com.example.linteacher.ui.articletag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ArticleTagViewModelFactory(private val dataModel: ArticleTagRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ArticleTagViewModel::class.java)) {
            return ArticleTagViewModel(dataModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}