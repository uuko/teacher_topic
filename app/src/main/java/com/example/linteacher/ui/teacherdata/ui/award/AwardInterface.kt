package com.example.linteacher.ui.teacherdata.ui.award


import com.example.linteacher.api.pojo.teacherdata.adamic.data.AdemicEventBaseData
import com.example.linteacher.api.pojo.teacherdata.award.data.AwardBaseData
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicAddData
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicEditData
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicOriginData

interface AwardInterface {

    interface View{
        fun onSaveClick(name: AwardBaseData, position: Int)
        fun onCancelClick( position: Int)
        fun onDeleteClick(expNumber: Int, position: Int)
        fun onEditSaveClick(name: AwardBaseData, position: Int)
        fun onEditClick(name: String, position: Int)
        fun onEditCancelClick(position: Int,name: AwardBaseData)
        fun onChangeVisibleClick(r: AwardBaseData, position: Int)

    }

}