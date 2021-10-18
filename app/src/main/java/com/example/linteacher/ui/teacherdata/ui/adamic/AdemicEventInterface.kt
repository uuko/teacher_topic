package com.example.linteacher.ui.teacherdata.ui.adamic

import com.example.linteacher.api.pojo.teacherdata.adamic.data.AdemicEventBaseData
import com.example.linteacher.api.pojo.teacherdata.award.data.AwardBaseData

interface AdemicEventInterface {

    interface View{
        fun onSaveClick(name: AdemicEventBaseData, position: Int)
        fun onCancelClick( position: Int)
        fun onDeleteClick(expNumber: Int, position: Int)
        fun onEditSaveClick(name: AdemicEventBaseData, position: Int)
        fun onEditClick(name: String, position: Int)
        fun onEditCancelClick(position: Int,name: AdemicEventBaseData)
        fun onChangeVisibleClick(r: AdemicEventBaseData, position: Int)
    }
}