package com.example.linteacher.api.pojo.teacherdata.paper

data class PaperUpdateRequest(
    val loginId: Int,
    val theAuthor: String,
    val theCollCategory: String,
    val theCorreAuthor: String,
    val theCoupons: String,
    val theId: Int,
    val theProject: String,
    val thePublishMonth: Int,
    val thePublishType: String,
    val thePublishYear: Int,
    val thePublishingcountry: String,
    val thePubmain_licatinNumber: String,
    val thePubmain_licationName: String,
    val theReviewer: String,
    val theTransCooperation: String,
    val themain_thesisName: String,
     var `public`: Boolean=false,

    )