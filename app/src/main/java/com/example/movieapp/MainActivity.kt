package com.example.movieapp

import android.app.AlertDialog
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieViewModel: MovieViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_main)

        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        // Check if movies are already stored in ViewModel
        if (movieViewModel.movies == null) {
            // If not, fetch movies and set them in ViewModel
          // Initialize RecyclerView and MovieAdapter
        recyclerView = findViewById(R.id.MovieList)
        movieAdapter = MovieAdapter(getDummyMovies(), this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = movieAdapter

            // Fetch popular movies and update RecyclerView
            try {
                getPopularMovies()
            } catch (e: Exception) {
                // Handle other exceptions (e.g., JSON parsing, network issues, etc.)
                val errorMessage = "Unexpected error: ${e.message}"
                showError(errorMessage)
            }
        } else {
            // If movies are already in ViewModel, update RecyclerView
            recyclerView = findViewById(R.id.MovieList)
            movieAdapter = MovieAdapter(movieViewModel.movies!!, this)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = movieAdapter
        }
    }
    private fun getPopularMovies() {
        if(movieAdapter.moviesLoaded())
            return
        val call = RetrofitClient.kinopoiskApiService.getPopularMovies("TOP_POPULAR_MOVIES")
        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movieResponse = response.body()
                    movieResponse?.let {
                        val movies = it.items.map { movieNoDescription ->
                            Movie(
                                movie = movieNoDescription,
                                description = "Description is to be loaded, please come back in 20 seconds..."
                                )
                        }
                        movieViewModel.movies = movies
                        movieAdapter.updateMovies(movies)
                        fetchMovieDetailsForList(movies) { updatedMovies ->
                            movieViewModel.movies = updatedMovies
                            movieAdapter.updateMovies(updatedMovies)
                        }
                    } ?: showError("Movie response is null")
                } else {
                    // Handle unsuccessful response
                    val errorMessage = "Error: ${response.code()} ${response.message()}"
                    showError(errorMessage)
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                // Handle network errors
                val errorMessage = "Network error: ${t.message}"
                showError(errorMessage)
            }
        })

    }

    private fun fetchMovieDetailsForList(movies: List<Movie>, onDetailsFetched: (List<Movie>) -> Unit) {
        val iterator = movies.iterator()
        val handler = Handler(Looper.getMainLooper())

        fun fetchNextMovieDetails() {
            if (iterator.hasNext()) {
                var movie = iterator.next()
                val movieId = movie.movie.kinopoiskId

                getMovieDetails(movieId) { movieDetails ->
                    // Update your Movie object with the details if needed
                    movie.description = movieDetails?.description ?: "Unfortunately, this movie has no description"

                    // Recursively fetch details for the next movie after a delay
                    handler.postDelayed({
                        fetchNextMovieDetails()
                    }, 1000) // Delay of 1000 milliseconds (1 second)
                }
            } else {
                // All details fetched, invoke the callback with the updated list
                onDetailsFetched(movies)
            }
        }

        // Start fetching details for the first movie
        fetchNextMovieDetails()
    }

    private fun getMovieDetails(movieId: Int, onDetailsFetched: (MovieDetails?) -> Unit) {
        val call = RetrofitClient.kinopoiskApiService.getMovieDetails(movieId)
        call.enqueue(object : Callback<MovieDetails> {
            override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
                if (response.isSuccessful) {
                    val movieDetails = response.body()
                    onDetailsFetched(movieDetails)
                } else {
                    // Handle unsuccessful response
                    onDetailsFetched(null)
                }
            }

            override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                // Handle network errors
                onDetailsFetched(null)
            }
        })
    }

    private fun showError(message: String) {
//        showToast(message)
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK") { _, _ ->
            }
            .setCancelable(false)
            .create()

        alertDialog.show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
fun getDummyMovies(): List<Movie> {

    return List(10) { index ->
        Movie(
            MovieNoDescription(
            kinopoiskId = index,
            nameRu = "Movie $index",
            posterUrl = "",
            year = index.toString(),
            nameEn = "",
            nameOriginal = "",
            countries = listOf(Country("")),
            genres =  listOf(Genre("")),
            ratingKinopoisk= 8.8,
            ratingImbd = 8.9,
            type ="String",
            posterUrlPreview = "localhost"
            ),
            description = ""
        )
    }
}