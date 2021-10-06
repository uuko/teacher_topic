package com.example.linteacher.api.pojo.teacherdata.dis

data class DisGetOneResponse(
    var result: String = "",
    val disAuthor: String = "",
    val disCollection: String = "",
    val disCorreAuthor: String = "",
    val disED: String = "",
    val disFD: String = "",
    val disHostCity: String = "",
    val disHostCountry: String = "",
    val disId: Int = 0,
    val disIsCountry: String = "",
    val disProject: String = "",
    val disPublishY: String = "",
    val disReviewer: String = "",
    val disSeminarName: String = "",
    val dismainThesisName: String = "",
    val loginId: Int = 0,
    val main_disYear: String = "",
    val `public`: Boolean = false,
    val tchSemester: Int = 0,
    val tchYear: Int = 0
)