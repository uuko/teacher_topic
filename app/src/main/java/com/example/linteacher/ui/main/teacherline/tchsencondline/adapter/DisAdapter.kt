package com.example.linteacher.ui.main.teacherline.tchsencondline.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.linteacher.api.pojo.teacherline.Dis
import com.example.linteacher.databinding.ItemDisBinding


class DisAdapter(

) : RecyclerView.Adapter<DisAdapter.ViewHolder>() {
    private var list: List<Dis> = arrayListOf()
    fun setDataList(list: List<Dis>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val headerBinding: ItemDisBinding =
            ItemDisBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(headerBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val binding: ItemDisBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(response: Dis) {

            binding.dismainThesisName.text = response.dismainThesisName
            binding.disAuthor.text = response.disAuthor
            binding.disPublishY.text = response.disPublishY.toString()
            binding.disCollection.text = response.disCollection
            binding.disSeminarName.text = response.disSeminarName
            binding.disHostCountry.text = response.disHostCountry

        }

    }
}