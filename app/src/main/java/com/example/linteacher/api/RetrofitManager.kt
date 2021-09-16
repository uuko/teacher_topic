package com.example.linteacher.api

import android.util.Log
import com.example.linteacher.util.Config

import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager constructor() {
    private val retrofit: Retrofit
    private var logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            Log.i("interceptor msg", message)
        }
    })
    private var okHttpClient : OkHttpClient

    init {
        logging.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient = OkHttpClient().newBuilder().addInterceptor(logging).build()
        retrofit = Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    companion object {
        val compositeDisposable get() = CompositeDisposable()
        private val manager = RetrofitManager( )
        private val client: Retrofit= manager.retrofit
        val apiServices get() =  client.create(ApiServices::class.java)

    }
}