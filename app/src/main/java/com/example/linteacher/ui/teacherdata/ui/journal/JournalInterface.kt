package com.example.linteacher.ui.teacherdata.ui.journal

import com.example.linteacher.api.pojo.teacherdata.dis.ui.DisAddData
import com.example.linteacher.api.pojo.teacherdata.dis.ui.DisEditData
import com.example.linteacher.api.pojo.teacherdata.dis.ui.DisOriginData
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicAddData
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicEditData
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicOriginData

interface JournalInterface {
    interface View {
        fun onSaveClick(name: DisAddData, position: Int)
        fun onCancelClick(position: Int)
        fun onDeleteClick(expNumber: Int, position: Int)
        fun onEditSaveClick(name: DisEditData, position: Int)
        fun onEditClick(name: DisOriginData, position: Int)
        fun onEditCancelClick(position: Int, name: DisEditData)
    }
}