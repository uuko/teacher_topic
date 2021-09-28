package com.example.linteacher.api.pojo.teacherdata.license.all

import com.example.linteacher.api.pojo.teacherdata.exp.ExpGetResponse
import com.example.linteacher.api.pojo.teacherdata.license.LicenseResponse

data class LicenseAllResponse (
    var list:List<LicenseResponse> = arrayListOf(),
    var error: String=""
)