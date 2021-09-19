package com.example.linteacher.api.pojo.teacherdata.exp.ui

import com.example.linteacher.api.pojo.teacherdata.exp.ExpGetResponse

data class ExpGetAllResponse(
    var list:List<ExpGetResponse> = arrayListOf(),
    var error: String=""
)