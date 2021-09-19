package com.example.linteacher.api.pojo.teacherdata.exp.ui

abstract   class ExpBaseData (
    open var company:String="",
    open var job:String="",
    open var itemType:Int=0,
    open var startDate:String="",
    open var endDate:String="",
    open var expType:String="",
    open var coopAgency:String="",
    open var isPublic:Boolean=false
        )