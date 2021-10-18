package com.example.linteacher.ui.teacherdata.ui.journal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.teacherdata.adamic.AcademicChangeVisibleRequest
import com.example.linteacher.api.pojo.teacherdata.dis.*

class JournalViewModel(val dataModel: JournalRepository) : ViewModel() {


    fun getDisList(number: String): MutableLiveData<DisGetAllResponse> {
        return dataModel.getDisList(number)
    }

    fun getDisListById(number: String): MutableLiveData<DisGetOneResponse> {
        return dataModel.getDisListById(number)
    }

    fun postDisData(number: DisPostRequest): MutableLiveData<UnitResponse> {
        return dataModel.postDisData(number)
    }

    //    updateDisData
    fun updateDisData(number: DisUpdateRequest): MutableLiveData<UnitResponse> {
        return dataModel.updateDisData(number)
    }

    //deleteDisData
    fun deleteDisData(number: DisDelRequest): MutableLiveData<UnitResponse> {
        return dataModel.deleteDisData(number)
    }
    //change Visible  by licId
    //licId就好,回傳值是<calss>
    fun changeVisible(request: AcademicChangeVisibleRequest): MutableLiveData<UnitResponse> {
        return dataModel.changeVisible(request)
    }
}