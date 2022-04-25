package com.example.linteacher.ui.main.profile

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.linteacher.api.MyApplication
import com.example.linteacher.api.pojo.TeacherBaseResponse
import com.example.linteacher.ui.main.teacherline.TeacherLineAllResponse
import com.example.linteacher.util.Config
import com.example.linteacher.util.preference.LoginPreferences

class ProfileViewModel(val dataModel: ProfileRepository) : ViewModel() {
    private var idStringData: MutableLiveData<String> = MutableLiveData<String>()
    val teacherLiveData: LiveData<TeacherBaseResponse> =
        Transformations.switchMap(idStringData) { address ->
            Log.e("TeacherLineViewModel", "Transformations: ")
            dataModel.getTeacherData(address)
        }

    init {
        val loginPreferences = LoginPreferences(MyApplication.sContext)
        if (loginPreferences.getTeacherGrade().equals(Config.TEACHER)) {
            getTeacherData(loginPreferences.getLoginId())
        }

    }

    fun getTeacherData(id: String) {
        idStringData.value = id
//        return dataModel.getTeacherData(id)
    }
}