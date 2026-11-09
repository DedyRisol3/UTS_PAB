package com.example.uts.ui

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.uts.R
import com.example.uts.data.AppDatabase147
import com.example.uts.data.Item147
import com.example.uts.data.SessionManager147
import com.example.uts.databinding.ActivityFormItem147Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FormItemActivity147 : AppCompatActivity() {

    companion object {
        const val EXTRA_ITEM_ID_147 = "extra_item_id_147"
    }

    private lateinit var binding147: ActivityFormItem147Binding
    private lateinit var sessionManager147: SessionManager147
    private lateinit var database147: AppDatabase147

    private var currentItemId147: Long = -1L
    private var isEditMode147: Boolean = false
    private var existingItem147: Item147? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding147 = ActivityFormItem147Binding.inflate(layoutInflater)
        setContentView(binding147.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding147.scrollFormItem147) { view147, insets147 ->
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

        currentItemId147 = intent.getLongExtra(EXTRA_ITEM_ID_147, -1L)
        isEditMode147 = (currentItemId147 > 0L)

        setupSpinnerKategori147()
        setupSeekBarKondisi147()
        setupUIForMode147()

        binding147.btnBackFromForm147.setOnClickListener {
            finish()
        }

        binding147.btnSimpanBarang147.setOnClickListener {
            handleSaveBarang147()
        }

        binding147.btnHapusBarang147.setOnClickListener {
            confirmDeleteFromForm147()
        }
    }

    private fun setupSpinnerKategori147() {
        val adapterKategori147 = ArrayAdapter.createFromResource(
            this,
            R.array.kategori_barang_array_147,
            android.R.layout.simple_spinner_item
        )
        adapterKategori147.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding147.spinnerKategoriBarang147.adapter = adapterKategori147
    }

    private fun setupSeekBarKondisi147() {
        binding147.tvNilaiKondisi147.text = "${binding147.seekBarKondisi147.progress}%"
        binding147.seekBarKondisi147.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar147: SeekBar?, progress147: Int, fromUser147: Boolean) {
                binding147.tvNilaiKondisi147.text = "$progress147%"
            }

            override fun onStartTrackingTouch(seekBar147: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar147: SeekBar?) {}
        })
    }

    private fun setupUIForMode147() {
        if (isEditMode147) {
            binding147.tvFormItemModeTitle147.text = "EDIT BARANG INVENTARIS"
            binding147.btnSimpanBarang147.text = "PERBARUI BARANG"
            binding147.btnHapusBarang147.visibility = View.VISIBLE
            loadItemDataToEdit147()
        } else {
            binding147.tvFormItemModeTitle147.text = "TAMBAH BARANG INVENTARIS"
            binding147.btnSimpanBarang147.text = "SIMPAN BARANG"
            binding147.btnHapusBarang147.visibility = View.GONE
        }
    }

    private fun loadItemDataToEdit147() {
        val currentUserId147 = sessionManager147.getUserId147()
        lifecycleScope.launch {
            val item147 = withContext(Dispatchers.IO) {
                database147.itemDao147().getItemByIdAndUser147(currentItemId147, currentUserId147)
            }

            if (item147 != null) {
                existingItem147 = item147
                binding147.etNamaBarang147.setText(item147.nama147)
                binding147.etJumlahBarang147.setText(item147.jumlah147.toString())
                binding147.etHargaBarang147.setText(item147.harga147.toString())

                // Set spinner kategori
                val kategoriArray147 = resources.getStringArray(R.array.kategori_barang_array_147)
                val catIndex147 = kategoriArray147.indexOf(item147.kategori147)
                if (catIndex147 >= 0) {
                    binding147.spinnerKategoriBarang147.setSelection(catIndex147)
                }

                // Set RadioButton kondisi
                when (item147.kondisi147) {
                    "Baru" -> binding147.rbKondisiBaru147.isChecked = true
                    "Bekas (Layak)" -> binding147.rbKondisiBekas147.isChecked = true
                    else -> binding147.rbKondisiRusak147.isChecked = true
                }

                // Set SeekBar & Switch
                binding147.seekBarKondisi147.progress = item147.nilaiKondisi147
                binding147.tvNilaiKondisi147.text = "${item147.nilaiKondisi147}%"
                binding147.switchTersedia147.isChecked = item147.isTersedia147
            } else {
                Toast.makeText(this@FormItemActivity147, "Data barang tidak ditemukan", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun handleSaveBarang147() {
        val namaBarang147 = binding147.etNamaBarang147.text.toString().trim()
        val strJumlah147 = binding147.etJumlahBarang147.text.toString().trim()
        val strHarga147 = binding147.etHargaBarang147.text.toString().trim()

        if (namaBarang147.isEmpty()) {
            binding147.etNamaBarang147.error = "Nama barang tidak boleh kosong"
            binding147.etNamaBarang147.requestFocus()
            return
        }

        if (strJumlah147.isEmpty()) {
            binding147.etJumlahBarang147.error = "Jumlah stok tidak boleh kosong"
            binding147.etJumlahBarang147.requestFocus()
            return
        }

        val jumlah147 = strJumlah147.toIntOrNull()
        if (jumlah147 == null || jumlah147 < 0) {
            binding147.etJumlahBarang147.error = "Jumlah harus berupa angka non-negatif"
            binding147.etJumlahBarang147.requestFocus()
            return
        }

        if (strHarga147.isEmpty()) {
            binding147.etHargaBarang147.error = "Harga satuan tidak boleh kosong"
            binding147.etHargaBarang147.requestFocus()
            return
        }

        val harga147 = strHarga147.toLongOrNull()
        if (harga147 == null || harga147 < 0L) {
            binding147.etHargaBarang147.error = "Harga harus berupa angka non-negatif"
            binding147.etHargaBarang147.requestFocus()
            return
        }

        val kategoriBarang147 = binding147.spinnerKategoriBarang147.selectedItem?.toString() ?: "Elektronik"
        val kondisiBarang147 = when {
            binding147.rbKondisiBaru147.isChecked -> "Baru"
            binding147.rbKondisiBekas147.isChecked -> "Bekas (Layak)"
            else -> "Perlu Servis"
        }
        val nilaiKondisi147 = binding147.seekBarKondisi147.progress
        val isTersedia147 = binding147.switchTersedia147.isChecked
        val currentUserId147 = sessionManager147.getUserId147()

        lifecycleScope.launch {
            if (isEditMode147) {
                val updatedItem147 = Item147(
                    id147 = currentItemId147,
                    userId147 = currentUserId147,
                    nama147 = namaBarang147,
                    jumlah147 = jumlah147,
                    harga147 = harga147,
                    kategori147 = kategoriBarang147,
                    kondisi147 = kondisiBarang147,
                    nilaiKondisi147 = nilaiKondisi147,
                    isTersedia147 = isTersedia147
                )
                withContext(Dispatchers.IO) {
                    database147.itemDao147().updateItem147(updatedItem147)
                }
                Toast.makeText(this@FormItemActivity147, "Barang berhasil diperbarui!", Toast.LENGTH_SHORT).show()
            } else {
                val newItem147 = Item147(
                    userId147 = currentUserId147,
                    nama147 = namaBarang147,
                    jumlah147 = jumlah147,
                    harga147 = harga147,
                    kategori147 = kategoriBarang147,
                    kondisi147 = kondisiBarang147,
                    nilaiKondisi147 = nilaiKondisi147,
                    isTersedia147 = isTersedia147
                )
                withContext(Dispatchers.IO) {
                    database147.itemDao147().insertItem147(newItem147)
                }
                Toast.makeText(this@FormItemActivity147, "Barang baru berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
    }

    private fun confirmDeleteFromForm147() {
        val itemToDelete147 = existingItem147 ?: return
        AlertDialog.Builder(this)
            .setTitle("Hapus Barang")
            .setMessage("Yakin ingin menghapus barang '${itemToDelete147.nama147}'?")
            .setPositiveButton("Hapus") { _, _ ->
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        database147.itemDao147().deleteItem147(itemToDelete147)
                    }
                    Toast.makeText(
                        this@FormItemActivity147,
                        "Barang berhasil dihapus!",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}

