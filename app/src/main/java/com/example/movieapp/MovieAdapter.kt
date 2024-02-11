package com.example.movieapp

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import com.bumptech.glide.Glide

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val posterImageView: ImageView = itemView.findViewById(R.id.moviePosterImageView)
    val titleTextView: TextView = itemView.findViewById(R.id.movieTitleTextView)
    val yearTextView: TextView = itemView.findViewById(R.id.movieYearTextView)
}
    class MovieAdapter(private var movies: List<Movie>) : RecyclerView.Adapter<MovieViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
            return MovieViewHolder(view)
        }

        override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
            val movie = movies[position]

            // Загружаем изображение с использованием Glide (или другой библиотеки для загрузки изображений)
            Glide.with(holder.itemView)
                .load(movie.posterUrlPreview)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.avatar_1)
                .into(holder.posterImageView)

            holder.titleTextView.text = movie.nameRu
            holder.yearTextView.text = movie.year
        }
        override fun getItemCount(): Int {
            return movies.size
        }

        fun updateMovies(newMovies: List<Movie>) {
            movies = newMovies
            notifyDataSetChanged()
        }

    }


