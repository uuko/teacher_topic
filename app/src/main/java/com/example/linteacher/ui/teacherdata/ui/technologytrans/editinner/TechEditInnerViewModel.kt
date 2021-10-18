package com.example.linteacher.ui.teacherdata.ui.technologytrans.editinner

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.teacherdata.techtransfer.*

class TechEditInnerViewModel(val dataModel: TechEditInnnerRepository) : ViewModel() {
    fun getData(companyId: String): MutableLiveData<TechInnerResponse> {
        return dataModel.getData(companyId)
    }

    fun postData(request: TechInnerPostOneRequest): MutableLiveData<UnitResponse> {
        return dataModel.postData(request)
    }

    fun updateData(request: TechInnerUpdateRequest): MutableLiveData<UnitResponse> {
        return dataModel.updateData(request)
    }

    fun deleteData(request: TechInnerDeleteRequest): MutableLiveData<UnitResponse>{
        return dataModel.deleteData(request);
    }
}