package com.example.uts.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.uts.data.AppDatabase147
import com.example.uts.data.SessionManager147
import com.example.uts.databinding.ActivityDashboard147Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.NumberFormat
import java.util.Locale

class DashboardActivity147 : AppCompatActivity() {

    private lateinit var binding147: ActivityDashboard147Binding
    private lateinit var sessionManager147: SessionManager147
    private lateinit var database147: AppDatabase147
    private val rupiahFormat147: NumberFormat = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding147 = ActivityDashboard147Binding.inflate(layoutInflater)
        setContentView(binding147.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding147.scrollDashboard147) { view147, insets147 ->
            val systemBars147 = insets147.getInsets(WindowInsetsCompat.Type.systemBars())
            view147.setPadding(
                systemBars147.left,
                systemBars147.top,
                systemBars147.right,
                systemBars147.bottom
            )
            insets147
        }

        sessionManager147 = SessionManager147(this)
        database147 = AppDatabase147.getDatabase147(this)

        if (!sessionManager147.isLoggedIn147()) {
            val intentLogin147 = Intent(this, LoginActivity147::class.java)
            startActivity(intentLogin147)
            finish()
            return
        }

        val userName147 = sessionManager147.getNamaLengkap147() ?: "User 147"
        binding147.tvUserDisplayName147.text = userName147

        binding147.cardNavigateList147.setOnClickListener {
            val intentList147 = Intent(this, ItemListActivity147::class.java)
            startActivity(intentList147)
        }

        binding147.cardNavigateAdd147.setOnClickListener {
            val intentAdd147 = Intent(this, FormItemActivity147::class.java)
            startActivity(intentAdd147)
        }

        binding147.btnLogout147.setOnClickListener {
            showLogoutConfirmation147()
        }
    }

    override fun onResume() {
        super.onResume()
        loadRealTimeDashboardStats147()
    }

    private fun loadRealTimeDashboardStats147() {
        val currentUserId147 = sessionManager147.getUserId147()
        if (currentUserId147 <= 0L) return

        lifecycleScope.launch {
            val totalNilai147 = withContext(Dispatchers.IO) {
                database147.itemDao147().getTotalNilaiInventarisByUser147(currentUserId147)
            }
            val totalUnit147 = withContext(Dispatchers.IO) {
                database147.itemDao147().getTotalUnitByUser147(currentUserId147)
            }
            val totalJenis147 = withContext(Dispatchers.IO) {
                database147.itemDao147().getTotalJenisBarangByUser147(currentUserId147)
            }

            binding147.tvTotalNilaiRealTime147.text = rupiahFormat147.format(totalNilai147)
            binding147.tvTotalUnitBarang147.text = "$totalUnit147 Unit"
            binding147.tvTotalJenisBarang147.text = "$totalJenis147 Jenis"
        }
    }

    private fun showLogoutConfirmation147() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Logout")
            .setMessage("Apakah Anda yakin ingin keluar dari akun?")
            .setPositiveButton("Logout") { _, _ ->
                sessionManager147.logout147()
                val intentLogin147 = Intent(this, LoginActivity147::class.java)
                intentLogin147.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intentLogin147)
                finish()
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}
