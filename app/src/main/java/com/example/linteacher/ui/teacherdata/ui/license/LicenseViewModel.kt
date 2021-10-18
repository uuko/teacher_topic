package com.example.linteacher.ui.teacherdata.ui.license

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.teacherdata.adamic.AcademicChangeVisibleRequest
import com.example.linteacher.api.pojo.teacherdata.license.LicPostRequest
import com.example.linteacher.api.pojo.teacherdata.license.LicUpdateRequest
import com.example.linteacher.api.pojo.teacherdata.license.all.LicAllOneResponse
import com.example.linteacher.api.pojo.teacherdata.license.all.LicenseAllResponse
import com.example.linteacher.api.pojo.teacherdata.off.OffAllOneResponse
import com.example.linteacher.api.pojo.teacherdata.off.OffGetAllResponse
import com.example.linteacher.api.pojo.teacherdata.off.OffPostRequest
import com.example.linteacher.api.pojo.teacherdata.off.OffUpdateRequest

class LicenseViewModel(var dataModel: LicenseRepository) : ViewModel() {
    //證照的viewmodel

    //git all lic by loginId
    fun getList( id:String): MutableLiveData<LicenseAllResponse> {
        return dataModel.getData(id)
    }

    //delete lic by licId
    fun delete(expNumber: Int): MutableLiveData<UnitResponse> {
        return dataModel.deleteData(expNumber.toString())
    }

    //get lic by licId
    fun getDataByExpNumber(loginId: String): MutableLiveData<LicAllOneResponse> {
        return dataModel.getOneData(loginId)
    }

    //post lic by request
    fun postData(request: LicPostRequest): MutableLiveData<UnitResponse> {
        return dataModel.postData(request)
    }

    //update lic by request
    fun updateList(request: LicUpdateRequest): MutableLiveData<UnitResponse> {
        return dataModel.updateData(request)
    }

    //change Visible  by licId
    //licId就好,回傳值是<calss>
    fun changeVisible(request: AcademicChangeVisibleRequest): MutableLiveData<UnitResponse> {
        return dataModel.changeVisible(request)
    }
}