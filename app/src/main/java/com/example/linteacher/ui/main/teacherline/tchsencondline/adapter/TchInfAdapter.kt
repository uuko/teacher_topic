package com.example.linteacher.ui.main.teacherline.tchsencondline.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.linteacher.api.pojo.teacherline.Tchinf
import com.example.linteacher.databinding.ItemTchInfBinding

class TchInfAdapter(
    var list: List<Tchinf>,
) : RecyclerView.Adapter<TchInfAdapter.ViewHolder>() {

    fun setDataList(list: MutableList<Tchinf>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val headerBinding: ItemTchInfBinding =
            ItemTchInfBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(headerBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val binding: ItemTchInfBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(response: Tchinf) {

            binding.infName.text = response.infName
            binding.infPubmainLicYear.text = response.infPubmainLicYear.toString()
            binding.infCategory.text = response.infCategory
            binding.infAuthorOrder.text = response.infAuthorOrder
            binding.infPublishHouse.text = response.infPublishHouse
            binding.infISBN.text = response.infISBN

        }

    }
}