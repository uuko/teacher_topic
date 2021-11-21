package com.example.linteacher.api.pojo.teacherdata.gov

class GovUpdateRequest (
        open var govProjectName:String="",
        open var govProbjectNumber:String="",
        open var govProbjectType:String="",
        open var govProjectType:String="",
        open var govProjectNature:String="",
        open var govFD:String="",
        open var govED:String="",
        open var govJobType:String="",
        open var govMoneyState:String="",
        open var govMainfund:String="",
        open var govUnitName:String="",
        open var govSecAund:String="",
        open var govBenefitDepartment:String="",
        open var govComUnit:String="",
        open var govTeamworkUnit:String="",
        open var govProjectAmount:String="",
        open var govmainGovAmount:String="",
        open var govEntAmount:String="",
        open var govOthAmount:String="",
        open var govSchAmount:String="",
        open var govOthIn:String="",
        open var govToOth:String="",
        open var `public`: Boolean=false,



        open var govId:Int=0,
        var loginId:Int=0 //重點本人
)
