package com.example.movieapp

import android.app.AlertDialog
import android.app.Application
import android.util.Log

class MyMovieApp : Application() {
    override fun onCreate() {
        super.onCreate()
        setupGlobalExceptionHandler()
    }

    private fun setupGlobalExceptionHandler() {
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { _, e ->
            handleUncaughtException(e)
            defaultHandler?.uncaughtException(Thread.currentThread(), e)
        }
    }
    private fun handleUncaughtException(throwable: Throwable) {
        showExceptionDialog(throwable)
        Log.e("UncaughtException", "Exception: ${throwable.message}", throwable)
    }

    private fun showExceptionDialog(throwable: Throwable) {
        // Create and show a dialog with the exception information
        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle("Unhandled Exception")
            .setMessage("An unexpected error occurred:\n${throwable.message}")
            .setPositiveButton("OK") { _, _ -> System.exit(0) } // Exit the application on OK
            .setCancelable(false)

        val dialog = dialogBuilder.create()
        dialog.show()
    }
}
