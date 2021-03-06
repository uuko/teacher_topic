package com.example.linteacher.ui.teacherdata.ui.license

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.teacherdata.adamic.AcademicChangeVisibleRequest
import com.example.linteacher.api.pojo.teacherdata.adamic.data.AdemicEventBaseData
import com.example.linteacher.api.pojo.teacherdata.license.LicPostRequest
import com.example.linteacher.api.pojo.teacherdata.license.LicUpdateRequest
import com.example.linteacher.api.pojo.teacherdata.license.LicenseResponse
import com.example.linteacher.api.pojo.teacherdata.license.all.LicenseAllResponse
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicAddData
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicBaseData
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicEditData
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicOriginData
import com.example.linteacher.databinding.FragmentLicenseBinding

import com.example.linteacher.util.Config
import com.example.linteacher.util.preference.LoginPreferences

class LicenseFragment : Fragment(),LicInterface.View {
    companion object {
        fun newInstance() = LicenseFragment()
    }

    private var _binding: FragmentLicenseBinding? = null
    private val binding get() = _binding!!
    private lateinit var loginPreferences: LoginPreferences
    //viewmodel
    private val factory = LicenseViewModelFactory(LicenseRepository())
    private val viewModel: LicenseViewModel by viewModels {
        factory
    }
    private lateinit var adapter: LicenseAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLicenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences = LoginPreferences(view.context)
        initRecycleView()
        viewModelObserveLst()
        binding.addLic.setOnClickListener {
            val list = adapter.getDataList() as ArrayList<LicBaseData>
            list.add(LicAddData())
            //list add????????????LicAddData,??????adapter
            adapter.setDataList(list)
        }
    }


    //???????????????fun(??????=??????list???viewType???LicOriginData)
    private fun viewModelObserveLst() {
        //git all lic by loginId
        viewModel.getList(loginPreferences.getTeacherId())
            .observe(viewLifecycleOwner, object : Observer<LicenseAllResponse> {
                override fun onChanged(t: LicenseAllResponse) {
                    //???????????? LicenseAllResponse->licOne???list?????????error
                    if (t.list.isEmpty()) {

                    } else {
                        //baseData =????????????
                        val valueLst = arrayListOf<LicBaseData>()
                        for (r: LicenseResponse in t.list) {
                            valueLst.add(
                                LicOriginData(
                                    licName = r.licName,
                                    licService = r.licService,
                                    licType = r.licType,
                                    licId = r.licId,
                                    public = r.public,
                                    )
                            )
                        }
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
        binding.licRecycleView.layoutManager = layoutManager
        val list = arrayListOf<LicBaseData>() //LicBaseData???????????????
        list.add(LicOriginData()) //LicOriginData???LicBaseData?????????
        adapter = LicenseAdapter(list, this)//?????????????????????:)
        _binding?.licRecycleView?.adapter = adapter
    }


    override fun onSaveClick(name: LicAddData, position: Int) {
        //request = adpter?????????LicAddData??????+loginId
        val request= LicPostRequest(
            loginId = loginPreferences.getTeacherId().toInt(),
            licName = name.licName,
            licNumber = name.licNumber.toInt(),
            licService = name.licService,
            licType = name.licType,
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

    override fun onEditSaveClick(name: LicEditData, position: Int) {
        //request =LicEditData??????+loginId
        val request=
            LicUpdateRequest(
                loginId = loginPreferences.getTeacherId().toInt(),
                licName = name.licName,
                licNumber = name.licNumber.toInt(),
                licService = name.licService,
                licType = name.licType,
                public = name.public,
                licId=name.licId
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


    override fun onEditClick(name: LicOriginData, position: Int) {
        //get lic by licId
        viewModel.getDataByExpNumber(name.licId.toString())
            .observe(viewLifecycleOwner,
                { t ->
                    //????????????LicOriginData  ????????????LicEditData
                    adapter.getDataList().removeAt(position)
                    (adapter.getDataList()  as ArrayList<LicBaseData>) .add(
                            //??????EditViewHolder,??????LicEditData??????????????????
                        position, LicEditData(
                            licName = t.list.licName,
                            licNumber = t.list.licNumber.toString(),
                            licService = t.list.licService,
                            licType = t.list.licType,
                            licId=t.list.licId,
                            public = t.list.public,

                            )
                    )
                    adapter.setOneData(adapter.getDataList(), position)
                })
    }

    override fun onEditCancelClick(position: Int, name: LicEditData) {
        //??????LicEditData ??????LicOriginData
        val data= LicOriginData(
            licName = name.licName,
            licService = name.licService,
            licType = name.licType,
            licId = name.licId,
            public = name.public,

            )
        adapter.getDataList().removeAt(position)
        val list=adapter.getDataList() as ArrayList<LicBaseData>
        list.add(position,data)
        adapter.setOneData(list,position)
    }

    override fun onChangeVisibleClick(r: LicOriginData, position: Int) {
        //request =LicEditData??????+loginId
        val request=
            AcademicChangeVisibleRequest(
                id = r.licId,
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