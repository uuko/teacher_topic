package com.example.linteacher.ui.teacherdata.ui.license

import com.example.linteacher.api.pojo.teacherdata.license.ui.LicAddData
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicEditData
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicOriginData
import com.example.linteacher.api.pojo.teacherdata.off.ui.OffAddData
import com.example.linteacher.api.pojo.teacherdata.off.ui.OffEditData
import com.example.linteacher.api.pojo.teacherdata.off.ui.OffOriginData

interface LicInterface {
    interface View{
        fun onSaveClick(name: LicAddData, position: Int)
        fun onCancelClick( position: Int)
        fun onDeleteClick(expNumber: Int, position: Int)
        fun onEditSaveClick(name: LicEditData, position: Int)
        fun onEditClick(name: LicOriginData, position: Int)
        fun onEditCancelClick(position: Int,name: LicEditData)
    }
}