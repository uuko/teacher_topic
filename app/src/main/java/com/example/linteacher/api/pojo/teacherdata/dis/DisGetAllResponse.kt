package com.example.linteacher.api.pojo.teacherdata.dis

data class DisGetAllResponse(
    var list: List<DisGetResponse> = arrayListOf(),
    var result: String = ""
)