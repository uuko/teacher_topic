package com.example.linteacher.api.pojo.teacherdata.patent.data

import com.example.linteacher.api.BaseData
import com.example.linteacher.util.Config

class PatentBaseData(


        open var patProject:String="",
        open var patmainPatentName:String="",
        open var patCountry:String="",
        open var patType:String="",
        open var patReportCode:String="",
        open var patProgressStatus:String="",
        open var patReportEdata:String="",
        open var patAuthor:String="",
        open var patAppmainLicant:String="",
        open var patAppmainLicantType:String="",
        open var patAppmainLicationDate:String="",
        open var patEndDate:String="",
        open var patmainLicensingAgency:String="",
        open var patCertificateNumber:String="",


        open var patId:Int=0,
        open var loginId: Int=0,
        override var itemType:Int= Config.ORIGIN_VIEW_TYPE,
        open var `public`: Boolean=false,
        open var tchSemester: Int=0,
        open var tchYear: Int=0
): BaseData()
