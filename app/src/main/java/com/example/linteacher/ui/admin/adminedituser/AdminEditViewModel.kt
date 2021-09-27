package com.example.linteacher.ui.admin.adminedituser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linteacher.api.pojo.admin.changeautority.AdminChangeAuthorityRequest
import com.example.linteacher.api.pojo.admin.changeautority.AdminChangeAuthorityResponse
import com.example.linteacher.api.pojo.admin.list.AdminListTeacherAllResponse
import com.example.linteacher.api.pojo.admin.list.AdminListTeacherResponse

class AdminEditViewModel constructor(var repository:AdminEditRepository): ViewModel(){
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    fun listAllTeacher():MutableLiveData<AdminListTeacherAllResponse>{
        return repository.adminListUsers()
    }
    fun changeUserAuthority(item: AdminListTeacherResponse):MutableLiveData<AdminChangeAuthorityResponse>{
        var grade:String=item.grade
        if (grade.equals("B")){
            grade="C"
        }
        else if (grade.equals("C")){
            grade="B"
        }
        val adminChangeAuthorityRequest=
            AdminChangeAuthorityRequest(grade,
            loginId = item.loginId)
        return repository.changeUserAuthority(adminChangeAuthorityRequest)
    }


}
