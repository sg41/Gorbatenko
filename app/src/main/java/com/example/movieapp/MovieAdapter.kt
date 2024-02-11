package com.example.movieapp

import android.content.Intent
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
    lateinit var currentMovie: Movie
    init {
        // Set an onClickListener for the whole item view
        itemView.setOnClickListener {
            // Check if the adapter position is valid
            if (adapterPosition != RecyclerView.NO_POSITION) {
                // Get the clicked movie from the list
                val clickedMovie = currentMovie

                // Create an Intent to start the MovieDetailsActivity
                val intent = Intent(itemView.context, MovieDetailActivity::class.java)

                // Pass the movie details to the Intent
                intent.putExtra("posterUrl", clickedMovie.posterUrl)
                intent.putExtra("description", clickedMovie.nameEn)
                intent.putExtra("genre", clickedMovie.genres[0].genre)
                intent.putExtra("country", clickedMovie.countries[0].country)
                // Start the activity
                itemView.context.startActivity(intent)
            }
        }
    }
    fun bind(movie: Movie) {
        // Update the views in the view holder with the data from the movie
        // For example, set text to TextView, load image to ImageView, etc.
//        itemView.findViewById<TextView>(R.id.movieTitleTextView).text = movie.nameRu
        // Update other views...
        currentMovie = movie
        // You can also set click listeners or perform other actions here if needed.
    }
}
    class MovieAdapter(private var movies: List<Movie>) : RecyclerView.Adapter<MovieViewHolder>() {
        lateinit var movieVieHolder: MovieViewHolder
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
            holder.bind(movie)
        }
        override fun getItemCount(): Int {
            return movies.size
        }

        fun updateMovies(newMovies: List<Movie>) {
            movies = newMovies
            notifyDataSetChanged()
        }

    }


