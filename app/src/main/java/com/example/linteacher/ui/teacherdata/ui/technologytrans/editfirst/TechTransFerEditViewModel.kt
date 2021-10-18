package com.example.linteacher.ui.teacherdata.ui.technologytrans.editfirst

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.teacherdata.techtransfer.*
import com.example.linteacher.ui.teacherdata.ui.technologytrans.TechTransRepository

class TechTransFerEditViewModel(val dataModel: TechTransFerEditRepository) :ViewModel(){
    fun getList( id:String): MutableLiveData<TechTransFerSecondAllResponse> {
        return dataModel.getData(id)
    }

    fun updateData(request: TechTransFerUpdateRequest): MutableLiveData<UnitResponse>{
        return dataModel.updateData(request)
    }

    fun postData(request: TechTransFerPostRequest): MutableLiveData<TechChgFirstPostResponse>{
        return dataModel.postData(request)
    }
    fun deleteData(request: TechInnerDeleteRequest): MutableLiveData<UnitResponse>{
        return dataModel.deleteData(request);
    }

}