package com.example.moviecatalogue.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.moviecatalogue.R
import com.example.moviecatalogue.data.source.local.entity.FavoriteShowItems
import com.example.moviecatalogue.data.source.remote.response.MovieDetailResponse
import com.example.moviecatalogue.data.source.remote.response.TvDetailResponse
import com.example.moviecatalogue.databinding.ActivityDetailBinding
import com.example.moviecatalogue.databinding.ContentDetailShowBinding
import com.example.moviecatalogue.viewmodel.ViewModelFactory

class DetailActivity : AppCompatActivity() , View.OnClickListener{
    private lateinit var detailShowBinding: ContentDetailShowBinding
    private var title: String? = "Detail Tv Show"

    private var dataItem : FavoriteShowItems? = null
    private lateinit var viewModel : DetailViewModel
    private lateinit var dbData : FavoriteShowItems
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityDetailTvShow = ActivityDetailBinding.inflate(layoutInflater)
        detailShowBinding = activityDetailTvShow.detailShow
        setContentView(activityDetailTvShow.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(this@DetailActivity, this.application)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        dataItem = intent.getParcelableExtra(EXTRA_DATA)

        dbData = dataItem as FavoriteShowItems

        detailShowBinding.progressBar.visibility = View.VISIBLE

        showData()


        detailShowBinding.btnFavorite.setOnClickListener {
            if (isFavorite) {
                Toast.makeText(this, "Kamu batal menyukai " + dataItem?.title, Toast.LENGTH_SHORT).show()
                viewModel.delete(dbData)
            } else {
                Toast.makeText(this, "Kamu menyukai " + dataItem?.title, Toast.LENGTH_SHORT).show()
                viewModel.insert(dataItem as FavoriteShowItems)
            }
        }
    }

    private fun showData() {
        if (dbData.type == 0) {

            viewModel.getMovies(dataItem?.id?.toInt() as Int).observe(this, { movieDetailResponse ->
                detailShowBinding.progressBar.visibility = View.GONE
                pupulateMovie(movieDetailResponse)
                title = movieDetailResponse.title
                supportActionBar?.title = title
            })

            viewModel.getSelectedFavoriteMovie(dataItem?.id!!).observe(this, { favorite ->
                if (favorite.isNotEmpty()) {
                    dbData = favorite[0]
                    isFavorite = true
                    val fullStar = AppCompatResources.getDrawable(this,R.drawable.ic_star_full)
                    detailShowBinding.btnFavorite.setCompoundDrawablesWithIntrinsicBounds(null,null,fullStar,null)
                } else {
                    isFavorite = false
                    val emptyStar = AppCompatResources.getDrawable( this,R.drawable.ic_star_empty)
                    detailShowBinding.btnFavorite.setCompoundDrawablesWithIntrinsicBounds(null,null,emptyStar,null)
                }
            })

        } else if (dbData.type == 1) {

            viewModel.getTvShow(dataItem?.id?.toInt() as Int).observe(this, { tvDetail ->
                detailShowBinding.progressBar.visibility = View.GONE
                pupulateTvShow(tvDetail)
                title = tvDetail.name
                supportActionBar?.title = title
            })

            viewModel.getSelectedFavoriteTv(dataItem?.id!!).observe(this, { favorite ->
                if (favorite.isNotEmpty()) {
                    dbData = favorite[0]
                    isFavorite = true
                    val fullStar = AppCompatResources.getDrawable(this, R.drawable.ic_star_full)
                    detailShowBinding.btnFavorite.setCompoundDrawablesWithIntrinsicBounds(null,null,fullStar,null)
                } else {
                    isFavorite = false
                    val emptyStar = AppCompatResources.getDrawable( this, R.drawable.ic_star_empty)
                    detailShowBinding.btnFavorite.setCompoundDrawablesWithIntrinsicBounds(null,null,emptyStar,null)
                }
            })

        }
    }

    private fun pupulateTvShow(tvShow: TvDetailResponse) {
        println(tvShow)
        detailShowBinding.apply {
            var category = ""
            for(item in tvShow.genres!!) {
                category += (item?.name as String)
                if (item != tvShow.genres[tvShow.genres.size -1] ){
                    category += ", "
                }
            }
            val detail1 = """
                ${tvShow.firstAirDate?.take(4)} • $category
            """.trimIndent()
            val detail2 = """
                ${tvShow.numberOfEpisodes} Episodes • ${tvShow.numberOfSeasons} Seasons
            """.trimIndent()
            tvDetail.text = detail1
            tvTitle.text = tvShow.name
            tvDetail2.text = detail2
            tvRating.text = tvShow.voteAverage.toString()
            tvOverview.text = tvShow.overview

            btnShare.setOnClickListener(this@DetailActivity)
            btnPlay.setOnClickListener(this@DetailActivity)
        }

        Glide.with(this)
            .load("https://www.themoviedb.org/t/p/w1280/${tvShow.posterPath}")
            .transform(RoundedCorners(20))
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error))
            .into(detailShowBinding.imagePoster)

    }

    private fun pupulateMovie(movie: MovieDetailResponse?) {
        println(movie)
        detailShowBinding.apply {
            var category = ""
            for(item in movie?.genres!!) {
                category += (item?.name as String)
                if (item != movie.genres[movie.genres.size -1] ){
                    category += ", "
                }
            }
            val detail1 = """
                ${movie.releaseDate?.take(4)} • $category
            """.trimIndent()
            val hour : String = if(movie.runtime!! /60 > 0) "${movie.runtime/60}h " else " "
            val minutes : String = if(movie.runtime % 60 > 0) "${movie.runtime % 60}m" else "0m"
            val duration : String = hour+minutes

            val detail2 = """
                $duration • Movie
                ${movie.releaseDate}
            """.trimIndent()
            tvDetail.text = detail1
            tvTitle.text = movie.title
            tvDetail2.text = detail2
            tvRating.text = movie.voteAverage.toString()
            tvOverview.text = movie.overview

            btnShare.setOnClickListener(this@DetailActivity)
            btnPlay.setOnClickListener(this@DetailActivity)
        }

        Glide.with(this)
            .load("https://www.themoviedb.org/t/p/w1280/${movie?.posterPath}")
            .transform(RoundedCorners(20))
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                .error(R.drawable.ic_error))
            .into(detailShowBinding.imagePoster)

    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_share) {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Jangan lupa untuk menonton $title di bioskop kesayangan anda"
            )
            startActivity(Intent.createChooser(shareIntent, "Tv Show : $title"))
        } else if (v.id == R.id.btn_play) {
            Toast.makeText(this, "Kamu memutar film : $title !!", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}