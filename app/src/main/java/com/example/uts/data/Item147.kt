package com.example.uts.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "items_147",
    foreignKeys = [
        ForeignKey(
            entity = User147::class,
            parentColumns = ["id147"],
            childColumns = ["userId147"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["userId147"])]
)
data class Item147(
    @PrimaryKey(autoGenerate = true)
    val id147: Long = 0L,
    val userId147: Long,
    val nama147: String,
    val jumlah147: Int,
    val harga147: Long,
    val kategori147: String = "Elektronik",
    val kondisi147: String = "Baru",
    val nilaiKondisi147: Int = 100,
    val isTersedia147: Boolean = true
)

