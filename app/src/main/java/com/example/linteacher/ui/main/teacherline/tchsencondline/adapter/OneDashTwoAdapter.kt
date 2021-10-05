package com.example.linteacher.ui.main.teacherline.tchsencondline.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.linteacher.api.pojo.teacherline.OneDashTwo
import com.example.linteacher.databinding.ItemOneDashTwoBinding
import com.example.linteacher.ui.admin.adminedituser.AdminEditActivity
import com.example.linteacher.ui.main.announce.Content
import com.example.linteacher.ui.main.teacherline.tchsencondline.TeacherSecondLineInterface

class OneDashTwoAdapter(
) : ListAdapter<OneDashTwo, OneDashTwoAdapter.ViewHolder>(DiffCallback()) {
    private class DiffCallback : DiffUtil.ItemCallback<OneDashTwo>() {
        //1
        override fun areItemsTheSame(
            oldItem: OneDashTwo,
            newItem: OneDashTwo
        ): Boolean {
            return oldItem.expJobtitle == newItem.expJobtitle
        }

        //2
        override fun areContentsTheSame(
            oldItem: OneDashTwo,
            newItem: OneDashTwo
        ): Boolean {
            return oldItem == newItem
        }
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
        holder.bind(getItem(position))
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