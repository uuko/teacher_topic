package com.example.linteacher.ui.main.teacherline.tchsencondline.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.linteacher.api.pojo.teacherline.Lic
import com.example.linteacher.api.pojo.teacherline.Pro
import com.example.linteacher.databinding.ItemLicBinding

class LicAdapter(

) : RecyclerView.Adapter<LicAdapter.ViewHolder>() {
    private var list: List<Lic> = arrayListOf()
    fun setDataList(list: List<Lic>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val headerBinding: ItemLicBinding =
            ItemLicBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(headerBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val binding: ItemLicBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(response: Lic) {

            binding.licName.text = response.licName
            binding.licService.text = response.licService
            binding.licType.text = response.licType
        }

    }
}