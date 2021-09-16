package com.example.linteacher.api.pojo

import java.io.Serializable


data class TeacherLineResponse(
    val eMail: String="",
    val introduce:  String="",
    val tchCoeDepartment: String="",
    val tchDepartment:  String="",
    val tchDiploma:  String="",
    val tchMainDepartment:  String="",
    val tchName:  String="",
    val tchNameEN:  String="",
    val tchNumber: Int=0,
    val tchPicUrl:  String="",
    val tchRireRank:  String="",
    val tchSchool:  String=""

):Serializable