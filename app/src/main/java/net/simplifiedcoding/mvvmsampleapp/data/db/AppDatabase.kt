package net.simplifiedcoding.mvvmsampleapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.simplifiedcoding.mvvmsampleapp.data.db.entities.Quote

@Database(
    entities = [Quote::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getQuoteDao(): QuoteDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "like_database"
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}