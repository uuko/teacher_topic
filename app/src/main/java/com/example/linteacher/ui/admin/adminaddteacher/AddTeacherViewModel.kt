package com.example.linteacher.ui.admin.adminaddteacher

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linteacher.api.pojo.admin.addteacher.AddTeacherAllResponse
import com.example.linteacher.api.pojo.admin.addteacher.AddTeacherRequest

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