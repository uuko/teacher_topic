package com.example.linteacher.api.pojo.teacherdata.book.data

import com.example.linteacher.api.BaseData
import com.example.linteacher.util.Config

class BookBaseData(
        open var   infCategory:String="",
        open var   infWhemainTher:String="",
        open var   infName:String="",
        open var   infAudit:String="",
        open var   infLanguage:String="",
        open var   infCoop:String="",
        open var   infAuthorOrder:String="",
        open var   infPubmainLicYear:String="",
        open var   infPubmainLicMonth:String="",
        open var  infPublishHouse:String="",
        open var  infISBN:String="",
        open var  infPlan:String="",
        open var  infCorreAuthor:String="",
        open var infNumber:Int=0,
        override var itemType:Int= Config.ORIGIN_VIEW_TYPE,
        open var loginId: Int=0,
        open var `public`: Boolean=false,
        open var tchSemester: Int=0,
        open var tchYear: Int=0
): BaseData()
