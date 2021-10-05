package com.example.linteacher.ui.main.teacherline.tchsencondline.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.linteacher.api.pojo.teacherline.Awards
import com.example.linteacher.databinding.ItemTchawardsBinding

class TchAwardsAdapter(

) : RecyclerView.Adapter<TchAwardsAdapter.ViewHolder>() {
    private var list: List<Awards> = arrayListOf()
    fun setDataList(list: List<Awards>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val headerBinding: ItemTchawardsBinding =
            ItemTchawardsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(headerBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val binding: ItemTchawardsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(response: Awards) {

            binding.awaName.text = response.awaName
            binding.awaDate.text = response.awaDate
            binding.awaMechanismName.text = response.awaMechanismName

        }

    }
}