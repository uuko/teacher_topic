package com.example.linteacher.ui.main.teacherline.tchsencondline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.linteacher.api.pojo.teacherline.TeacherSecondLineResponse

class TeacherSecondViewModel(val dataModel: TeacherSecondRepository, val response: Int) : ViewModel() {
    private val sencondLiveData = MutableLiveData<String>()
    val teacherLineLiveData: LiveData<TeacherSecondLineResponse> =
        Transformations.switchMap(sencondLiveData) { address ->
            dataModel.getTeacherLineData(address)
        }
    init {
        getTeacherLineData(response.toString())
    }
    fun getTeacherLineData(id: String) {
        sencondLiveData.value=id
    }
}