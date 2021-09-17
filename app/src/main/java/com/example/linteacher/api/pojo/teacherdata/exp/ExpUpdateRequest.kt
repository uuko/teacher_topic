package com.example.linteacher.api.pojo.teacherdata.exp
data class ExpUpdateRequest(
    val expCategory: String,
    val expJobtitle: String,
    val expMechanismName: String,
    val expMechanismSort: String,
    val expNumber: Int,
    val expPFtime: String,
    val expStartDate: String,
    val expStopDate: String,
    val loginId: Int,
    val `public`: Boolean
)
