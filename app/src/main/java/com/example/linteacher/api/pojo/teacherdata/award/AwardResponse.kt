package com.example.linteacher.api.pojo.teacherdata.award

import com.example.linteacher.api.BaseData
import com.example.linteacher.api.pojo.teacherdata.award.data.AwardBaseData
import com.example.linteacher.util.Config

open class AwardResponse (
         open var awaPlan:String="",
         open var awaName:String="",
         open var awaMechanismName:String="",
         open var awaSort:String="",
         open var awaCampus:String="",
         open var awaCountry:String="",
         open var awaDate:String="",
         open var awaId:Int=0,
         open var loginId: Int=0,
         open var `public`: Boolean=false,
         open var tchSemester: Int=0,
         open var tchYear: Int=0,
)