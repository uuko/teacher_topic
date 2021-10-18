package com.example.linteacher.ui.teacherdata.ui.technologytrans.editfirst

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.linteacher.api.pojo.teacherdata.techtransfer.TechChgeCompanyModel
import com.example.linteacher.databinding.ItemTransterInnerBinding
import com.example.linteacher.ui.teacherdata.ui.technologytrans.TechTransFerListener

class TechTransFerInnerAdapter (
                                private val listener: TechTransFerListener.View
) : ListAdapter<TechChgeCompanyModel,TechTransFerInnerAdapter.ViewHolder>(DiffCallback()) {
    private class DiffCallback : DiffUtil.ItemCallback<TechChgeCompanyModel>() {
        //1
        override fun areItemsTheSame(
            oldItem: TechChgeCompanyModel,
            newItem: TechChgeCompanyModel
        ): Boolean {
            return oldItem.tecCompanyId == newItem.tecCompanyId
        }

        //2
        override fun areContentsTheSame(
            oldItem: TechChgeCompanyModel,
            newItem: TechChgeCompanyModel
        ): Boolean {
            return oldItem == newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemTransterInnerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding = itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }



    class ViewHolder(val binding:  ItemTransterInnerBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(
            response: TechChgeCompanyModel,
            listener: TechTransFerListener.View
        ) {
            binding.tecTransferAmount .text = response.tecTransferAmount
            binding.tecTransferVendor .text = response.tecTransferVendor
            binding.tecTransferNumber .text = response.tecTransferNumber
//
            binding.editButton.setOnClickListener {
                listener.onItemClick(response.tecCompanyId)
            }
        }

    }
}
