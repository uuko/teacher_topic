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
import com.example.linteacher.api.pojo.teacherdata.adamic.AcademicChangeVisibleRequest
import com.example.linteacher.api.pojo.teacherdata.adamic.data.AdemicEventBaseData
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
            //list add????????????LicAddData,??????adapter
            adapter.setDataList(list)
        }
    }

    private fun viewModelObserveLst() {
        //git all lic by loginId
        viewModel.getList(loginPreferences.getTeacherId())
                .observe(viewLifecycleOwner, object : Observer<BookAllResponse> {
                    override fun onChanged(t: BookAllResponse) {
                        //???????????? LicenseAllResponse->licOne???list?????????error
                        if (t.list.isEmpty()) {

                        } else {
                            //baseData =????????????
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
                                            public = r.public,

                                                itemType = Config.ORIGIN_VIEW_TYPE
                                        )
                                )

                            }
                            Log.d("theid", "onChanged: "+ t.list[0].infNumber)
                            adapter.setDataList(valueLst) //?????????????????????
                        }


                    }

                })
    }

    private fun initRecycleView() {
        //???recycleview?????????,??????list??????get all lic ,??????list??????adapter,recycleview setAdapter
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        layoutManager.reverseLayout = false
        binding.BookRecycleView.layoutManager = layoutManager
        val list = arrayListOf<BookBaseData>() //LicBaseData???????????????
        list.add(BookBaseData()) //LicOriginData???LicBaseData?????????
        Log.d("fuckckc", "hh: "+list.size)

        adapter = BookAdapter(list, this)//?????????????????????:)
        _binding?.BookRecycleView?.adapter = adapter
    }

    override fun onSaveClick(r: BookBaseData, position: Int) {
        //request = adpter?????????LicAddData??????+loginId

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
                        //???????????? =t = UnitResponse ,????????????setValue??????onChanged
                        if (t.result != Config.RESULT_OK){

                        }
                        else{
                            //???api??????->??????
                            viewModelObserveLst()
                        }
                    }

                })
    }

    override fun onCancelClick(position: Int) {
        //??????,??????list[position]
        adapter.getDataList().removeAt(position)
        adapter.setDataList(adapter.getDataList())
    }

    override fun onDeleteClick(expNumber: Int, position: Int) {
        viewModel.delete(expNumber)
                .observe(viewLifecycleOwner, {
                    if (it.result == Config.RESULT_OK) {
                        //??????list[position]
                        adapter.getDataList().removeAt(position)
                        adapter.setDataList(adapter.getDataList())
                    }
                })
    }

    override fun onEditSaveClick(r: BookBaseData, position: Int) {
        //request =LicEditData??????+loginId
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
                    public = r.public,

                    loginId = loginPreferences.getTeacherId().toInt(),
                        infNumber = r.infNumber

                )

        viewModel.updateList(request)
                .observe(viewLifecycleOwner,{
                    t->
                    if (t.result == Config.RESULT_OK){
                        //???API??????->??????
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
                            //????????????LicOriginData  ????????????LicEditData
                            adapter.getDataList().removeAt(position)
                            (adapter.getDataList()  as ArrayList<BookBaseData>) .add(
                                    //??????EditViewHolder,??????LicEditData??????????????????
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
                                    public = t.list.public,

                                    infNumber=t.list.infNumber,
                                    itemType = Config.EdIT_VIEW_TYPE

                            )
                            )
                            adapter.setOneData(adapter.getDataList(), position)
                        })
    }

    override fun onEditCancelClick(position: Int, r: BookBaseData) {
        //??????LicEditData ??????LicOriginData
        var data : BookBaseData = BookBaseData()
        data = r
        data.itemType = Config.ORIGIN_VIEW_TYPE
        adapter.getDataList().removeAt(position)
        val list=adapter.getDataList() as ArrayList<BookBaseData>
        list.add(position,data)
        adapter.setOneData(list,position)
    }

    override fun onChangeVisibleClick(r: BookBaseData, position: Int) {
        //request =LicEditData??????+loginId
        val request=
            AcademicChangeVisibleRequest(
                id = r.infNumber,
                visible = r.public

            )

        viewModel.changeVisible(request)
            .observe(viewLifecycleOwner,{
                    t->
                if (t.result == Config.RESULT_OK){
                    //???API??????->??????
                    viewModelObserveLst()
                }
            })
    }

}