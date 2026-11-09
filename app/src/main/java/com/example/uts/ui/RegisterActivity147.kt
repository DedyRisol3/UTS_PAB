package com.example.uts.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.uts.R
import com.example.uts.data.AppDatabase147
import com.example.uts.data.User147
import com.example.uts.databinding.ActivityRegister147Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity147 : AppCompatActivity() {

    private lateinit var binding147: ActivityRegister147Binding
    private lateinit var database147: AppDatabase147

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding147 = ActivityRegister147Binding.inflate(layoutInflater)
        setContentView(binding147.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding147.scrollRegister147) { view147, insets147 ->
            val systemBars147 = insets147.getInsets(WindowInsetsCompat.Type.systemBars())
            view147.setPadding(
                systemBars147.left,
                systemBars147.top,
                systemBars147.right,
                systemBars147.bottom
            )
            insets147
        }

        database147 = AppDatabase147.getDatabase147(this)

        setupSpinnerKota147()
        setupSeekBar147()

        binding147.btnSubmitRegister147.setOnClickListener {
            handleRegister147()
        }

        binding147.tvToLogin147.setOnClickListener {
            finish()
        }
    }

    private fun setupSpinnerKota147() {
        val adapterKota147 = ArrayAdapter.createFromResource(
            this,
            R.array.kota_array_147,
            android.R.layout.simple_spinner_item
        )
        adapterKota147.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding147.spinnerKota147.adapter = adapterKota147
    }

    private fun setupSeekBar147() {
        binding147.tvSeekBarValue147.text = "${binding147.seekBar147.progress} / 100"
        binding147.seekBar147.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar147: SeekBar?, progress147: Int, fromUser147: Boolean) {
                binding147.tvSeekBarValue147.text = "$progress147 / 100"
            }

            override fun onStartTrackingTouch(seekBar147: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar147: SeekBar?) {}
        })
    }

    private fun handleRegister147() {
        val username147 = binding147.etUsername147.text.toString().trim()
        val password147 = binding147.etPassword147.text.toString().trim()
        val namaLengkap147 = binding147.etNamaLengkap147.text.toString().trim()

        val jenisKelamin147 = if (binding147.rbLaki147.isChecked) "Laki-Laki" else "Perempuan"
        val kotaAsal147 = binding147.spinnerKota147.selectedItem?.toString() ?: "Jakarta"
        val skorAwal147 = binding147.seekBar147.progress
        val isAgree147 = binding147.switchAgreement147.isChecked

        // Validasi Form
        if (username147.isEmpty()) {
            binding147.etUsername147.error = "Username wajib diisi"
            binding147.etUsername147.requestFocus()
            return
        }

        if (password147.isEmpty()) {
            binding147.etPassword147.error = "Password wajib diisi"
            binding147.etPassword147.requestFocus()
            return
        }

        if (password147.length < 6) {
            binding147.etPassword147.error = "Password minimal 6 karakter"
            binding147.etPassword147.requestFocus()
            return
        }

        if (namaLengkap147.isEmpty()) {
            binding147.etNamaLengkap147.error = "Nama lengkap wajib diisi"
            binding147.etNamaLengkap147.requestFocus()
            return
        }

        if (!isAgree147) {
            Toast.makeText(
                this,
                "Anda harus menyetujui syarat & ketentuan untuk mendaftar!",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        lifecycleScope.launch {
            // Validasi Username Unik
            val existingUser147 = withContext(Dispatchers.IO) {
                database147.userDao147().getUserByUsername147(username147)
            }

            if (existingUser147 != null) {
                binding147.etUsername147.error = "Username sudah terdaftar! Gunakan username lain."
                binding147.etUsername147.requestFocus()
                Toast.makeText(
                    this@RegisterActivity147,
                    "Username sudah digunakan, silakan pilih username lain",
                    Toast.LENGTH_SHORT
                ).show()
                return@launch
            }

            // Simpan User baru ke Room
            val newUser147 = User147(
                username147 = username147,
                password147 = password147,
                namaLengkap147 = namaLengkap147,
                jenisKelamin147 = jenisKelamin147,
                kotaAsal147 = kotaAsal147,
                skorAwal147 = skorAwal147
            )

            withContext(Dispatchers.IO) {
                database147.userDao147().insertUser147(newUser147)
            }

            Toast.makeText(
                this@RegisterActivity147,
                "Pendaftaran berhasil! Silakan login dengan akun Anda.",
                Toast.LENGTH_LONG
            ).show()

            finish()
        }
    }
}

