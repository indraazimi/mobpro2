/*
 * Copyright (c) 2020-2023 Indra Azimi. All rights reserved.
 *
 * Dibuat untuk kelas Pemrograman untuk Perangkat Bergerak 2.
 * Dilarang melakukan penggandaan dan atau komersialisasi,
 * sebagian atau seluruh bagian, baik cetak maupun elektronik
 * terhadap project ini tanpa izin pemilik hak cipta.
 */

package com.indraazimi.mobpro2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.indraazimi.mobpro2.databinding.ItemMainBinding
import com.indraazimi.mobpro2.model.Harian
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private val formatter = SimpleDateFormat("dd MMMM", Locale("ID", "id"))
    private val data = mutableListOf<Harian>()

    fun setData(newData: List<Harian>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    fun getDate(position: Int): Date {
        return Date(data[position].key)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMainBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[data.size - position - 1])
    }

    inner class ViewHolder(
        private val binding: ItemMainBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(harian: Harian) = with(binding) {
            tanggalTextView.text = formatter.format(Date(harian.key))
            positifTextView.text = itemView.context.getString(R.string.x_orang, harian.jumlahPositif.value)
        }
    }
}