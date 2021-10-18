package com.example.linteacher.ui.teacherdata.ui.off

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.teacherdata.adamic.AcademicChangeVisibleRequest
import com.example.linteacher.api.pojo.teacherdata.off.OffAllOneResponse
import com.example.linteacher.api.pojo.teacherdata.off.OffGetAllResponse
import com.example.linteacher.api.pojo.teacherdata.off.OffPostRequest
import com.example.linteacher.api.pojo.teacherdata.off.OffUpdateRequest

class OffCampusViewModel(var dataModel: OffCampusRepository) : ViewModel() {

    fun getList( id:String):MutableLiveData<OffGetAllResponse>{
        return dataModel.getData(id)
    }
    fun delete(expNumber: Int): MutableLiveData<UnitResponse>{
        return dataModel.deleteData(expNumber.toString())
    }

    fun getDataByExpNumber(loginId: String): MutableLiveData<OffAllOneResponse> {
        return dataModel.getOneData(loginId)
    }

    fun postData(request: OffPostRequest): MutableLiveData<UnitResponse>{
        return dataModel.postData(request)
    }

    fun updateList(request:OffUpdateRequest): MutableLiveData<UnitResponse>{
        return dataModel.updateData(request)
    }

    //change Visible  by licId
    //licId就好,回傳值是<calss>
    fun changeVisible(request: AcademicChangeVisibleRequest): MutableLiveData<UnitResponse> {
        return dataModel.changeVisible(request)
    }
}