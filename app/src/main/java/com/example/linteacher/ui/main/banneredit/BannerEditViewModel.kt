package com.example.linteacher.ui.main.banneredit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linteacher.api.pojo.banner.BannerGetResponse
import com.example.linteacher.api.pojo.banner.BannerUpdateRequest
import com.example.linteacher.api.pojo.banner.ResponseContent

class BannerEditViewModel(private val dataModel: BannerEditRepository) : ViewModel() {
    fun getBannerList(): MutableLiveData<BannerGetResponse> {
        return dataModel.getBannerList()
    }

    fun deleteBanner(id: String): MutableLiveData<ResponseContent> {
        return dataModel.deleteBanner(id)
    }
}