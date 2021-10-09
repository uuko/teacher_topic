package com.example.linteacher.api.pojo.teacherdata.award

import com.example.linteacher.util.Config

class AwardPostRequest( var awaPlan:String="",
                        var awaName:String="",
                        var awaMechanismName:String="",
                        var awaSort:String="",
                        var awaCampus:String="",
                        var awaCountry:String="",
                        var awaDate:String="",
                        var loginId:Int=0 //重點本人

)