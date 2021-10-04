package com.example.linteacher.ui.articletag

import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.linteacher.api.RetrofitManager
import com.example.linteacher.api.pojo.artical.ArticalGetResponse
import com.example.linteacher.api.pojo.artical.ArticleResponse
import com.example.linteacher.api.pojo.artical.ArticleTagResponse
import com.example.linteacher.api.pojo.artical.LatestAllArticleResponse
import com.example.linteacher.ui.main.listannounce.FeedDataSource
import com.example.linteacher.util.Config
import com.example.linteacher.util.NetworkState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class TagDataSource(private val searchQuery: String) :
    PageKeyedDataSource<Long, ArticleResponse>() {
    /*
   * Step 1: Initialize the restApiFactory.
   * The networkState and initialLoading variables
   * are for updating the UI when data is being fetched
   * by displaying a progress bar
   */

    val networkState = MutableLiveData<NetworkState>()
    val initialLoading = MutableLiveData<Any?>()

    /*
   * Step 2: This method is responsible to load the data initially
   * when app screen is launched for the first time.
   * We are fetching the first page data from the api
   * and passing it via the callback method to the UI.
   */
    override fun loadInitial(
        @NonNull params: LoadInitialParams<Long>,
        @NonNull callback: LoadInitialCallback<Long, ArticleResponse>
    ) {
        initialLoading.postValue(NetworkState.LOADING)
        networkState.postValue(NetworkState.LOADING)
        val data = MutableLiveData<LatestAllArticleResponse>()
        val url = String.format(Config.GET_ARTICLE_PAGING_TAGS, 0, searchQuery)
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.getArticleByTag(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ArticleTagResponse>() {
                    override fun onNext(item: ArticleTagResponse) {
                        Log.d(TAG, "onNext: ${item.responses.size}")
                        callback.onResult(item.responses, null, 2L)
                        initialLoading.postValue(NetworkState.LOADED)
                        networkState.postValue(NetworkState.LOADED)
                    }

                    override fun onError(e: Throwable) {
                        initialLoading.postValue(
                            NetworkState(
                                NetworkState.Status.FAILED,
                                e.toString()
                            )
                        )
                        networkState.postValue(
                            NetworkState(
                                NetworkState.Status.FAILED,
                                e.toString()
                            )
                        )
                    }

                    override fun onComplete() {

                    }

                })
        )
    }

    override fun loadBefore(
        @NonNull params: LoadParams<Long>,
        @NonNull callback: LoadCallback<Long, ArticleResponse?>
    ) {
    }

    /*
   * Step 3: This method it is responsible for the subsequent call to load the data page wise.
   * This method is executed in the background thread
   * We are fetching the next page data from the api
   * and passing it via the callback method to the UI.
   * The "params.key" variable will have the updated value.
   */
    override fun loadAfter(
        params: LoadParams<Long>,
        callback: LoadCallback<Long, ArticleResponse>
    ) {
        Log.i(TAG, "Loading Rang " + params.key + " Count " + params.requestedLoadSize)
        networkState.postValue(NetworkState.LOADING)
        val url = String.format(Config.GET_ARTICLE_PAGING_TAGS, params.key - 1, searchQuery)
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.getArticleByTag(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ArticleTagResponse>() {
                    override fun onNext(item: ArticleTagResponse) {
                        var nextKey: Long? = 0
                        if (params.key.equals(item.pageTotalCount)) nextKey = null
                        else nextKey = params.key + 1
                        callback.onResult(item.responses, nextKey)
                        networkState.postValue(NetworkState.LOADED)
                    }

                    override fun onError(e: Throwable) {
                        networkState.postValue(
                            NetworkState(
                                NetworkState.Status.FAILED,
                                e.toString()
                            )
                        )
                    }

                    override fun onComplete() {

                    }

                })
        )
    }

    companion object {
        private val TAG = "TagDataSource"
    }


}