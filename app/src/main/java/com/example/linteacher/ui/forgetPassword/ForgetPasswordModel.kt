package com.example.linteacher.ui.forgetPassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.forgetPawwrod.GetCheckTokenResponse
import com.example.linteacher.api.pojo.forgetPawwrod.SavePasswordRequest
import com.example.linteacher.api.pojo.login.LoginAllResponse
import com.example.linteacher.api.pojo.login.LoginRequest
import com.example.linteacher.api.pojo.teacherdata.adamic.AcademicChangeVisibleRequest
import com.example.linteacher.api.pojo.teacherdata.award.AwardAllOneResponse
import com.example.linteacher.api.pojo.teacherdata.award.AwardAllResponse
import com.example.linteacher.api.pojo.teacherdata.award.AwardPostRequest
import com.example.linteacher.api.pojo.teacherdata.award.AwardUpdateRequest

class ForgetPasswordModel constructor(var dataModel: ForgetPasswordRepository): ViewModel(){
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(true)



    //loginId就好,回傳值是list<class>
    fun getCheckToken( token:String): MutableLiveData<UnitResponse> {
        return dataModel.getCheckToken(token)
    }



    fun postSavaPassword(savePasswordRequest: SavePasswordRequest): MutableLiveData<UnitResponse> {
        return dataModel.postSavaPassword(savePasswordRequest)
    }


    fun postSendMail(email: String): MutableLiveData<UnitResponse> {
        return dataModel.postSendMail(email)
    }




}
