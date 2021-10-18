package com.example.linteacher.ui.teacherdata.ui.technologytrans

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.linteacher.api.pojo.teacherdata.techtransfer.TechTransFerFirstResponse
import com.example.linteacher.databinding.ItemTransferFirstBinding
import com.example.linteacher.ui.teacherdata.ui.technologytrans.TechTransferAdapter.ViewHolder

class TechTransferAdapter(
    private var list: List<TechTransFerFirstResponse>,
    private val listener: TechTransFerListener.View
) : RecyclerView.Adapter<ViewHolder>() {

    fun setDataList(list: List<TechTransFerFirstResponse>) {
        this.list = list;
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemTransferFirstBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding = itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], listener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val binding: ItemTransferFirstBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(
            response: TechTransFerFirstResponse,
            listener: TechTransFerListener.View
        ) {
            binding.tecTransferName.text = response.tecTransferName
            binding.tecTransfer.text = response.tecTransfer
            binding.tecNumber.text = response.tecNumber

            binding.editButton.setOnClickListener {
                listener.onItemClick(response.tecId)
            }
        }

    }
}