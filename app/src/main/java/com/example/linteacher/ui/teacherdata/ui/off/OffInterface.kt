package com.example.linteacher.ui.teacherdata.ui.off

import com.example.linteacher.api.pojo.teacherdata.off.ui.OffAddData
import com.example.linteacher.api.pojo.teacherdata.off.ui.OffEditData
import com.example.linteacher.api.pojo.teacherdata.off.ui.OffOriginData

interface OffInterface {
    interface View{
        fun onSaveClick(name: OffAddData, position: Int)
        fun onCancelClick( position: Int)
        fun onDeleteClick(expNumber: Int, position: Int)
        fun onEditSaveClick(name: OffEditData, position: Int)
        fun onEditClick(name: OffOriginData, position: Int)
        fun onEditCancelClick(position: Int,name: OffEditData)
    }
}