package com.example.linteacher.api.pojo.teacherdata.gov

import com.example.linteacher.api.pojo.teacherdata.gov.GovResponse

class GovAllResponse (
        var list:List<GovResponse> = arrayListOf(),
        var error: String=""
)
