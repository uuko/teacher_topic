package com.example.linteacher.ui.main.announce

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.linteacher.databinding.*
import com.example.linteacher.ui.teacherdata.ui.experience.ExperienceFragment
import java.util.*

class ImportantContentAdapter(
    private var list: ArrayList<Content.ImportantInnerAnnounce>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun setDataList(list: ArrayList<Content.ImportantInnerAnnounce>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding =
            ItemCarsoulBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size;
    }

    class ViewHolder(
        private val binding: ItemCarsoulBinding, private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            items: Content.ImportantInnerAnnounce,
            position: Int,
        ) {


        }
    }
}