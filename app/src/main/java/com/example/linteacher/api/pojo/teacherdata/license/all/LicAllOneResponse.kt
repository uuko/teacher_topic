package com.example.linteacher.api.pojo.teacherdata.license.all

import com.example.linteacher.api.pojo.teacherdata.license.LicOneResponse
import com.example.linteacher.api.pojo.teacherdata.license.LicenseResponse

data class LicAllOneResponse (
    var list:LicOneResponse =LicOneResponse(),
    var error: String=""
        )