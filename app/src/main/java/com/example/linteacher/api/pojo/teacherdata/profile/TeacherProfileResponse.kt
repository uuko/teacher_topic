package com.example.linteacher.api.pojo.teacherdata.profile

import java.io.Serializable

data class TeacherProfileResponse(
    val eMail: String="",
    val introduce: Any?=null,
    var sex: Any?=null,
    val tch106PaySalary: Any?=null,
    var tch107PaySalary: Any?=null,
    var tchAboriginal: Any?=null,
    val tchAdminiJob: Any?=null,
    val tchAppointDate: Any?=null,
    var tchBirthday: Any?=null,
    var tchCertificateNumber: Any?=null,
    var tchCertificateRank: Any?=null,
    val tchCoeDepartment: Any?=null,
    var tchComplyLaw: Any?=null,
    var tchCountry: Any?=null,
    var tchDepartment: Any?=null,
    var tchDiploma: Any?=null,
    val tchEstablishment: Any?=null,
    var tchEvaNumber: Any?=null,
    var tchExpertise: Any?=null,
    var tchFiestAssistant: Any?=null,
    val tchFullPartPosition: Any?=null,
    var tchFullTime: Any?=null,
    var tchHireNumber: Any?=null,
    val tchHureDate: Any?=null,
    var tchIdNumberI: Any?=null,
    val tchIdNumberP: Any?=null,
    val tchIdNumberR: Any?=null,
    val tchIdType: Any?=null,
    var tchIsAboriginal: Any?=null,
    val tchKind: Any?=null,
    val tchKindDepartment: Any?=null,
    val tchKindIndustry: Any?=null,
    val tchMainDepartment: Any?=null,
    var tchName: String?=null,
    var tchNameEN: Any?=null,
    val tchNumber: Int=0,
    val tchOriginalUnit: Any?=null,
    val tchPartAdmini: Any?=null,
    var tchPicUrl: String?="",
    val tchReinstateDate: Any?=null,
    val tchResignDate: Any?=null,
    var tchRireRank: Any?=null,
    var tchRireYear: Any?=null,
    val tchScePurpose: Any?=null,
    val tchSceWhemain_ther: Any?=null,
    val tchSchDate: Any?=null,
    var tchSchool: Any?=null,
    var tchSchoolType: Any?=null,
    val tchSecUnit: Any?=null,
    val tchSemester: Int=0,
    val tchSixtyFive: Any?=null,
    val tchState: Any?=null,
    val tchStopDate: Any?=null,
    val tchTow: Any?="",
    var tchTwoFour: Any?="",
    var tchType: Any?="",
    val tchValidationStatus: Any?="",
    val tchYear: Int=0,
    var tchmain_licenseNumber: Any?=""
):Serializable{

}