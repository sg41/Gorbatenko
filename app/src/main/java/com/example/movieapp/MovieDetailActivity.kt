package com.example.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.bumptech.glide.Glide

class MovieDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        // Получение данных из Intent
        val moviePosterUrl = intent.getStringExtra("posterUrl")
        val movieDescription = intent.getStringExtra("description")
        val movieGenre = intent.getStringExtra("genre")
        val movieCountry = intent.getStringExtra("country")

        // Загрузка данных на экран
        // Здесь вы можете использовать библиотеку Glide для загрузки изображений
        Glide.with(this).load(moviePosterUrl).into(findViewById(R.id.imageViewPoster))

        findViewById<TextView>(R.id.textViewDescription).text = movieDescription
        findViewById<TextView>(R.id.textViewGenre).text = movieGenre
        findViewById<TextView>(R.id.textViewCountry).text = movieCountry

    }
}