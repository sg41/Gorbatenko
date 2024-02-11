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
    private lateinit var currentMovie: Movie
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
                intent.putExtra("posterUrl", clickedMovie.movie.posterUrl)
                intent.putExtra("description", clickedMovie.description)
                intent.putExtra("genre", clickedMovie.movie.genres[0].genre)
                intent.putExtra("country", clickedMovie.movie.countries[0].country)
                // Start the activity
                itemView.context.startActivity(intent)
            }
        }
    }
    fun bind(movie: Movie) {
        currentMovie = movie
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
            Glide.with(holder.itemView)
                .load(movie.movie.posterUrlPreview)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.avatar_1)
                .into(holder.posterImageView)

            holder.titleTextView.text = movie.movie.nameRu
            holder.yearTextView.text = movie.movie.year
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


