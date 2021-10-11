package com.example.linteacher.api.pojo.teacherdata.book

import com.example.linteacher.api.pojo.teacherdata.award.AwardResponse

class BookAllResponse  (
        var list:List<BookResponse> = arrayListOf(),
        var error: String=""
)