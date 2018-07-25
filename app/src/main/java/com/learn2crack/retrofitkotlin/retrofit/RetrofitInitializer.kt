package com.learn2crack.retrofitkotlin.retrofit

import com.learn2crack.retrofitkotlin.retrofit.service.NoteService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    private val retrofit = Retrofit.Builder()
            .baseUrl("https://learn2crack-json.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun noteService() = retrofit.create(NoteService::class.java)
}