package com.example.beatoreat.widget

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.beatoreat.databinding.MovieItemBinding
import com.example.core.data.models.Movie
import com.example.core.data.models.movies.MutableMovie
import com.squareup.picasso.Picasso

class MovieAdapter(val context: Context): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    var movies: MutableList<MutableMovie> = mutableListOf()

    fun addMovie(movies:List<MutableMovie>, isClear: Boolean = false) {
        if (isClear) {
            this.movies.clear()
        }
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindMovie(movies[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = MovieItemBinding.inflate(inflater, parent, false)
        return MovieViewHolder(view)
    }

    class MovieViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {

        var data: MutableMovie? = null

        init{
            binding.imFavorite.setOnClickListener {
                val newValue = !(data?.isFavorite ?: true)
                data?.isFavorite = newValue
                binding.imFavorite.isActivated = newValue
            }
        }

        fun bindMovie(data: MutableMovie) {
            this.data = data
            val imageBase = "https://image.tmdb.org/t/p/w300/"
            val imgUrl = "$imageBase${data.poster_path}"
            Picasso.get().load(imgUrl).into(binding.imPoster)
            binding.tvTitle.text = data.title
            binding.imFavorite.isActivated = data.isFavorite
        }
    }

}