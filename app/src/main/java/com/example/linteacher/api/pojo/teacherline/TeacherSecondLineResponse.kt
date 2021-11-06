package com.example.linteacher.api.pojo.teacherline

data class TeacherSecondLineResponse(

    val eMail: String = "",
    val introduce: String = "",
    val tchCoeDepartment: String = "",
    val tchDepartment: String = "",
    val tchDiploma: String = "",
    val tchMainDepartment: String = "",
    val tchName: String = "",
    val tchNameEN: String = "",
    val tchNumber: Int = 0,
    val tchPicUrl: String = "",
    val tchRireRank: String = "",
    val tchSchool: String = "",
    //
    val oneDashTwoList: List<OneDashTwo> = arrayListOf(),
    val licList: List<Lic> = arrayListOf(),
    val proList: List<Pro> = arrayListOf(),
    val eventList: List<Event> = arrayListOf(),
    val awardsList: List<Awards> = arrayListOf(),
    val tchinfList: List<Tchinf> = arrayListOf(),
    val theList: List<The> = arrayListOf(),
    val govList: List<Gov> = arrayListOf(),

    val disList: List<Dis> = arrayListOf(),


//
    val result: String = ""
)

data class Awards(
    val awaDate: String,
    val awaMechanismName: String,
    val awaName: String
)

data class Dis(
    val disAuthor: String,
    val disCollection: String,
    val disHostCountry: String,
    val disPublishY: String,
    val disSeminarName: String,
    val dismainThesisName: String
)

data class Event(
    val eveCategory: String,
    val eveName: String,
    val eveSort: String
)

data class Gov(
    val govJobType: String,
    val govProbjectType: String,
    val govProjectName: String
)

data class Lic(
    val licName: String,
    val licService: String,
    val licType: String
)

data class OneDashTwo(
    val expJobtitle: String,
    val expMechanismName: String,
    val expStartDate: String,
    val expStopDate: String
)

data class Pro(
    val proCaseName: String,
    val proNature: String,
    val proVendor: String
)

data class Tchinf(
    val infAuthorOrder: String,
    val infCategory: String,
    val infISBN: String,
    val infName: String,
    val infPublishHouse: String,
    val infPubmainLicYear: String
)

data class The(
    val theAuthor: String,
    val theCollCategory: String,
    val theCoupons: String,
    val thePublishMonth: String,
    val thePublishYear: String,
    val thePubmainLicatinNumber: String,
    val thePubmainLicationName: String,
    val themainThesisName: String
)
