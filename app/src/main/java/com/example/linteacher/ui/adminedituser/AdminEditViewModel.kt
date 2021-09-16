package com.example.linteacher.ui.adminedituser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linteacher.api.pojo.admin.changeautority.AdminChangeAuthorityRequest
import com.example.linteacher.api.pojo.admin.changeautority.AdminChangeAuthorityResponse
import com.example.linteacher.api.pojo.admin.list.AdminListTeacherAllResponse
import com.example.linteacher.util.preference.LoginPreferences
import kotlin.math.log

class AdminEditViewModel constructor(var repository:AdminEditRepository): ViewModel(){
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    fun listAllTeacher():MutableLiveData<AdminListTeacherAllResponse>{
        return repository.adminListUsers()
    }
    fun changeUserAuthority(loginPreferences: LoginPreferences):MutableLiveData<AdminChangeAuthorityResponse>{
        var grade:String=loginPreferences.getTeacherGrade()
        if (loginPreferences.getTeacherGrade().equals("B")){
            grade="C"
        }
        else if (loginPreferences.getTeacherGrade().equals("C")){
            grade="B"
        }
        val adminChangeAuthorityRequest=
            AdminChangeAuthorityRequest(grade,
            loginId = loginPreferences.getTeacherId().toInt())
        return repository.changeUserAuthority(adminChangeAuthorityRequest)
    }


}
