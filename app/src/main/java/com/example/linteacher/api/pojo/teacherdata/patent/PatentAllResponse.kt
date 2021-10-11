package com.example.linteacher.api.pojo.teacherdata.patent

import com.example.linteacher.api.pojo.teacherdata.patent.PatentResponse

class PatentAllResponse (
        var list:List<PatentResponse> = arrayListOf(),
        var error: String=""
)
