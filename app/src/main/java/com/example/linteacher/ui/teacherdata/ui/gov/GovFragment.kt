package com.example.linteacher.ui.teacherdata.ui.gov

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
import com.example.linteacher.api.pojo.teacherdata.gov.GovAllResponse
import com.example.linteacher.api.pojo.teacherdata.gov.GovPostRequest
import com.example.linteacher.api.pojo.teacherdata.gov.GovResponse
import com.example.linteacher.api.pojo.teacherdata.gov.GovUpdateRequest
import com.example.linteacher.api.pojo.teacherdata.gov.data.GovBaseData
import com.example.linteacher.databinding.FragmentGovBinding
import com.example.linteacher.ui.teacherdata.ui.gov.*
import com.example.linteacher.util.Config
import com.example.linteacher.util.preference.LoginPreferences

class GovFragment: Fragment(), GovInterface.View {

    companion object {
        fun newInstance() = GovFragment()
    }

    private var _binding: FragmentGovBinding? = null
    private val binding get() = _binding!!
    private lateinit var loginPreferences: LoginPreferences
    //viewmodel
    private val factory = GovViewModelFactory(GovRepository())
    private val viewModel: GovViewModel by viewModels {
        factory
    }
    private lateinit var adapter: GovAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGovBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences = LoginPreferences(view.context)
        initRecycleView()
        viewModelObserveLst()
        binding.addGov.setOnClickListener {
            val list = adapter.getDataList() as ArrayList<GovBaseData>
            list.add(GovBaseData(itemType = Config.ADD_VIEW_TYPE))
            //list add一筆空的LicAddData,傳入adapter
            adapter.setDataList(list)
        }
    }

    private fun viewModelObserveLst() {
        //git all lic by loginId
        viewModel.getList(loginPreferences.getTeacherId())
                .observe(viewLifecycleOwner, object : Observer<GovAllResponse> {
                    override fun onChanged(t: GovAllResponse) {
                        //觀察對象 LicenseAllResponse->licOne的list和是否error
                        if (t.list.isEmpty()) {

                        } else {
                            //baseData =初始模底
                            val valueLst = arrayListOf<GovBaseData>()
                            for (r: GovResponse in t.list) {
                                valueLst.add(
                                        GovBaseData(
                                                govProjectName = r.govProjectName,
                                                govProbjectNumber = r.govProbjectNumber,
                                                govProbjectType = r.govProbjectType,
                                                govProjectType = r.govProjectType,
                                                govProjectNature = r.govProjectNature,
                                                govFD = r.govFD,
                                                govED = r.govED,
                                                govJobType = r.govJobType,
                                                govMoneyState = r.govMoneyState,
                                                govMainfund = r.govMainfund,
                                                govUnitName = r.govUnitName,
                                                govSecAund = r.govSecAund,
                                                govBenefitDepartment = r.govBenefitDepartment,
                                                govComUnit = r.govComUnit,
                                                govTeamworkUnit = r.govTeamworkUnit,
                                                govProjectAmount = r.govProjectAmount,
                                                govmainGovAmount = r.govmainGovAmount,
                                                govEntAmount = r.govEntAmount,
                                                govOthAmount = r.govOthAmount,
                                                govSchAmount = r.govSchAmount,
                                                govOthIn = r.govOthIn,
                                                govToOth = r.govToOth,


                                                govId = r.govId,
                                                itemType = Config.ORIGIN_VIEW_TYPE
                                        )
                                )

                            }
                            Log.d("theid", "onChanged: "+ t.list[0].govId)
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
        binding.GovRecycleView.layoutManager = layoutManager
        val list = arrayListOf<GovBaseData>() //LicBaseData為初始模底
        list.add(GovBaseData()) //LicOriginData是LicBaseData的小孩
        Log.d("fuckckc", "hh: "+list.size)

        adapter = GovAdapter(list, this)//放一個空得進去:)
        _binding?.GovRecycleView?.adapter = adapter
    }

    override fun onSaveClick(r: GovBaseData, position: Int) {
        //request = adpter傳入的LicAddData內容+loginId

        val request= GovPostRequest(
                govProjectName = r.govProjectName,
                govProbjectNumber = r.govProbjectNumber,
                govProbjectType = r.govProbjectType,
                govProjectType = r.govProjectType,
                govProjectNature = r.govProjectNature,
                govFD = r.govFD,
                govED = r.govED,
                govJobType = r.govJobType,
                govMoneyState = r.govMoneyState,
                govMainfund = r.govMainfund,
                govUnitName = r.govUnitName,
                govSecAund = r.govSecAund,
                govBenefitDepartment = r.govBenefitDepartment,
                govComUnit = r.govComUnit,
                govTeamworkUnit = r.govTeamworkUnit,
                govProjectAmount = r.govProjectAmount,
                govmainGovAmount = r.govmainGovAmount,
                govEntAmount = r.govEntAmount,
                govOthAmount = r.govOthAmount,
                govSchAmount = r.govSchAmount,
                govOthIn = r.govOthIn,
                govToOth = r.govToOth,


                govId = r.govId,
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

    override fun onEditSaveClick(r: GovBaseData, position: Int) {
        //request =LicEditData內容+loginId
        val request=
                GovUpdateRequest(
                        govProjectName = r.govProjectName,
                        govProbjectNumber = r.govProbjectNumber,
                        govProbjectType = r.govProbjectType,
                        govProjectType = r.govProjectType,
                        govProjectNature = r.govProjectNature,
                        govFD = r.govFD,
                        govED = r.govED,
                        govJobType = r.govJobType,
                        govMoneyState = r.govMoneyState,
                        govMainfund = r.govMainfund,
                        govUnitName = r.govUnitName,
                        govSecAund = r.govSecAund,
                        govBenefitDepartment = r.govBenefitDepartment,
                        govComUnit = r.govComUnit,
                        govTeamworkUnit = r.govTeamworkUnit,
                        govProjectAmount = r.govProjectAmount,
                        govmainGovAmount = r.govmainGovAmount,
                        govEntAmount = r.govEntAmount,
                        govOthAmount = r.govOthAmount,
                        govSchAmount = r.govSchAmount,
                        govOthIn = r.govOthIn,
                        govToOth = r.govToOth,


                        govId = r.govId,
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
                            (adapter.getDataList()  as ArrayList<GovBaseData>) .add(
                                    //觸發EditViewHolder,這筆LicEditData會有新的樣貌
                                    position, GovBaseData(
                                    govProjectName = t.list.govProjectName,
                                    govProbjectNumber = t.list.govProbjectNumber,
                                    govProbjectType = t.list.govProbjectType,
                                    govProjectType = t.list.govProjectType,
                                    govProjectNature = t.list.govProjectNature,
                                    govFD = t.list.govFD,
                                    govED = t.list.govED,
                                    govJobType = t.list.govJobType,
                                    govMoneyState = t.list.govMoneyState,
                                    govMainfund = t.list.govMainfund,
                                    govUnitName = t.list.govUnitName,
                                    govSecAund = t.list.govSecAund,
                                    govBenefitDepartment = t.list.govBenefitDepartment,
                                    govComUnit = t.list.govComUnit,
                                    govTeamworkUnit = t.list.govTeamworkUnit,
                                    govProjectAmount = t.list.govProjectAmount,
                                    govmainGovAmount = t.list.govmainGovAmount,
                                    govEntAmount = t.list.govEntAmount,
                                    govOthAmount = t.list.govOthAmount,
                                    govSchAmount = t.list.govSchAmount,
                                    govOthIn = t.list.govOthIn,
                                    govToOth = t.list.govToOth,


                                    govId = t.list.govId,
                                    itemType = Config.EdIT_VIEW_TYPE

                            )
                            )
                            adapter.setOneData(adapter.getDataList(), position)
                        })
    }

    override fun onEditCancelClick(position: Int, r: GovBaseData) {
        //移除LicEditData 新增LicOriginData
        var data : GovBaseData = GovBaseData()
        data = r
        data.itemType = Config.ORIGIN_VIEW_TYPE
        adapter.getDataList().removeAt(position)
        val list=adapter.getDataList() as ArrayList<GovBaseData>
        list.add(position,data)
        adapter.setOneData(list,position)
    }


}