package com.example.linteacher.ui.teacherdata.ui.book

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.teacherdata.book.BookAllResponse
import com.example.linteacher.api.pojo.teacherdata.book.BookPostRequest
import com.example.linteacher.api.pojo.teacherdata.book.BookResponse
import com.example.linteacher.api.pojo.teacherdata.book.BookUpdateRequest
import com.example.linteacher.api.pojo.teacherdata.book.data.BookBaseData
import com.example.linteacher.databinding.FragmentBookBinding
import com.example.linteacher.ui.teacherdata.ui.book.*
import com.example.linteacher.util.Config
import com.example.linteacher.util.preference.LoginPreferences

class BookFragment : Fragment(), BookInterface.View{
    companion object {
        fun newInstance() = BookFragment()
    }

    private var _binding: FragmentBookBinding? = null
    private val binding get() = _binding!!
    private lateinit var loginPreferences: LoginPreferences
    //viewmodel
    private val factory = BookViewModelFactory(BookRepository())
    private val viewModel: BookViewModel by viewModels {
        factory
    }
    private lateinit var adapter: BookAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences = LoginPreferences(view.context)
        initRecycleView()
        viewModelObserveLst()
        binding.addBook.setOnClickListener {
            val list = adapter.getDataList() as ArrayList<BookBaseData>
            list.add(BookBaseData(itemType = Config.ADD_VIEW_TYPE))
            //list add一筆空的LicAddData,傳入adapter
            adapter.setDataList(list)
        }
    }

    private fun viewModelObserveLst() {
        //git all lic by loginId
        viewModel.getList(loginPreferences.getTeacherId())
                .observe(viewLifecycleOwner, object : Observer<BookAllResponse> {
                    override fun onChanged(t: BookAllResponse) {
                        //觀察對象 LicenseAllResponse->licOne的list和是否error
                        if (t.list.isEmpty()) {

                        } else {
                            //baseData =初始模底
                            val valueLst = arrayListOf<BookBaseData>()
                            for (r: BookResponse in t.list) {
                                valueLst.add(
                                        BookBaseData(
                                                infCategory = r.infCategory,
                                                infWhemainTher = r.infWhemainTher,
                                                infName = r.infName,
                                                infAudit = r.infAudit,
                                                infLanguage = r.infLanguage,
                                                infCoop = r.infCoop,
                                                infAuthorOrder = r.infAuthorOrder,
                                                infPubmainLicYear = r.infPubmainLicYear,
                                                infPubmainLicMonth = r.infPubmainLicMonth,
                                                infPublishHouse = r.infPublishHouse,
                                                infISBN = r.infISBN,
                                                infPlan = r.infPlan,
                                                infCorreAuthor = r.infCorreAuthor,
                                                infNumber = r.infNumber,

                                                itemType = Config.ORIGIN_VIEW_TYPE
                                        )
                                )

                            }
                            Log.d("theid", "onChanged: "+ t.list[0].infNumber)
                            adapter.setDataList(valueLst) //照理說就有植了
                        }


                    }

                })
    }

    private fun initRecycleView() {
        //對recycleview初始化,初始list放入get all lic ,再將list傳入adapter,recycleview setAdapter
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        layoutManager.reverseLayout = false
        binding.BookRecycleView.layoutManager = layoutManager
        val list = arrayListOf<BookBaseData>() //LicBaseData為初始模底
        list.add(BookBaseData()) //LicOriginData是LicBaseData的小孩
        Log.d("fuckckc", "hh: "+list.size)

        adapter = BookAdapter(list, this)//放一個空得進去:)
        _binding?.BookRecycleView?.adapter = adapter
    }

    override fun onSaveClick(r: BookBaseData, position: Int) {
        //request = adpter傳入的LicAddData內容+loginId

        val request= BookPostRequest(
                infCategory = r.infCategory,
                infWhemainTher = r.infWhemainTher,
                infName = r.infName,
                infAudit = r.infAudit,
                infLanguage = r.infLanguage,
                infCoop = r.infCoop,
                infAuthorOrder = r.infAuthorOrder,
                infPubmainLicYear = r.infPubmainLicYear,
                infPubmainLicMonth = r.infPubmainLicMonth,
                infPublishHouse = r.infPublishHouse,
                infISBN = r.infISBN,
                infPlan = r.infPlan,
                infCorreAuthor = r.infCorreAuthor,

                infNumber = r.infNumber,
                loginId = loginPreferences.getTeacherId().toInt(),
        )

        //postData(request)->
        viewModel.postData(request)
                .observe(viewLifecycleOwner,object : Observer<UnitResponse> {
                    override fun onChanged(t: UnitResponse) {
                        //觀察目標 =t = UnitResponse ,如果有被setValue進入onChanged
                        if (t.result != Config.RESULT_OK){

                        }
                        else{
                            //打api失敗->刷新
                            viewModelObserveLst()
                        }
                    }

                })
    }

    override fun onCancelClick(position: Int) {
        //疑惑,刪除list[position]
        adapter.getDataList().removeAt(position)
        adapter.setDataList(adapter.getDataList())
    }

    override fun onDeleteClick(expNumber: Int, position: Int) {
        viewModel.delete(expNumber)
                .observe(viewLifecycleOwner, {
                    if (it.result == Config.RESULT_OK) {
                        //刪除list[position]
                        adapter.getDataList().removeAt(position)
                        adapter.setDataList(adapter.getDataList())
                    }
                })
    }

    override fun onEditSaveClick(r: BookBaseData, position: Int) {
        //request =LicEditData內容+loginId
        val request=
                BookUpdateRequest(
                        infCategory = r.infCategory,
                        infWhemainTher = r.infWhemainTher,
                        infName = r.infName,
                        infAudit = r.infAudit,
                        infLanguage = r.infLanguage,
                        infCoop = r.infCoop,
                        infAuthorOrder = r.infAuthorOrder,
                        infPubmainLicYear = r.infPubmainLicYear,
                        infPubmainLicMonth = r.infPubmainLicMonth,
                        infPublishHouse = r.infPublishHouse,
                        infISBN = r.infISBN,
                        infPlan = r.infPlan,
                        infCorreAuthor = r.infCorreAuthor,
                        loginId = loginPreferences.getTeacherId().toInt(),
                        infNumber = r.infNumber

                )

        viewModel.updateList(request)
                .observe(viewLifecycleOwner,{
                    t->
                    if (t.result == Config.RESULT_OK){
                        //打API成功->刷新
                        viewModelObserveLst()
                    }
                })
    }

    override fun onEditClick(awaId: String, position: Int) {
        //get lic by licId
        Log.d("theId", "onEditClick: "+awaId)

        viewModel.getDataByExpNumber(awaId.toString())
                .observe(viewLifecycleOwner,
                        { t ->
                            //移除舊的LicOriginData  新增新的LicEditData
                            adapter.getDataList().removeAt(position)
                            (adapter.getDataList()  as ArrayList<BookBaseData>) .add(
                                    //觸發EditViewHolder,這筆LicEditData會有新的樣貌
                                    position, BookBaseData(
                                    infCategory = t.list.infCategory,
                                    infWhemainTher = t.list.infWhemainTher,
                                    infName = t.list.infName,
                                    infAudit = t.list.infAudit,
                                    infLanguage = t.list.infLanguage,
                                    infCoop = t.list.infCoop,
                                    infAuthorOrder = t.list.infAuthorOrder,
                                    infPubmainLicYear = t.list.infPubmainLicYear,
                                    infPubmainLicMonth = t.list.infPubmainLicMonth,
                                    infPublishHouse = t.list.infPublishHouse,
                                    infISBN = t.list.infISBN,
                                    infPlan = t.list.infPlan,
                                    infCorreAuthor = t.list.infCorreAuthor,

                                    infNumber=t.list.infNumber,
                                    itemType = Config.EdIT_VIEW_TYPE

                            )
                            )
                            adapter.setOneData(adapter.getDataList(), position)
                        })
    }

    override fun onEditCancelClick(position: Int, r: BookBaseData) {
        //移除LicEditData 新增LicOriginData
        var data : BookBaseData = BookBaseData()
        data = r
        data.itemType = Config.ORIGIN_VIEW_TYPE
        adapter.getDataList().removeAt(position)
        val list=adapter.getDataList() as ArrayList<BookBaseData>
        list.add(position,data)
        adapter.setOneData(list,position)
    }

}