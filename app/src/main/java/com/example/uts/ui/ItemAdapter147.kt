package com.example.uts.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uts.data.Item147
import com.example.uts.databinding.ItemBarang147Binding
import java.text.NumberFormat
import java.util.Locale

class ItemAdapter147(
    private var itemList147: List<Item147>,
    private val onEditClick147: (Item147) -> Unit,
    private val onDeleteClick147: (Item147) -> Unit
) : RecyclerView.Adapter<ItemAdapter147.ItemViewHolder147>() {

    private val rupiahFormat147: NumberFormat = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID"))

    inner class ItemViewHolder147(val binding147: ItemBarang147Binding) :
        RecyclerView.ViewHolder(binding147.root)

    override fun onCreateViewHolder(parent147: ViewGroup, viewType147: Int): ItemViewHolder147 {
        val binding147 = ItemBarang147Binding.inflate(
            LayoutInflater.from(parent147.context),
            parent147,
            false
        )
        return ItemViewHolder147(binding147)
    }

    override fun onBindViewHolder(holder147: ItemViewHolder147, position147: Int) {
        val currentItem147 = itemList147[position147]
        with(holder147.binding147) {
            tvItemNama147.text = currentItem147.nama147
            tvItemKategori147.text = currentItem147.kategori147
            tvItemJumlah147.text = "Stok: ${currentItem147.jumlah147} Unit"

            val hargaFormatted147 = rupiahFormat147.format(currentItem147.harga147)
            tvItemHargaSatuan147.text = "@ $hargaFormatted147"

            val subtotalNilai147 = currentItem147.jumlah147.toLong() * currentItem147.harga147
            val subtotalFormatted147 = rupiahFormat147.format(subtotalNilai147)
            tvItemSubtotal147.text = "Total: $subtotalFormatted147"

            tvItemKondisi147.text = "Kondisi: ${currentItem147.kondisi147} (${currentItem147.nilaiKondisi147}%)"

            if (currentItem147.isTersedia147) {
                tvItemStatusTersedia147.text = "Tersedia"
                tvItemStatusTersedia147.setBackgroundResource(com.example.uts.R.drawable.bg_badge_green_147)
                tvItemStatusTersedia147.setTextColor(root.context.getColor(com.example.uts.R.color.green_soft_text_147))
            } else {
                tvItemStatusTersedia147.text = "Tidak Tersedia"
                tvItemStatusTersedia147.setBackgroundResource(com.example.uts.R.drawable.bg_badge_red_147)
                tvItemStatusTersedia147.setTextColor(root.context.getColor(com.example.uts.R.color.red_soft_text_147))
            }

            btnItemEdit147.setOnClickListener {
                onEditClick147(currentItem147)
            }

            btnItemDelete147.setOnClickListener {
                onDeleteClick147(currentItem147)
            }

            root.setOnClickListener {
                onEditClick147(currentItem147)
            }
        }
    }

    override fun getItemCount(): Int = itemList147.size

    fun updateData147(newList147: List<Item147>) {
        itemList147 = newList147
        notifyDataSetChanged()
    }
}
