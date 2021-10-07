package com.example.linteacher.api.pojo.teacherdata.dis


data class DisGetResponse(
    val disAuthor: String = "",
    val disCollection: String = "",
    val disCorreAuthor: String = "",
    val disED: String = "",
    val disFD: String = "",
    val disHostCity: String = "",
    val disHostCountry: String = "",
    val disId: Int = 0,
    val disIsCountry: Boolean,
    val disProject: String = "",
    val disPublishY: String = "",
    val disReviewer: Boolean,
    val disSeminarName: String = "",
    val dismainThesisName: String = "",
    val loginId: Int = 0,
    val main_disYear: String = "",
    val `public`: Boolean,
    val tchSemester: Int,
    val tchYear: Int
)