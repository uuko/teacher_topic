package com.example.linteacher.ui.main.announce

interface ContentListener {
    interface View {
        fun onItemClick(position: Int, artical: Int)
    }
}