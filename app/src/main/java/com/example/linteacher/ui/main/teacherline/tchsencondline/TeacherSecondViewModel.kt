package com.example.linteacher.ui.main.teacherline.tchsencondline

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linteacher.api.pojo.artical.ArticleAllPicResponse
import com.example.linteacher.api.pojo.teacherline.TeacherSecondLineResponse
import com.example.linteacher.ui.addarticle.AddArticleRequest
import java.io.File

class TeacherSecondViewModel(val dataModel: TeacherSecondRepository) : ViewModel() {
    fun getTeacherLineData(id: String): MutableLiveData<TeacherSecondLineResponse> {
        return dataModel.getTeacherLineData(id)
    }
}