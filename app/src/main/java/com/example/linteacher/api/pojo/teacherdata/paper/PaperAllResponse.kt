package com.example.linteacher.api.pojo.teacherdata.paper

data class PaperAllResponse (
    var list: List<PaperResponse> = arrayListOf(),
    var error: String
        )