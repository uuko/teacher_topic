package com.example.linteacher.ui.main.banneredit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.example.linteacher.api.pojo.banner.BannerResponse
import com.example.linteacher.databinding.ItemBannerEditBinding

class BannerEditAdapter constructor(
    var items: List<BannerResponse>, val listener: BannerEditActivity.Listener
) : RecyclerView.Adapter<BannerEditAdapter.ViewHolder>() {

    fun setDataList(items: List<BannerResponse>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemBinding =
            ItemBannerEditBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, items[position], listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(val binding: ItemBannerEditBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            position: Int,
            bannerResponse: BannerResponse,
            listener: BannerEditActivity.Listener
        ) {
            Glide.with(binding.root.context)
                .load(GlideUrl(bannerResponse.picUrl))
                .into(binding.bannerImageView)
            itemView.setOnClickListener {
                listener.onItemClick(bannerResponse.articleId)
            }
            binding.removeBannerBtn.setOnClickListener {
                listener.onRemoveBannerClick(bannerResponse.picId.toString())
            }
        }

    }
}