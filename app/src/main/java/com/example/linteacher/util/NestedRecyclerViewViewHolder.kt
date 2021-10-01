package com.example.linteacher.util

import androidx.recyclerview.widget.RecyclerView

interface NestedRecyclerViewViewHolder {
    fun getId(): String
    fun getLayoutManager(): RecyclerView.LayoutManager?
}