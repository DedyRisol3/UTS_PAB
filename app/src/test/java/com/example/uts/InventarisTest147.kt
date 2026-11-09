package com.example.uts

import com.example.uts.data.Item147
import com.example.uts.data.User147
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class InventarisTest147 {

    @Test
    fun testUserCreationAndValidation147() {
        val user147 = User147(
            id147 = 1L,
            username147 = "admin147",
            password147 = "rahasia147",
            namaLengkap147 = "Mahasiswa 147",
            jenisKelamin147 = "Laki-Laki",
            kotaAsal147 = "Jakarta",
            skorAwal147 = 85
        )

        assertEquals("admin147", user147.username147)
        assertTrue(user147.password147.length >= 6)
        assertEquals("Laki-Laki", user147.jenisKelamin147)
        assertEquals(85, user147.skorAwal147)
    }

    @Test
    fun testItemTotalCalculation147() {
        val item147 = Item147(
            id147 = 10L,
            userId147 = 1L,
            nama147 = "Laptop Gaming 147",
            jumlah147 = 4,
            harga147 = 15_000_000L,
            kategori147 = "Elektronik",
            kondisi147 = "Baru",
            nilaiKondisi147 = 100,
            isTersedia147 = true
        )

        val totalNilai147 = item147.jumlah147.toLong() * item147.harga147
        assertEquals(60_000_000L, totalNilai147)
        assertTrue(item147.isTersedia147)
    }

    @Test
    fun testPasswordValidationLogic147() {
        fun isValidPassword147(password147: String): Boolean {
            return password147.length >= 6
        }

        assertFalse(isValidPassword147("12345"))
        assertTrue(isValidPassword147("123456"))
        assertTrue(isValidPassword147("password147"))
    }
}

