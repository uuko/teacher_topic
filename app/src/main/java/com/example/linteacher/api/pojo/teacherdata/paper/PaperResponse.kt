package com.example.linteacher.api.pojo.teacherdata.paper

data class PaperResponse(
    val loginId: Int,
    val `public`: Boolean,
    val tchSemester: Int,
    val tchYear: Int,
    val theAuthor: String,
    val theCollCategory: String,
    val theCorreAuthor: String,
    val theCoupons: String,
    val theId: Int,
    val theProject: String,
    val thePublishMonth: String,
    val thePublishType: String,
    val thePublishYear: String,
    val thePublishingcountry: String,
    val thePubmain_licationName: String,
    val theReviewer: String,
    val theTransCooperation: String,
    val themain_thesisName: String
)