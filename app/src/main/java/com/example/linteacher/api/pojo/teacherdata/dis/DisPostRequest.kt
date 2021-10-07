package com.example.linteacher.api.pojo.teacherdata.dis

data class DisPostRequest(
    val disAuthor: String,
    val disCollection: String,
    val disCorreAuthor: String,
    val disED: String,
    val disFD: String,
    val disHostCity: String,
    val disHostCountry: String,
    val disIsCountry: String,
    val disProject: String,
    val disPublishY: String,
    val disReviewer: String,
    val disSeminarName: String,
    val dismainThesisName: String,
    val loginId: Int,
    val main_disYear: String
)