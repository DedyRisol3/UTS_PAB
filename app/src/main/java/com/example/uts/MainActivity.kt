package com.example.uts

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uts.data.SessionManager147
import com.example.uts.databinding.ActivityMainBinding
import com.example.uts.ui.DashboardActivity147
import com.example.uts.ui.LoginActivity147

class MainActivity : AppCompatActivity() {

    private lateinit var binding147: ActivityMainBinding
    private lateinit var sessionManager147: SessionManager147

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding147 = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding147.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding147.mainLayout147) { view147, insets147 ->
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

        Handler(Looper.getMainLooper()).postDelayed({
            if (sessionManager147.isLoggedIn147()) {
                val intentDashboard147 = Intent(this, DashboardActivity147::class.java)
                startActivity(intentDashboard147)
            } else {
                val intentLogin147 = Intent(this, LoginActivity147::class.java)
                startActivity(intentLogin147)
            }
            finish()
        }, 1200)
    }
}
