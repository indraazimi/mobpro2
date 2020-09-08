/*
 * Copyright (c) 2020-2023 Indra Azimi. All rights reserved.
 *
 * Dibuat untuk kelas Pemrograman untuk Perangkat Bergerak 2.
 * Dilarang melakukan penggandaan dan atau komersialisasi,
 * sebagian atau seluruh bagian, baik cetak maupun elektronik
 * terhadap project ini tanpa izin pemilik hak cipta.
 */

package com.indraazimi.mobpro2

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.indraazimi.mobpro2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding.chart) {
            setNoDataText(getString(R.string.belum_ada_data))
            description.text = ""
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            axisLeft.axisMinimum = 0f
            axisRight.isEnabled = false

            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            legend.setDrawInside(false)
        }


        myAdapter = MainAdapter()
        with(binding.recyclerView) {
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            setHasFixedSize(true)
            adapter = myAdapter
        }

        viewModel.getData().observe(this) { myAdapter.setData(it) }
        viewModel.getEntries().observe(this) { updateChart(it) }
        viewModel.getStatus().observe(this) { updateProgress(it) }
    }

    private fun updateChart(entries: List<Entry>) {
        val dataset = LineDataSet(entries, getString(R.string.jumlah_kasus_positif))
        dataset.color = ContextCompat.getColor(this, R.color.purple_500)
        dataset.fillColor = dataset.color
        dataset.setDrawFilled(true)
        dataset.setDrawCircles(false)

        binding.chart.data = LineData(dataset)
        binding.chart.invalidate()
    }

    private fun updateProgress(status: ApiStatus) {
        when (status) {
            ApiStatus.LOADING -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            ApiStatus.SUCCESS -> {
                binding.progressBar.visibility = View.GONE
            }
            ApiStatus.FAILED -> {
                binding.progressBar.visibility = View.GONE
                binding.errorTextView.visibility = View.VISIBLE
            }
        }
    }
}