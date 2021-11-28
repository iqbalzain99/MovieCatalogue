package com.example.moviecatalogue.ui.home.movie

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

class MovieAdapter : PagingDataAdapter<FavoriteShowItems, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemsShowBinding = ItemsShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemsShowBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }



    class MovieViewHolder(private val binding: ItemsShowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: FavoriteShowItems) {
            with(binding) {
                tvItemTitle.text = movie.title
                tvItemYear.text = itemView.resources.getString(R.string.year_template, movie.year)
                tvItemCategory.text = movie.category
                tvItemRating.text = movie.rating

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_DATA, movie)
                    itemView.context.startActivity(intent)
                }
                Glide.with(itemView.context)
                    .load("https://www.themoviedb.org/t/p/w1280/${movie.imagePath}")
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                    .into(imgPoster)
            }
        }

    }
}