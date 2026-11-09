package com.example.uts.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.uts.data.AppDatabase147
import com.example.uts.data.SessionManager147
import com.example.uts.databinding.ActivityLogin147Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity147 : AppCompatActivity() {

    private lateinit var binding147: ActivityLogin147Binding
    private lateinit var sessionManager147: SessionManager147
    private lateinit var database147: AppDatabase147

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding147 = ActivityLogin147Binding.inflate(layoutInflater)
        setContentView(binding147.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding147.scrollLogin147) { view147, insets147 ->
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

        binding147.btnLogin147.setOnClickListener {
            handleLogin147()
        }

        binding147.tvToRegister147.setOnClickListener {
            val intentRegister147 = Intent(this, RegisterActivity147::class.java)
            startActivity(intentRegister147)
        }
    }

    private fun handleLogin147() {
        val username147 = binding147.etUsername147.text.toString().trim()
        val password147 = binding147.etPassword147.text.toString().trim()

        if (username147.isEmpty()) {
            binding147.etUsername147.error = "Username tidak boleh kosong"
            binding147.etUsername147.requestFocus()
            return
        }

        if (password147.isEmpty()) {
            binding147.etPassword147.error = "Password tidak boleh kosong"
            binding147.etPassword147.requestFocus()
            return
        }

        if (password147.length < 6) {
            binding147.etPassword147.error = "Password minimal 6 karakter"
            binding147.etPassword147.requestFocus()
            return
        }

        lifecycleScope.launch {
            val userFound147 = withContext(Dispatchers.IO) {
                database147.userDao147().loginUser147(username147, password147)
            }

            if (userFound147 != null) {
                sessionManager147.saveLoginSession147(
                    userFound147.id147,
                    userFound147.username147,
                    userFound147.namaLengkap147.ifEmpty { userFound147.username147 }
                )
                Toast.makeText(
                    this@LoginActivity147,
                    "Login berhasil! Selamat datang, ${userFound147.namaLengkap147.ifEmpty { userFound147.username147 }}",
                    Toast.LENGTH_SHORT
                ).show()

                val intentDashboard147 = Intent(this@LoginActivity147, DashboardActivity147::class.java)
                startActivity(intentDashboard147)
                finish()
            } else {
                Toast.makeText(
                    this@LoginActivity147,
                    "Username atau password salah!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}

