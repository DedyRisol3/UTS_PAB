package com.example.uts.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uts.data.AppDatabase147
import com.example.uts.data.Item147
import com.example.uts.data.SessionManager147
import com.example.uts.databinding.ActivityItemList147Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemListActivity147 : AppCompatActivity() {

    private lateinit var binding147: ActivityItemList147Binding
    private lateinit var sessionManager147: SessionManager147
    private lateinit var database147: AppDatabase147
    private lateinit var itemAdapter147: ItemAdapter147

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding147 = ActivityItemList147Binding.inflate(layoutInflater)
        setContentView(binding147.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding147.coordinatorItemList147) { view147, insets147 ->
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

        setupRecyclerView147()
        setupListeners147()
    }

    override fun onResume() {
        super.onResume()
        val currentSearchQuery147 = binding147.etSearch147.text.toString().trim()
        loadItemsData147(currentSearchQuery147)
    }

    private fun setupRecyclerView147() {
        itemAdapter147 = ItemAdapter147(
            itemList147 = emptyList(),
            onEditClick147 = { selectedItem147 ->
                val intentEdit147 = Intent(this, FormItemActivity147::class.java).apply {
                    putExtra(FormItemActivity147.EXTRA_ITEM_ID_147, selectedItem147.id147)
                }
                startActivity(intentEdit147)
            },
            onDeleteClick147 = { selectedItem147 ->
                confirmDeleteItem147(selectedItem147)
            }
        )

        binding147.rvItems147.layoutManager = LinearLayoutManager(this)
        binding147.rvItems147.adapter = itemAdapter147
    }

    private fun setupListeners147() {
        binding147.btnBackFromList147.setOnClickListener {
            finish()
        }

        binding147.fabAddItem147.setOnClickListener {
            val intentAdd147 = Intent(this, FormItemActivity147::class.java)
            startActivity(intentAdd147)
        }

        // Live Search Bar Listener (Bonus 10%)
        binding147.etSearch147.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loadItemsData147(s?.toString()?.trim() ?: "")
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun loadItemsData147(query147: String) {
        val currentUserId147 = sessionManager147.getUserId147()
        if (currentUserId147 <= 0L) return

        lifecycleScope.launch {
            val itemsResult147 = withContext(Dispatchers.IO) {
                if (query147.isEmpty()) {
                    database147.itemDao147().getItemsByUser147(currentUserId147)
                } else {
                    database147.itemDao147().searchItemsByUser147(currentUserId147, query147)
                }
            }

            itemAdapter147.updateData147(itemsResult147)
            binding147.tvItemCountSummary147.text = "Menampilkan ${itemsResult147.size} barang"

            if (itemsResult147.isEmpty()) {
                binding147.layoutEmptyState147.visibility = View.VISIBLE
                binding147.rvItems147.visibility = View.GONE
            } else {
                binding147.layoutEmptyState147.visibility = View.GONE
                binding147.rvItems147.visibility = View.VISIBLE
            }
        }
    }

    private fun confirmDeleteItem147(itemToDelete147: Item147) {
        AlertDialog.Builder(this)
            .setTitle("Hapus Barang")
            .setMessage("Yakin ingin menghapus barang '${itemToDelete147.nama147}' dari inventaris?")
            .setPositiveButton("Hapus") { _, _ ->
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        database147.itemDao147().deleteItem147(itemToDelete147)
                    }
                    Toast.makeText(
                        this@ItemListActivity147,
                        "Barang '${itemToDelete147.nama147}' berhasil dihapus",
                        Toast.LENGTH_SHORT
                    ).show()
                    loadItemsData147(binding147.etSearch147.text.toString().trim())
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}

