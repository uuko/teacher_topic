package com.example.linteacher.ui.main.teacherline

import com.example.linteacher.api.pojo.teacherline.TeacherLineResponse

data class TeacherLineAllResponse (
    val list: List<TeacherLineResponse> = mutableListOf(),
    val error:String=""
        )