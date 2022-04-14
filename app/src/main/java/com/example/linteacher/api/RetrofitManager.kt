package com.example.linteacher.api

import android.content.Context
import android.util.Log
import com.example.linteacher.util.Config
import com.example.linteacher.util.preference.LoginPreferences

import io.reactivex.disposables.CompositeDisposable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager constructor(mContext: Context) {
    private val retrofit: Retrofit
    private val loginRetrofit: Retrofit
    private var logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            Log.i("interceptor msg", message)
        }
    })
    private var okHttpClient: OkHttpClient
    private var loginOkHttpClient: OkHttpClient


    init {
        val loginSharePreferences = LoginPreferences(mContext)
        Log.d("母咪", ": "+loginSharePreferences.getToken())
        logging.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient = OkHttpClient().newBuilder()
                .addInterceptor(logging)
                .addInterceptor(object : Interceptor {
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val newRequest: Request = chain.request().newBuilder()
                                .addHeader("Authorization", loginSharePreferences.getToken())
                                .build()
                        return chain.proceed(newRequest)
                    }

                })
                .build()
        loginOkHttpClient = OkHttpClient().newBuilder()
                .addInterceptor(logging)
                .build()
        loginRetrofit = Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
        retrofit = Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
    }

    companion object {
        private var retrofitManager: RetrofitManager = RetrofitManager(MyApplication.sContext)

        val compositeDisposable get() = CompositeDisposable()

        private val client: Retrofit = retrofitManager.retrofit
        private val loginClient: Retrofit = retrofitManager.loginRetrofit;
        val apiServices get() = client.create(ApiServices::class.java)
        val loginApiServices get() = loginClient.create(ApiServices::class.java)
    }
}