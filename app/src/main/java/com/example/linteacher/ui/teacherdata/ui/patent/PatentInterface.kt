package com.example.linteacher.ui.teacherdata.ui.patent

import com.example.linteacher.api.pojo.teacherdata.patent.data.PatentBaseData

interface PatentInterface {
    interface View{
        fun onSaveClick(name: PatentBaseData, position: Int)
        fun onCancelClick( position: Int)
        fun onDeleteClick(expNumber: Int, position: Int)
        fun onEditSaveClick(name: PatentBaseData, position: Int)
        fun onEditClick(name: String, position: Int)
        fun onEditCancelClick(position: Int,name: PatentBaseData)
    }
}