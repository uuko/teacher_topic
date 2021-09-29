package com.example.linteacher.api.pojo.teacherdata.paper

data class PaperOneResponse(
    val loginId: Int=0,
    val `public`: Boolean=false,
    val tchSemester: Int=0,
    val tchYear: Int=0,
    val theAuthor: String="",
    val theCollCategory: String="",
    val theCorreAuthor: String="",
    val theCoupons: String="",
    val theId: Int=0,
    val theProject: String="",
    val thePublishMonth: String="",
    val thePublishType: String="",
    val thePublishYear: String="",
    val thePublishingcountry: String="",
    val thePubmain_licationName: String="",
    val theReviewer: String="",
    val theTransCooperation: String="",
    val themain_thesisName: String=""
)