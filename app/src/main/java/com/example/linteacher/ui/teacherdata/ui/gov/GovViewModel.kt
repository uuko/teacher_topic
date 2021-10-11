package com.example.linteacher.ui.teacherdata.ui.gov

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.teacherdata.gov.GovAllOneResponse
import com.example.linteacher.api.pojo.teacherdata.gov.GovAllResponse
import com.example.linteacher.api.pojo.teacherdata.gov.GovPostRequest
import com.example.linteacher.api.pojo.teacherdata.gov.GovUpdateRequest
import com.example.linteacher.ui.teacherdata.ui.gov.GovRepository

class GovViewModel (var dataModel: GovRepository) : ViewModel() {

    //git all lic by loginId
    //loginId就好,回傳值是list<class>
    fun getList( id:String): MutableLiveData<GovAllResponse> {
        return dataModel.getData(id)
    }

    //delete lic by licId
    //awaId就好
    fun delete(expNumber: Int): MutableLiveData<UnitResponse> {
        return dataModel.deleteData(expNumber.toString())
    }

    //get lic by licId
    //licId就好,回傳值是<calss>
    fun getDataByExpNumber(loginId: String): MutableLiveData<GovAllOneResponse> {
        return dataModel.getOneData(loginId)
    }

    //post lic by request
    fun postData(request: GovPostRequest): MutableLiveData<UnitResponse> {
        return dataModel.postData(request)
    }

    //update lic by request
    fun updateList(request: GovUpdateRequest): MutableLiveData<UnitResponse> {
        return dataModel.updateData(request)
    }

    //共計2個request(一個吃awaid,一個吃loginid),2個response,1個Data viewType

}