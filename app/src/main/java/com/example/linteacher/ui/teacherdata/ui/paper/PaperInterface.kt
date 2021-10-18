package com.example.linteacher.ui.teacherdata.ui.paper

import com.example.linteacher.api.pojo.teacherdata.adamic.data.AdemicEventBaseData
import com.example.linteacher.api.pojo.teacherdata.paper.ui.PaperAddData
import com.example.linteacher.api.pojo.teacherdata.paper.ui.PaperEditData
import com.example.linteacher.api.pojo.teacherdata.paper.ui.PaperOriginData

interface PaperInterface {
    interface View{
        fun onSaveClick(name: PaperAddData, position: Int)
        fun onCancelClick( position: Int)
        fun onDeleteClick(expNumber: Int, position: Int)
        fun onEditSaveClick(name: PaperEditData, position: Int)
        fun onEditClick(name: PaperOriginData, position: Int)
        fun onEditCancelClick(position: Int, name: PaperEditData)
        fun onChangeVisibleClick(r: PaperOriginData, position: Int)

    }
}