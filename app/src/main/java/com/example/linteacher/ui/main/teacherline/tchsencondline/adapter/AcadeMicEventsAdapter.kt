package com.example.linteacher.ui.main.teacherline.tchsencondline.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.linteacher.api.pojo.teacherline.Event
import com.example.linteacher.databinding.ItemMainAcademiceventsBinding

class AcadeMicEventsAdapter(

) : RecyclerView.Adapter<AcadeMicEventsAdapter.ViewHolder>() {

    private var list: List<Event> = arrayListOf()
    fun setDataList(list: List<Event>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val headerBinding: ItemMainAcademiceventsBinding =
            ItemMainAcademiceventsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(headerBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val binding: ItemMainAcademiceventsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(response: Event) {

            binding.eveName.text = response.eveName
            binding.eveCategory.text = response.eveCategory
            binding.eveSort.text = response.eveSort

        }

    }
}