package com.example.linteacher.ui.teacherdata.ui.experience

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.teacherdata.ExpGetAllResponse
import com.example.linteacher.api.pojo.teacherdata.exp.ExpAddRequest
import com.example.linteacher.api.pojo.teacherdata.exp.ExpDeleteRequest
import com.example.linteacher.api.pojo.teacherdata.exp.ExpOneGetResponse
import com.example.linteacher.api.pojo.teacherdata.exp.ExpUpdateRequest
import com.example.linteacher.ui.main.teacherline.TeacherLineRepository

class ExpViewModel constructor(var repository: ExpRepository): ViewModel() {

    private val _text = MutableLiveData<ExpGetAllResponse>()


    fun getExpList( id:String):MutableLiveData<ExpGetAllResponse>{
       return repository.getExpData(id)
    }

    fun postExpList(request: ExpAddRequest):MutableLiveData<UnitResponse>{
        return repository.addExp(request)
    }

    fun updateExpList(request: ExpUpdateRequest):MutableLiveData<UnitResponse>{
        return repository.updateExp(request)
    }
    fun deleteExp(expNumber:Int):MutableLiveData<UnitResponse>{
        val request=ExpDeleteRequest(expNumber)
        return repository.deleteExp(request)
    }
    fun getExpDataByExpNumber(number:String): MutableLiveData<ExpOneGetResponse>{
        return repository.getExpDataByTchNumber(number)
    }
}