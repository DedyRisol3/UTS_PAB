package com.example.uts.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [User147::class, Item147::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase147 : RoomDatabase() {

    abstract fun userDao147(): UserDao147
    abstract fun itemDao147(): ItemDao147

    companion object {
        @Volatile
        private var INSTANCE_147: AppDatabase147? = null

        fun getDatabase147(context147: Context): AppDatabase147 {
            return INSTANCE_147 ?: synchronized(this) {
                val instance147 = Room.databaseBuilder(
                    context147.applicationContext,
                    AppDatabase147::class.java,
                    "inventaris_db_147"
                ).build()
                INSTANCE_147 = instance147
                instance147
            }
        }
    }
}

