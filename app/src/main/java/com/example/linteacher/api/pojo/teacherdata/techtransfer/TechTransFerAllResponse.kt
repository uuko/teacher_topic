package com.example.linteacher.api.pojo.teacherdata.techtransfer

import com.example.linteacher.api.pojo.teacherdata.paper.PaperResponse

data class TechTransFerAllResponse (
    var list: List<TechTransFerFirstResponse> = arrayListOf(),
    var error: String

        )
