package com.example.linteacher.ui.main.teacherline.tchsencondline.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.linteacher.api.pojo.teacherline.Pro
import com.example.linteacher.databinding.ItemProfeserviceBinding
import com.example.linteacher.ui.main.teacherline.tchsencondline.TeacherSecondLineInterface

class ProfeServiceAdapter(
    var list: List<Pro>,
) : RecyclerView.Adapter<ProfeServiceAdapter.ViewHolder>() {

    fun setDataList(list: MutableList<Pro>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val headerBinding: ItemProfeserviceBinding =
            ItemProfeserviceBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(headerBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val binding: ItemProfeserviceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(response: Pro) {
            binding.proCaseName.text = response.proCaseName
            binding.proVendor.text = response.proVendor
            binding.proNature.text = response.proNature
        }

    }
}
