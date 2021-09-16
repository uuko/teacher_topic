package com.example.linteacher.ui.main.teacherline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TeacherLineViewModel  constructor(var teacherLineRepository: TeacherLineRepository): ViewModel(){

    val isLoading:  MutableLiveData<Boolean> = MutableLiveData(true)
    private var repoLiveData: MutableLiveData<TeacherLineAllResponse> = MutableLiveData<TeacherLineAllResponse>()
    fun getTeacherList():LiveData<TeacherLineAllResponse>{
        return repoLiveData;
    }
    fun postTeacherList(): MutableLiveData<TeacherLineAllResponse>{
        isLoading.value=(true)
        return teacherLineRepository.getTeacherLineList()
    }

}