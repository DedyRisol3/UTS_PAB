package com.example.uts.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_147")
data class User147(
    @PrimaryKey(autoGenerate = true)
    val id147: Long = 0L,
    val username147: String,
    val password147: String,
    val namaLengkap147: String = "",
    val jenisKelamin147: String = "",
    val kotaAsal147: String = "",
    val skorAwal147: Int = 0
)

