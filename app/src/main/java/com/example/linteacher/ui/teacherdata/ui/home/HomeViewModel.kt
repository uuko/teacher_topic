package com.example.linteacher.ui.teacherdata.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.teacherdata.profile.TeacherProfileAllResponse
import com.example.linteacher.api.pojo.teacherdata.profile.TeacherProfileResponse
import com.example.linteacher.api.pojo.teacherdata.profile.pic.ProfilePicResponse
import java.io.File

class HomeViewModel(private val dataModel: HomeRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun getTeacherProfile(loginId:String): MutableLiveData<TeacherProfileAllResponse>{
      return  dataModel.getTeacherProfileAllData(loginId)
    }
    fun updateTeacherProfile(request: TeacherProfileResponse, loginId: String):MutableLiveData<UnitResponse>{
        return dataModel.postTeacherProfileData(request,loginId)
    }
    fun uploadFile(file: File, loginId: String):MutableLiveData<ProfilePicResponse>{
        return dataModel.uploadFile(file,loginId)
    }


}