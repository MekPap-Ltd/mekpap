package com.mekpap.mekPap.payment

import com.google.gson.GsonBuilder
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface PaymentInterface{
    @POST("payment")
    fun payment(@Body creds:Payment):Observable<Any>

    companion object {
        fun create():PaymentInterface{
            val gson = GsonBuilder()
                    .setLenient()
                    .create()
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create()
                    )
                    .addConverterFactory(
                            GsonConverterFactory.create(gson)
                    )
                    .baseUrl("https://us-central1-mekpap-45765.cloudfunctions.net/createReceipt/")
                    .build()
            return retrofit.create(PaymentInterface::class.java)
        }
    }
}