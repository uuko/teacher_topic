package com.example.linteacher.api.pojo.teacherdata.patent

class PatentUpdateRequest ( open var patProject:String="",
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
                            var loginId:Int=0 //重點本人

)
