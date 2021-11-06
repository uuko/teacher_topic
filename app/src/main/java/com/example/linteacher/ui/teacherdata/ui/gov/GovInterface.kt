package com.example.linteacher.ui.teacherdata.ui.gov

import com.example.linteacher.api.pojo.teacherdata.adamic.data.AdemicEventBaseData
import com.example.linteacher.api.pojo.teacherdata.gov.data.GovBaseData

interface GovInterface {

    interface View{
        fun onSaveClick(name: GovBaseData, position: Int)
        fun onCancelClick( position: Int)
        fun onDeleteClick(expNumber: Int, position: Int)
        fun onEditSaveClick(name: GovBaseData, position: Int)
        fun onEditClick(name: String, position: Int)
        fun onEditCancelClick(position: Int,name: GovBaseData)
        fun onChangeVisibleClick(r: GovBaseData, position: Int)

    }
}