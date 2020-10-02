/*
 * Copyright (c) 2020-2023 Indra Azimi. All rights reserved.
 *
 * Dibuat untuk kelas Pemrograman untuk Perangkat Bergerak 2.
 * Dilarang melakukan penggandaan dan atau komersialisasi,
 * sebagian atau seluruh bagian, baik cetak maupun elektronik
 * terhadap project ini tanpa izin pemilik hak cipta.
 */

package com.indraazimi.mobpro2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.indraazimi.mobpro2.data.Mahasiswa
import com.indraazimi.mobpro2.data.MahasiswaDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val db : MahasiswaDao) : ViewModel() {

    val data = db.getData()

    fun insertData(mahasiswa: Mahasiswa) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.insertData(mahasiswa)
            }
        }
    }

    fun deleteData(ids: List<Int>) {
        val newIds = ids.toList()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.deleteData(newIds)
            }
        }
    }
}