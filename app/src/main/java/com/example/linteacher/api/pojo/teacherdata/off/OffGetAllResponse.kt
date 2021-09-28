package com.example.linteacher.api.pojo.teacherdata.off

import com.example.linteacher.api.pojo.teacherdata.exp.ExpGetResponse

data class OffGetAllResponse
    (
    var list: List<OffGetResponse> = arrayListOf(),
    var error: String = ""
)