package com.example.linteacher.ui.teacherdata.ui.paper

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.teacherdata.license.LicPostRequest
import com.example.linteacher.api.pojo.teacherdata.license.LicUpdateRequest
import com.example.linteacher.api.pojo.teacherdata.license.all.LicAllOneResponse
import com.example.linteacher.api.pojo.teacherdata.license.all.LicenseAllResponse
import com.example.linteacher.api.pojo.teacherdata.paper.PaperAllResponse
import com.example.linteacher.api.pojo.teacherdata.paper.PaperOneAllResponse
import com.example.linteacher.api.pojo.teacherdata.paper.PaperPostRequest
import com.example.linteacher.api.pojo.teacherdata.paper.PaperUpdateRequest

class PaperViewModel(val dataModel: PaperRepository) : ViewModel() {
    fun getList( id:String): MutableLiveData<PaperAllResponse> {
        return dataModel.getData(id)
    }
    fun delete(expNumber: Int): MutableLiveData<UnitResponse> {
        return dataModel.deleteData(expNumber.toString())
    }

    fun getDataByExpNumber(loginId: String): MutableLiveData<PaperOneAllResponse> {
        return dataModel.getOneData(loginId)
    }

    fun postData(request: PaperPostRequest): MutableLiveData<UnitResponse> {
        return dataModel.postData(request)
    }

    fun updateList(request: PaperUpdateRequest): MutableLiveData<UnitResponse> {
        return dataModel.updateData(request)
    }
}