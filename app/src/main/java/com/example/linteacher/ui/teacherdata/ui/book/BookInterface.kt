package com.example.linteacher.ui.teacherdata.ui.book

import com.example.linteacher.api.pojo.teacherdata.adamic.data.AdemicEventBaseData
import com.example.linteacher.api.pojo.teacherdata.book.data.BookBaseData

interface BookInterface {

    interface View{
        fun onSaveClick(name: BookBaseData, position: Int)
        fun onCancelClick( position: Int)
        fun onDeleteClick(expNumber: Int, position: Int)
        fun onEditSaveClick(name: BookBaseData, position: Int)
        fun onEditClick(name: String, position: Int)
        fun onEditCancelClick(position: Int,name: BookBaseData)
        fun onChangeVisibleClick(r: BookBaseData, position: Int)

    }
}