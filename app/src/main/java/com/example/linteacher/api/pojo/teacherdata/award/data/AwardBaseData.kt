package com.example.linteacher.api.pojo.teacherdata.award.data

import com.example.linteacher.api.BaseData
import com.example.linteacher.api.pojo.teacherdata.award.AwardResponse
import com.example.linteacher.util.Config

open class AwardBaseData(

         var awaPlan:String="",
         open var awaName:String="",
         open var awaMechanismName:String="",
         open var awaSort:String="",
         open var awaCampus:String="",
         open var awaCountry:String="",
         open var awaDate:String="",
         open var awaId:Int=0,
        override var itemType:Int= Config.ORIGIN_VIEW_TYPE,
         open var loginId: Int=0,
         open var `public`: Boolean=false,
         open var tchSemester: Int=0,
         open var tchYear: Int=0
):BaseData()

