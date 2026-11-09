package com.example.uts.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ItemDao147 {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem147(item147: Item147): Long

    @Update
    suspend fun updateItem147(item147: Item147): Int

    @Delete
    suspend fun deleteItem147(item147: Item147): Int

    @Query("SELECT * FROM items_147 WHERE userId147 = :userId147 ORDER BY id147 DESC")
    suspend fun getItemsByUser147(userId147: Long): List<Item147>

    @Query("SELECT * FROM items_147 WHERE userId147 = :userId147 AND (nama147 LIKE '%' || :query147 || '%' OR kategori147 LIKE '%' || :query147 || '%') ORDER BY id147 DESC")
    suspend fun searchItemsByUser147(userId147: Long, query147: String): List<Item147>

    @Query("SELECT * FROM items_147 WHERE id147 = :itemId147 AND userId147 = :userId147 LIMIT 1")
    suspend fun getItemByIdAndUser147(itemId147: Long, userId147: Long): Item147?

    @Query("SELECT COALESCE(SUM(jumlah147 * harga147), 0) FROM items_147 WHERE userId147 = :userId147")
    suspend fun getTotalNilaiInventarisByUser147(userId147: Long): Long

    @Query("SELECT COALESCE(SUM(jumlah147), 0) FROM items_147 WHERE userId147 = :userId147")
    suspend fun getTotalUnitByUser147(userId147: Long): Int

    @Query("SELECT COUNT(*) FROM items_147 WHERE userId147 = :userId147")
    suspend fun getTotalJenisBarangByUser147(userId147: Long): Int
}

