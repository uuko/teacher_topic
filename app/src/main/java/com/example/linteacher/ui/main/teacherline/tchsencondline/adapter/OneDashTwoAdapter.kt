package com.example.linteacher.ui.main.teacherline.tchsencondline.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.linteacher.api.pojo.teacherline.OneDashTwo
import com.example.linteacher.databinding.ItemOneDashTwoBinding
import com.example.linteacher.ui.admin.adminedituser.AdminEditActivity
import com.example.linteacher.ui.main.teacherline.tchsencondline.TeacherSecondLineInterface

class OneDashTwoAdapter(
    var list: List<OneDashTwo>,
) : RecyclerView.Adapter<OneDashTwoAdapter.ViewHolder>() {

    fun setDataList(list: MutableList<OneDashTwo>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val headerBinding: ItemOneDashTwoBinding =
            ItemOneDashTwoBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(headerBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val binding: ItemOneDashTwoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(response: OneDashTwo) {

            binding.expMechanismName.text = response.expMechanismName
            binding.expJobtitle.text = response.expJobtitle
            binding.expStartDate.text = response.expStartDate
            binding.expStopDate.text = response.expStopDate
        }

    }
}