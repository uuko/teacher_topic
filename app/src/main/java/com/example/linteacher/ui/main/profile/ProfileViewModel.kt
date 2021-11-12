package com.example.linteacher.ui.main.profile

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linteacher.api.pojo.TeacherBaseResponse

class ProfileViewModel(val dataModel: ProfileRepository) : ViewModel() {
    fun getTeacherData(id: String): MutableLiveData<TeacherBaseResponse> {
        return dataModel.getTeacherData(id)
    }
}