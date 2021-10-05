package com.example.linteacher.ui.main.teacherline.tchsencondline.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.linteacher.api.pojo.teacherline.The
import com.example.linteacher.databinding.ItemTheBinding

class TchTheAdapter(

) : RecyclerView.Adapter<TchTheAdapter.ViewHolder>() {
    private var list: List<The> = arrayListOf()
    fun setDataList(list: List<The>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val headerBinding: ItemTheBinding =
            ItemTheBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(headerBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val binding: ItemTheBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(response: The) {

            binding.thePubmainLicationName.text = response.thePubmainLicationName
            binding.theCoupons.text = response.theCoupons
            binding.thePubmainLicatinNumber.text = response.thePubmainLicatinNumber.toString()
            binding.thePublishYear.text = response.thePublishYear
            binding.thePublishMonth.text = response.thePublishMonth
            binding.theCollCategory.text = response.theCollCategory
            binding.themainThesisName.text = response.themainThesisName
            binding.theAuthor.text = response.theAuthor
        }

    }
}