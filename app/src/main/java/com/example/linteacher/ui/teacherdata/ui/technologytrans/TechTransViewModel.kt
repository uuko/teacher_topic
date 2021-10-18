package com.example.linteacher.ui.teacherdata.ui.technologytrans

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linteacher.api.pojo.teacherdata.paper.PaperAllResponse
import com.example.linteacher.api.pojo.teacherdata.techtransfer.TechTransFerAllResponse

class TechTransViewModel(val dataModel: TechTransRepository):ViewModel() {
    fun getList( id:String): MutableLiveData<TechTransFerAllResponse> {
        return dataModel.getData(id)
    }
}