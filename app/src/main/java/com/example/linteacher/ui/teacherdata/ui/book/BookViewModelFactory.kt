package com.example.linteacher.ui.teacherdata.ui.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.linteacher.ui.teacherdata.ui.book.BookRepository
import com.example.linteacher.ui.teacherdata.ui.book.BookViewModel

class BookViewModelFactory (private val dataModel: BookRepository): ViewModelProvider.Factory{
    //重點(?)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
            return BookViewModel(dataModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}