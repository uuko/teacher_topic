package com.example.linteacher.api.pojo.teacherdata.exp
data class ExpOneGetResponse(
    val expCategory: String?="",
    val expJobtitle: String?="",
    val expMechanismName: String?="",
    val expMechanismSort: String?="",
    val expNumber: Int?=-1,
    val expPFtime: String?="",
    val expRemarks: String?="",
    val expStartDate: String?="",
    val expStopDate: String?="",
    val loginId: Int?=-1,
    val `public`: Boolean?=false,
    val tchSemester: Int?=-1,
    val tchYear: Int?=-2
)
