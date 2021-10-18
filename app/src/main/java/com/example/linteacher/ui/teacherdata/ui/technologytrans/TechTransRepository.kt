package com.example.linteacher.ui.teacherdata.ui.technologytrans

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.linteacher.api.RetrofitManager
import com.example.linteacher.api.pojo.teacherdata.paper.PaperAllResponse
import com.example.linteacher.api.pojo.teacherdata.paper.PaperResponse
import com.example.linteacher.api.pojo.teacherdata.techtransfer.TechTransFerAllResponse
import com.example.linteacher.api.pojo.teacherdata.techtransfer.TechTransFerFirstResponse
import com.example.linteacher.util.Config
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class TechTransRepository {
    fun getData(loginId:String): MutableLiveData<TechTransFerAllResponse> {
        val data= MutableLiveData<TechTransFerAllResponse>()
        val url=String.format(Config.GET_TECHCHANGE_FIRST,loginId);
        Log.d("fuckURLLL", "url : "+url)
        RetrofitManager.compositeDisposable.add(
            RetrofitManager.apiServices.getTechFistList(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<TechTransFerFirstResponse>>(){
                    override fun onNext(t: List<TechTransFerFirstResponse>) {
                        data.value= TechTransFerAllResponse(t, Config.RESULT_OK)
                    }

                    override fun onError(e: Throwable) {
                        data.value= TechTransFerAllResponse(arrayListOf(),e.toString())
                    }

                    override fun onComplete() {

                    }

                })
        )
        return data
    }
}