package com.example.linteacher.ui.main.announceinner

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linteacher.api.pojo.artical.ArticleResponse

class AnnounceInnerViewModel(val dataModel: AnnounceInnerRepository) : ViewModel() {
    fun getArticle(id: String): MutableLiveData<ArticleResponse> {
        return dataModel.getArticle(id)
    }
}