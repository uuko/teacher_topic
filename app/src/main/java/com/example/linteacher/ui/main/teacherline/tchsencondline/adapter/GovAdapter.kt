package com.example.linteacher.ui.main.teacherline.tchsencondline.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.linteacher.api.pojo.teacherline.Gov
import com.example.linteacher.databinding.ItemGovBinding

class GovAdapter(

) : RecyclerView.Adapter<GovAdapter.ViewHolder>() {
    private var list: List<Gov> = arrayListOf()
    fun setDataList(list: List<Gov>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val headerBinding: ItemGovBinding =
            ItemGovBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(headerBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val binding: ItemGovBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(response: Gov) {

            binding.govProjectName.text = response.govProjectName
            binding.govProbjectType.text = response.govProbjectType
            binding.govJobType.text = response.govJobType.toString()

        }

    }
}