package com.example.moviecatalogue.ui.home.tvshow

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviecatalogue.R
import com.example.moviecatalogue.data.source.local.entity.FavoriteShowItems
import com.example.moviecatalogue.databinding.ItemsShowBinding
import com.example.moviecatalogue.ui.detail.DetailActivity

class TvShowAdapter : PagingDataAdapter<FavoriteShowItems, TvShowAdapter.TvShowViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteShowItems>() {
            override fun areItemsTheSame(
                oldItem: FavoriteShowItems,
                newItem: FavoriteShowItems
            ): Boolean {
                return oldItem.id == newItem.id && oldItem.type == newItem.type
            }

            override fun areContentsTheSame(
                oldItem: FavoriteShowItems,
                newItem: FavoriteShowItems
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val itemsShowBinding = ItemsShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(itemsShowBinding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShow = getItem(position)
        if (tvShow != null) {
            holder.bind(tvShow)
        }
    }


    class TvShowViewHolder(private val binding: ItemsShowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: FavoriteShowItems) {
            with(binding) {
                tvItemTitle.text = tvShow.title
                tvItemYear.text = itemView.resources.getString(R.string.year_template, tvShow.year)
                tvItemCategory.text = tvShow.category
                tvItemRating.text = tvShow.rating

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_DATA, tvShow)
                    itemView.context.startActivity(intent)
                }

                Glide.with(itemView.context)
                    .load("https://www.themoviedb.org/t/p/w1280/${tvShow.imagePath}")
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                    .into(imgPoster)
            }
        }

    }
}