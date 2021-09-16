package com.example.linteacher.ui.adminaddteacher

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linteacher.api.pojo.admin.addteacher.AddTeacherAllResponse
import com.example.linteacher.api.pojo.admin.addteacher.AddTeacherRequest
import com.example.linteacher.api.pojo.login.LoginAllResponse
import com.example.linteacher.api.pojo.login.LoginRequest
import com.example.linteacher.ui.login.LoginRepository

class AddTeacherViewModel constructor(var repository: AddTeacherRepository): ViewModel(){
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(true)

    fun registerTeacher(name:String, pwd:String,email:String):MutableLiveData<AddTeacherAllResponse>{
        val requst= AddTeacherRequest(
            account = email,
            email=email,
            password = pwd,
            tchName = name
        )
        return repository.registerTeacher(requst)
    }
}