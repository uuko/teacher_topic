package com.example.linteacher.ui.teacherdata.ui.adamic

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.teacherdata.adamic.AdemicEventAllResponse
import com.example.linteacher.api.pojo.teacherdata.adamic.AcademicPostRequest
import com.example.linteacher.api.pojo.teacherdata.adamic.AdemicEventResponse
import com.example.linteacher.api.pojo.teacherdata.adamic.data.AdemicEventBaseData
import com.example.linteacher.databinding.FragmentAdemicEventBinding
import com.example.linteacher.util.Config
import com.example.linteacher.util.preference.LoginPreferences


class AdemicEventFragment :  Fragment(), AdemicEventInterface.View {

    companion object {
    fun newInstance() = AdemicEventFragment()
}

    private var _binding: FragmentAdemicEventBinding? = null
    private val binding get() = _binding!!
    private lateinit var loginPreferences: LoginPreferences
    //viewmodel
    private val factory = AdemicEventViewModelFactory(AdemicEventRepository())
    private val viewModel: AdemicEventViewModel by viewModels {
        factory
    }
    private lateinit var adapter: AdemicEventAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdemicEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences = LoginPreferences(view.context)
        initRecycleView()
        viewModelObserveLst()
        binding.addAdemicEvent.setOnClickListener {
            val list = adapter.getDataList() as ArrayList<AdemicEventBaseData>
            list.add(AdemicEventBaseData(itemType = Config.ADD_VIEW_TYPE))
            //list add一筆空的LicAddData,傳入adapter
            adapter.setDataList(list)
        }
    }

    private fun viewModelObserveLst() {
        //git all lic by loginId
        viewModel.getList(loginPreferences.getTeacherId())
                .observe(viewLifecycleOwner, object : Observer<AdemicEventAllResponse> {
                    override fun onChanged(t: AdemicEventAllResponse) {
                        //觀察對象 LicenseAllResponse->licOne的list和是否error
                        if (t.list.isEmpty()) {

                        } else {
                            //baseData =初始模底
                            val valueLst = arrayListOf<AdemicEventBaseData>()
                            for (r: AdemicEventResponse in t.list) {
                                valueLst.add(
                                        AdemicEventBaseData(
                                                eveName  = r.eveName ,
                                                eveOrganizer = r.eveOrganizer,
                                                eveHours  = r.eveHours,
                                                eveCategory = r.eveCategory,
                                                eveSort = r.eveSort,
                                                eveLocation = r.eveLocation,
                                                eveParticimainPation = r.eveParticimainPation,
                                                eveClassRelated = r.eveClassRelated,
                                                eveStratDate = r.eveStratDate,
                                                eveStopDate = r.eveStopDate,
                                                eveStudyCertificate = r.eveStudyCertificate,
                                                eveCertificateNumber = r.eveCertificateNumber,
                                                eveSchSubsidy = r.eveSchSubsidy,

                                                eveNumber = r.eveNumber,
                                                itemType = Config.ORIGIN_VIEW_TYPE
                                        )
                                )

                            }
                            Log.d("theid", "onChanged: "+ t.list[0].eveNumber)
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
        binding.AdemicEventRecycleView.layoutManager = layoutManager
        val list = arrayListOf<AdemicEventBaseData>() //LicBaseData為初始模底
        list.add(AdemicEventBaseData()) //LicOriginData是LicBaseData的小孩
        Log.d("fuckckc", "hh: "+list.size)

        adapter = AdemicEventAdapter(list, this)//放一個空得進去:)
        _binding?.AdemicEventRecycleView?.adapter = adapter
    }

    override fun onSaveClick(r: AdemicEventBaseData, position: Int) {
        //request = adpter傳入的LicAddData內容+loginId

        val request= AcademicPostRequest(
                eveName  = r.eveName ,
                eveOrganizer = r.eveOrganizer,
                eveHours  = r.eveHours,
                eveCategory = r.eveCategory,
                eveSort = r.eveSort,
                eveLocation = r.eveLocation,
                eveParticimainPation = r.eveParticimainPation,
                eveClassRelated = r.eveClassRelated,
                eveStratDate = r.eveStratDate,
                eveStopDate = r.eveStopDate,
                eveStudyCertificate = r.eveStudyCertificate,
                eveCertificateNumber = r.eveCertificateNumber,
                eveSchSubsidy = r.eveSchSubsidy,

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


    override fun onEditSaveClick(r: AdemicEventBaseData, position: Int) {
        //request =LicEditData內容+loginId
        val request=
                AcademicPostRequest(
                        eveName  = r.eveName ,
                        eveOrganizer = r.eveOrganizer,
                        eveHours  = r.eveHours,
                        eveCategory = r.eveCategory,
                        eveSort = r.eveSort,
                        eveLocation = r.eveLocation,
                        eveParticimainPation = r.eveParticimainPation,
                        eveClassRelated = r.eveClassRelated,
                        eveStratDate = r.eveStratDate,
                        eveStopDate = r.eveStopDate,
                        eveStudyCertificate = r.eveStudyCertificate,
                        eveCertificateNumber = r.eveCertificateNumber,
                        eveSchSubsidy = r.eveSchSubsidy,




                        loginId = loginPreferences.getTeacherId().toInt(),
                        eveNumber = r.eveNumber

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
                            (adapter.getDataList()  as ArrayList<AdemicEventBaseData>) .add(
                                    //觸發EditViewHolder,這筆LicEditData會有新的樣貌
                                    position, AdemicEventBaseData(
                                    eveName  = t.list.eveName ,
                                    eveOrganizer = t.list.eveOrganizer,
                                    eveHours  = t.list.eveHours,
                                    eveCategory = t.list.eveCategory,
                                    eveSort = t.list.eveSort,
                                    eveLocation = t.list.eveLocation,
                                    eveParticimainPation = t.list.eveParticimainPation,
                                    eveClassRelated = t.list.eveClassRelated,
                                    eveStratDate = t.list.eveStratDate,
                                    eveStopDate = t.list.eveStopDate,
                                    eveStudyCertificate = t.list.eveStudyCertificate,
                                    eveCertificateNumber = t.list.eveCertificateNumber,
                                    eveSchSubsidy = t.list.eveSchSubsidy,

                                    eveNumber=t.list.eveNumber,
                                    itemType = Config.EdIT_VIEW_TYPE

                            )
                            )
                            adapter.setOneData(adapter.getDataList(), position)
                        })
    }



    override fun onEditCancelClick(position: Int, r: AdemicEventBaseData) {
        //移除LicEditData 新增LicOriginData
        var data : AdemicEventBaseData = AdemicEventBaseData()
        data = r
        data.itemType = Config.ORIGIN_VIEW_TYPE
        adapter.getDataList().removeAt(position)
        val list=adapter.getDataList() as ArrayList<AdemicEventBaseData>
        list.add(position,data)
        adapter.setOneData(list,position)
    }




}