package com.learn2crack.retrofitkotlin.retrofit.service

import com.learn2crack.retrofitkotlin.model.Android
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NoteService {
//    @GET("notes")
//    fun list(): Call<List<Note>>

//    @POST("notes")
//    fun insert(@Body note: Note): Call<Note>

    @GET("api/android")
//    fun list() : Call<List<Android>>
    fun list() : Call<ResponseBody>

}