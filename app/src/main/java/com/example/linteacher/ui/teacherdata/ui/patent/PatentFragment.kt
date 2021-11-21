package com.example.linteacher.ui.teacherdata.ui.patent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.linteacher.R
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.teacherdata.award.data.AwardBaseData
import com.example.linteacher.api.pojo.teacherdata.patent.PatentAllResponse
import com.example.linteacher.api.pojo.teacherdata.patent.PatentPostRequest
import com.example.linteacher.api.pojo.teacherdata.patent.PatentResponse
import com.example.linteacher.api.pojo.teacherdata.patent.PatentUpdateRequest
import com.example.linteacher.api.pojo.teacherdata.patent.data.PatentBaseData
import com.example.linteacher.databinding.FragmentPatentBinding
import com.example.linteacher.ui.teacherdata.ui.patent.*
import com.example.linteacher.util.Config
import com.example.linteacher.util.preference.LoginPreferences

class PatentFragment:Fragment(), PatentInterface.View {
    companion object {
        fun newInstance() = PatentFragment()
    }

    private var _binding: FragmentPatentBinding? = null
    private val binding get() = _binding!!
    private lateinit var loginPreferences: LoginPreferences
    //viewmodel
    private val factory = PatentViewModelFactory(PatentRepository())
    private val viewModel: PatentViewModel by viewModels {
        factory
    }
    private lateinit var adapter: PatentAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPatentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences = LoginPreferences(view.context)
        initRecycleView()
        viewModelObserveLst()
        binding.addPatent.setOnClickListener {
            val list = adapter.getDataList() as ArrayList<PatentBaseData>
            list.add(PatentBaseData(itemType = Config.ADD_VIEW_TYPE))
            //list add一筆空的LicAddData,傳入adapter
            adapter.setDataList(list)
        }
    }

    private fun viewModelObserveLst() {
        //git all lic by loginId
        viewModel.getList(loginPreferences.getTeacherId())
                .observe(viewLifecycleOwner, object : Observer<PatentAllResponse> {
                    override fun onChanged(t: PatentAllResponse) {
                        //觀察對象 LicenseAllResponse->licOne的list和是否error
                        if (t.list.isEmpty()) {

                        } else {
                            //baseData =初始模底
                            val valueLst = arrayListOf<PatentBaseData>()
                            for (r: PatentResponse in t.list) {
                                valueLst.add(
                                        PatentBaseData(
                                                patProject = r.patProject,
                                                patmainPatentName = r.patmainPatentName,
                                                patReportCode = r.patReportCode,
                                                patReportEdata = r.patReportEdata,
                                                patAuthor = r.patAuthor,
                                                patAppmainLicant = r.patAppmainLicant,
                                                patAppmainLicationDate = r.patAppmainLicationDate,
                                                patEndDate = r.patEndDate,
                                                patmainLicensingAgency = r.patmainLicensingAgency,
                                                patCertificateNumber = r.patCertificateNumber,
                                                patCountry = r.patCountry,
                                                patType = r.patType,
                                                patProgressStatus = r.patProgressStatus,
                                                patAppmainLicantType = r.patAppmainLicantType,

                                                patId = r.patId,
                                                itemType = Config.ORIGIN_VIEW_TYPE
                                        )
                                )

                            }
                            Log.d("theid", "onChanged: "+ t.list[0].patId)
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
        binding.PatentRecycleView.layoutManager = layoutManager
        val list = arrayListOf<PatentBaseData>() //LicBaseData為初始模底
        list.add(PatentBaseData()) //LicOriginData是LicBaseData的小孩
        Log.d("fuckckc", "hh: "+list.size)

        adapter = PatentAdapter(list, this)//放一個空得進去:)
        _binding?.PatentRecycleView?.adapter = adapter
    }

    override fun onSaveClick(r: PatentBaseData, position: Int) {
        //request = adpter傳入的LicAddData內容+loginId

        val request= PatentPostRequest(
                patProject = r.patProject,
                patmainPatentName = r.patmainPatentName,
                patReportCode = r.patReportCode,
                patReportEdata = r.patReportEdata,
                patAuthor = r.patAuthor,
                patAppmainLicant = r.patAppmainLicant,
                patAppmainLicationDate = r.patAppmainLicationDate,
                patEndDate = r.patEndDate,
                patmainLicensingAgency = r.patmainLicensingAgency,
                patCertificateNumber = r.patCertificateNumber,
                patCountry = r.patCountry,
                patType = r.patType,
                patProgressStatus = r.patProgressStatus,
                patAppmainLicantType = r.patAppmainLicantType,

                patId = r.patId,
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

    override fun onEditSaveClick(r: PatentBaseData, position: Int) {
        //request =LicEditData內容+loginId
        val request=
                PatentUpdateRequest(
                        patProject = r.patProject,
                        patmainPatentName = r.patmainPatentName,
                        patReportCode = r.patReportCode,
                        patReportEdata = r.patReportEdata,
                        patAuthor = r.patAuthor,
                        patAppmainLicant = r.patAppmainLicant,
                        patAppmainLicationDate = r.patAppmainLicationDate,
                        patEndDate = r.patEndDate,
                        patmainLicensingAgency = r.patmainLicensingAgency,
                        patCertificateNumber = r.patCertificateNumber,
                        patCountry = r.patCountry,
                        patType = r.patType,
                        patProgressStatus = r.patProgressStatus,
                        patAppmainLicantType = r.patAppmainLicantType,
                        public = r.public,
                    patId = r.patId,
                        loginId = loginPreferences.getTeacherId().toInt(),

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
                            (adapter.getDataList()  as ArrayList<PatentBaseData>) .add(
                                    position, PatentBaseData(
                                    //觸發EditViewHolder,這筆LicEditData會有新的樣貌
                                    patProject = t.list.patProject,
                                    patmainPatentName = t.list.patmainPatentName,
                                    patReportCode = t.list.patReportCode,
                                    patReportEdata = t.list.patReportEdata,
                                    patAuthor = t.list.patAuthor,
                                    patAppmainLicant = t.list.patAppmainLicant,
                                    patAppmainLicationDate = t.list.patAppmainLicationDate,
                                    patEndDate = t.list.patEndDate,
                                    patmainLicensingAgency = t.list.patmainLicensingAgency,
                                    patCertificateNumber = t.list.patCertificateNumber,
                                    patCountry = t.list.patCountry,
                                    patType = t.list.patType,
                                    patProgressStatus = t.list.patProgressStatus,
                                    patAppmainLicantType = t.list.patAppmainLicantType,

                                    patId = t.list.patId,
                                    itemType = Config.EdIT_VIEW_TYPE,

                            ))

                            adapter.setOneData(adapter.getDataList(), position)
                        })
    }

    override fun onEditCancelClick(position: Int, r: PatentBaseData) {
        //移除LicEditData 新增LicOriginData
        var data : PatentBaseData = PatentBaseData()
        data = r
        data.itemType = Config.ORIGIN_VIEW_TYPE
        adapter.getDataList().removeAt(position)
        val list=adapter.getDataList() as ArrayList<PatentBaseData>
        list.add(position,data)
        adapter.setOneData(list,position)
    }
}