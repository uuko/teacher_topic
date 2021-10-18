package com.example.linteacher.ui.teacherdata.ui.adamic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.teacherdata.adamic.*


class AdemicEventViewModel (var dataModel: AdemicEventRepository) : ViewModel() {

    //git all lic by loginId
    //loginId就好,回傳值是list<class>
    fun getList( id:String): MutableLiveData<AdemicEventAllResponse> {
        return dataModel.getData(id)
    }

    //delete lic by licId
    //awaId就好
    fun delete(expNumber: Int): MutableLiveData<UnitResponse> {
        return dataModel.deleteData(expNumber.toString())
    }

    //get lic by licId
    //licId就好,回傳值是<calss>
    fun getDataByExpNumber(loginId: String): MutableLiveData<AdemicEventAllOneResponse> {
        return dataModel.getOneData(loginId)
    }

    //post lic by request
    fun postData(request: AcademicPostRequest): MutableLiveData<UnitResponse> {
        return dataModel.postData(request)
    }

    //update lic by request
    fun updateList(request: AcademicPostRequest): MutableLiveData<UnitResponse> {
        return dataModel.updateData(request)
    }

    //change Visible  by licId
    //licId就好,回傳值是<calss>
    fun changeVisible(request: AcademicChangeVisibleRequest): MutableLiveData<UnitResponse> {
        return dataModel.changeVisible(request)
    }

    //共計2個request(一個吃awaid,一個吃loginid),2個response,1個Data viewType

}


