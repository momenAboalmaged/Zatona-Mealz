package com.zatona.zatona.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.zatona.zatona.R
import com.zatona.zatona.databinding.MealsRvItemBinding
import com.zatona.zatona.models.Meal
import com.zatona.zatona.ui.home.HomeViewModel

class FavouriteAdapter(private val viewModel: HomeViewModel) :
    RecyclerView.Adapter<FavouriteAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: MealsRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Meal) {
            itemView.setOnClickListener { onItemClickListener?.invoke(item) }
            binding.apply {
                tvCategory.text = item.strMeal
                iconFavourite.isChecked = true
                Glide.with(itemView)
                    .load(item.strMealThumb)
                    .transform(CenterCrop())
                    .error(R.drawable.ic_wifi_broken)
                    .placeholder(R.drawable.zatona)
                    .into(imgCategory)
            }
            binding.iconFavourite.setOnClickListener {
                if (!binding.iconFavourite.isChecked) {
                    viewModel.removeFavourite(item)
                }
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            MealsRvItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    var onItemClickListener: ((Meal) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}