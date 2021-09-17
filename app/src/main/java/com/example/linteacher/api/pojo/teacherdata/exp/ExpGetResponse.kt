package com.example.linteacher.api.pojo.teacherdata.exp



data class ExpGetResponse(
    val expCategory: String,
    val expJobtitle: String,
    val expMechanismName: String,
    val expMechanismSort: String,
    val expNumber: Int,
    val expPFtime: String,
    val expRemarks: String,
    val expStartDate: String,
    val expStopDate: String,
    val loginId: Int,
    val `public`: Boolean,
    val tchSemester: Int,
    val tchYear: Int
)