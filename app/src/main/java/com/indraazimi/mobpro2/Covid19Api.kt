/*
 * Copyright (c) 2020-2023 Indra Azimi. All rights reserved.
 *
 * Dibuat untuk kelas Pemrograman untuk Perangkat Bergerak 2.
 * Dilarang melakukan penggandaan dan atau komersialisasi,
 * sebagian atau seluruh bagian, baik cetak maupun elektronik
 * terhadap project ini tanpa izin pemilik hak cipta.
 */

package com.indraazimi.mobpro2

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

object Covid19Api {

    private const val BASE_URL = "https://raw.githubusercontent.com/indraazimi/mobpro2/static-api/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    val service : ApiService by lazy { retrofit.create(ApiService::class.java) }

    interface ApiService {
        @GET("update.json")
        suspend fun getData(): String
    }
}