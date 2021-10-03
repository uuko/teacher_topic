package com.example.linteacher.ui.addarticle

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linteacher.api.pojo.artical.ArticleAllPicResponse
import com.example.linteacher.api.pojo.artical.ArticlePostRequest
import com.example.linteacher.api.pojo.artical.ArticlePostResponse
import com.example.linteacher.api.pojo.artical.ArticleUpdateRequest
import com.example.linteacher.api.pojo.teacherdata.profile.pic.ProfilePicResponse
import java.io.File

class AddArticleViewModel(val dataModel: AddArticleRepository) : ViewModel() {
    fun uploadFile(file: File, request: AddArticleRequest): MutableLiveData<ArticleAllPicResponse> {
        return dataModel.uploadFile(file, request)
    }

    fun updateArticle(request: ArticleUpdateRequest): MutableLiveData<ArticlePostResponse> {
        return dataModel.updateArticleData(request)
    }

    fun postArticle(request: ArticlePostRequest): MutableLiveData<ArticlePostResponse> {
        return dataModel.postArticleData(request)
    }
}