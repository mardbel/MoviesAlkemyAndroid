package com.example.moviesdatabase.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesdatabase.R
import com.example.moviesdatabase.data.Movie
import com.example.moviesdatabase.databinding.MovieListLayoutBinding
import kotlinx.coroutines.NonDisposableHandle.parent


class MovieListAdapter(private val context: Context, val onClick: (Int) -> Unit) :
    PagingDataAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffCallback) {

    lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_list_layout, parent, false)
        mContext = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is ViewHolder -> {
                getItem(position)?.let { movie ->
                    holder.bind(movie)
                    holder.bindView(movie)
                    holder.itemView.setOnClickListener {
                        onClick(movie.id)
                    }
                }
            }
        }
    }

    inner class ViewHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var binding = MovieListLayoutBinding.bind(itemView)

        private val movieTittle = binding.movieTittleTv
        var moviePoster = binding.posterImageView

        fun bind(movie: Movie) {
            movieTittle.text = movie.title
        }

        fun bindView(movie: Movie) {
            val IMAGE_URL_ROOT = "https://image.tmdb.org/t/p/w500"
            val posterFormat = IMAGE_URL_ROOT + movie.posterPath
            Glide.with(context)
                .load(posterFormat)
                .into(moviePoster)
        }
    }

    private object MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
    }
}