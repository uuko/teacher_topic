package com.example.linteacher.api.pojo.teacherdata.award

import com.example.linteacher.api.pojo.teacherdata.license.LicOneResponse
import com.example.linteacher.api.pojo.teacherdata.license.LicenseResponse

class AwardAllResponse (
        var list:List<AwardResponse> = arrayListOf(),
        var error: String=""
        )