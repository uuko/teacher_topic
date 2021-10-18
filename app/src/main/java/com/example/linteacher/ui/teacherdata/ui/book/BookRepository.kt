package com.example.linteacher.ui.teacherdata.ui.book

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.linteacher.api.RetrofitManager
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.teacherdata.adamic.AcademicChangeVisibleRequest
import com.example.linteacher.api.pojo.teacherdata.book.*
import com.example.linteacher.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class BookRepository {

    //回傳一個老師的證照資料
    fun getOneData(loginId: String): MutableLiveData<BookAllOneResponse> {
        //得到data = BookAllOneResponse
        val data = MutableLiveData<BookAllOneResponse>()
        //url= /teacher/Bookense/BookId/+loginId
        val url=String.format(Config.GET_ONE_BOOK,loginId);
        RetrofitManager.compositeDisposable.add(
                RetrofitManager.apiServices.getOneBookData(url)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableObserver<BookResponse>() {
                            override fun onNext(t: BookResponse) {
                                //view model  data = BookAllOneResponse
                                data.value = BookAllOneResponse(t, Config.RESULT_OK)
                            }

                            override fun onError(e: Throwable) {
                                data.value = BookAllOneResponse(BookResponse(), e.toString())
                            }

                            override fun onComplete() {

                            }

                        })
        )
        return data
    }
    fun getData(loginId:String): MutableLiveData<BookAllResponse> {
        val data= MutableLiveData<BookAllResponse>()
        val url=String.format(Config.GET_BOOK,loginId);
        RetrofitManager.compositeDisposable.add(
                RetrofitManager.apiServices.getBookData(url)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableObserver<List<BookResponse>>(){
                            override fun onNext(t: List<BookResponse>) {
                                data.value= BookAllResponse(t, Config.RESULT_OK)
                            }

                            override fun onError(e: Throwable) {
                                data.value= BookAllResponse(arrayListOf(),e.toString())
                            }

                            override fun onComplete() {

                            }

                        })
        )
        return data
    }
    fun deleteData(loginId: String): MutableLiveData<UnitResponse> {
        val data = MutableLiveData<UnitResponse>()
        val request = BookDeleteRequest(loginId.toInt())
        Log.d("BOOKID", "deleteData: "+request.infNumber)

        RetrofitManager.compositeDisposable.add(
                RetrofitManager.apiServices.deleteBookData(request)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableObserver<Unit>() {
                            override fun onNext(t: Unit) {
                                data.value = UnitResponse(Config.RESULT_OK)
                            }

                            override fun onError(e: Throwable) {
                                data.value = UnitResponse(e.toString())
                            }

                            override fun onComplete() {

                            }

                        })
        )
        return data
    }
    fun postData(request: BookPostRequest): MutableLiveData<UnitResponse> {
        //MutableLiveData->UnitResponse
        val data= MutableLiveData<UnitResponse>()
        RetrofitManager.compositeDisposable.add(
                RetrofitManager.apiServices.postBookData(Config.POST_BOOK,request)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableObserver<Unit>(){
                            override fun onNext(t: Unit) {
                                //setValue ->觸發obsebever
                                data.value= UnitResponse(Config.RESULT_OK)
                            }

                            override fun onError(e: Throwable) {
                                data.value= UnitResponse(e.toString())
                            }

                            override fun onComplete() {

                            }

                        })
        )
        return data
    }
    fun updateData(request: BookUpdateRequest): MutableLiveData<UnitResponse> {
        val data= MutableLiveData<UnitResponse>()
        RetrofitManager.compositeDisposable.add(
                RetrofitManager.apiServices.updateBookData(Config.UPDATE_BOOK,request)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableObserver<Unit>(){
                            override fun onNext(t: Unit) {
                                data.value= UnitResponse(Config.RESULT_OK)
                            }

                            override fun onError(e: Throwable) {
                                data.value= UnitResponse(e.toString())
                            }

                            override fun onComplete() {

                            }

                        })
        )
        return data
    }

    //改變item是否公開
    fun changeVisible(request: AcademicChangeVisibleRequest): MutableLiveData<UnitResponse>  {
        Log.d("whaaaa", "updateData: "+request.id)
        val data= MutableLiveData<UnitResponse>()
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.changeVisibleAdemicEventData(Config.CHANGE_VISIBLE_BOOK,request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Unit>(){
                    override fun onNext(t: Unit) {
                        data.value= UnitResponse(Config.RESULT_OK)
                    }

                    override fun onError(e: Throwable) {
                        data.value= UnitResponse(e.toString())
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }
}