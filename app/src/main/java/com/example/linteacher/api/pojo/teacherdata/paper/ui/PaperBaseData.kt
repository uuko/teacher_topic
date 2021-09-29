package com.example.linteacher.api.pojo.teacherdata.paper.ui

import com.example.linteacher.api.BaseData

abstract class PaperBaseData (
    open var theId:Int=0,
    open var themain_thesisName:String="",
    open var theAuthor :String="",
):BaseData()