package com.example.uts.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao147 {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser147(user147: User147): Long

    @Query("SELECT * FROM users_147 WHERE username147 = :username147 LIMIT 1")
    suspend fun getUserByUsername147(username147: String): User147?

    @Query("SELECT * FROM users_147 WHERE username147 = :username147 AND password147 = :password147 LIMIT 1")
    suspend fun loginUser147(username147: String, password147: String): User147?

    @Query("SELECT * FROM users_147 WHERE id147 = :userId147 LIMIT 1")
    suspend fun getUserById147(userId147: Long): User147?
}

