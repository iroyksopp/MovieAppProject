package com.example.movieapp.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieapp.model.network.response.FavouriteMovie
import com.example.movieapp.model.network.response.Movie

@Database(entities = [Movie::class, FavouriteMovie::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private var db: AppDatabase? = null
        private const val DB_NAME = "movie.db"
        private val LOCK = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                db = instance
                return instance
            }
        }
    }

    abstract fun movieDao(): MovieDao
}


