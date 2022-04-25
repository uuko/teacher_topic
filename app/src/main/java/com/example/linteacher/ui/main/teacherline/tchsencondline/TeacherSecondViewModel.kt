package com.example.linteacher.ui.main.teacherline.tchsencondline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.linteacher.api.pojo.artical.ArticalGetResponse
import com.example.linteacher.api.pojo.artical.ArticleAllPicResponse
import com.example.linteacher.api.pojo.teacherline.TeacherSecondLineResponse
import com.example.linteacher.ui.addarticle.AddArticleRequest
import java.io.File

class TeacherSecondViewModel(val dataModel: TeacherSecondRepository) : ViewModel() {
    private val sencondLiveData = MutableLiveData<String>()
    val teacherLineLiveData: LiveData<TeacherSecondLineResponse> =
        Transformations.switchMap(sencondLiveData) { address ->
            dataModel.getTeacherLineData(address)
        }
    fun getTeacherLineData(id: String) {
        sencondLiveData.value=id
    }
}