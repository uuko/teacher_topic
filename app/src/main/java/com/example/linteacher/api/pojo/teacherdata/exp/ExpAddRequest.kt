package com.example.linteacher.api.pojo.teacherdata.exp
data class ExpAddRequest(
    val expCategory: String,
    val expJobtitle: String,
    val expMechanismName: String,
    val expMechanismSort: String,
    val expPFtime: String,
    val expStartDate: String,
    val expStopDate: String,
    val loginId: Int,
    val `public`: Boolean
)
