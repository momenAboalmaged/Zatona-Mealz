package com.zatona.zatona.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.zatona.zatona.R
import com.zatona.zatona.databinding.HomeRvItemsBinding
import com.zatona.zatona.models.Category

class HomeAdapter:RecyclerView.Adapter<HomeAdapter.ViewHolder>() {


    inner class ViewHolder(private val binding: HomeRvItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Category) {
            itemView.setOnClickListener {
                onItemClickListener?.invoke(item)
            }
            binding.apply {
                tvCategory.text = item.strCategory
                Glide.with(itemView)
                    .load(item.strCategoryThumb)
                    .transform(CenterCrop())
                    .error(R.drawable.ic_wifi_broken)
                    .placeholder(R.drawable.zatona)
                    .into(imgCategory)
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.idCategory == newItem.idCategory
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
    var onItemClickListener: ((Category) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HomeRvItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}