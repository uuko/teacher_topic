package com.example.linteacher.ui.main.teacherline

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.linteacher.api.pojo.artical.ArticalGetResponse

class TeacherLineViewModel constructor(var teacherLineRepository: TeacherLineRepository) :
    ViewModel() {

    val isLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    private var repoLiveData: MutableLiveData<Int> = MutableLiveData<Int>()
    val teacherLiveData: LiveData<TeacherLineAllResponse> =
        Transformations.switchMap(repoLiveData) { address ->
            Log.e("TeacherLineViewModel", "Transformations: ")
            teacherLineRepository.getTeacherLineList()
        }


    init {
        Log.e("TeacherLineViewModel", "init: ")
        postTeacherList()
    }


    fun postTeacherList() {
        Log.e("TeacherLineViewModel", "postTeacherList: ")
        repoLiveData.value = 1
        isLoading.value = true
    }

}