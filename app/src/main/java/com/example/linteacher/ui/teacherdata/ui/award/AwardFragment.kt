package com.example.linteacher.ui.teacherdata.ui.award

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
import com.example.linteacher.api.pojo.teacherdata.adamic.AcademicChangeVisibleRequest
import com.example.linteacher.api.pojo.teacherdata.adamic.data.AdemicEventBaseData
import com.example.linteacher.api.pojo.teacherdata.award.AwardAllResponse
import com.example.linteacher.api.pojo.teacherdata.award.AwardPostRequest
import com.example.linteacher.api.pojo.teacherdata.award.AwardResponse
import com.example.linteacher.api.pojo.teacherdata.award.AwardUpdateRequest
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicAddData
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicBaseData
import com.example.linteacher.databinding.FragmentAwardBinding
import com.example.linteacher.api.pojo.teacherdata.award.data.AwardBaseData
import com.example.linteacher.api.pojo.teacherdata.license.LicPostRequest
import com.example.linteacher.api.pojo.teacherdata.license.LicUpdateRequest
import com.example.linteacher.api.pojo.teacherdata.license.LicenseResponse
import com.example.linteacher.api.pojo.teacherdata.license.all.LicenseAllResponse
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicEditData
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicOriginData
import com.example.linteacher.ui.teacherdata.ui.license.LicInterface
import com.example.linteacher.ui.teacherdata.ui.license.LicenseAdapter
import com.example.linteacher.ui.teacherdata.ui.license.LicenseViewModel
import com.example.linteacher.util.Config
import com.example.linteacher.util.preference.LoginPreferences

class AwardFragment : Fragment(), AwardInterface.View {

    companion object {
        fun newInstance() = AwardFragment()
    }

    private var _binding: FragmentAwardBinding? = null
    private val binding get() = _binding!!
    private lateinit var loginPreferences: LoginPreferences
    //viewmodel
    private val factory = AwardViewModelFactory(AwardRepository())
    private val viewModel: AwardViewModel by viewModels {
        factory
    }
    private lateinit var adapter: AwardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAwardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences = LoginPreferences(view.context)
        initRecycleView()
        viewModelObserveLst()
        binding.addAward.setOnClickListener {
            val list = adapter.getDataList() as ArrayList<AwardBaseData>
            list.add(AwardBaseData(itemType = Config.ADD_VIEW_TYPE))
            //list add????????????LicAddData,??????adapter
            adapter.setDataList(list)
        }
    }

    private fun viewModelObserveLst() {
        //git all lic by loginId
        viewModel.getList(loginPreferences.getTeacherId())
                .observe(viewLifecycleOwner, object : Observer<AwardAllResponse> {
                    override fun onChanged(t: AwardAllResponse) {
                        //???????????? LicenseAllResponse->licOne???list?????????error
                        if (t.list.isEmpty()) {

                        } else {
                            //baseData =????????????
                            val valueLst = arrayListOf<AwardBaseData>()
                            for (r: AwardResponse in t.list) {
                                valueLst.add(
                                        AwardBaseData(
                                                awaPlan = r.awaPlan,
                                                awaName = r.awaName,
                                                awaMechanismName = r.awaMechanismName,
                                                awaSort = r.awaSort,
                                                awaCampus = r.awaCampus,
                                                awaCountry = r.awaCountry,
                                                awaDate = r.awaDate,
                                                awaId = r.awaId,
                                                itemType = Config.ORIGIN_VIEW_TYPE,
                                            public = r.public,
                                        )
                                )

                            }
                            Log.d("theid", "onChanged: "+ t.list[0].awaId)
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
        binding.awardRecycleView.layoutManager = layoutManager
        val list = arrayListOf<AwardBaseData>() //LicBaseData???????????????
        list.add(AwardBaseData()) //LicOriginData???LicBaseData?????????
        Log.d("fuckckc", "hh: "+list.size)

        adapter = AwardAdapter(list, this)//?????????????????????:)
        _binding?.awardRecycleView?.adapter = adapter
    }

    override fun onSaveClick(name: AwardBaseData, position: Int) {
        //request = adpter?????????LicAddData??????+loginId

        val request= AwardPostRequest(
                awaPlan = name.awaPlan,
                awaName = name.awaName,
                awaMechanismName = name.awaMechanismName,
                awaSort = name.awaSort,
                awaCampus = name.awaCampus,
                awaCountry = name.awaCountry,
                awaDate = name.awaDate,
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

    override fun onEditSaveClick(name: AwardBaseData, position: Int) {
        //request =LicEditData??????+loginId
        val request=
                AwardUpdateRequest(
                        awaPlan = name.awaPlan,
                        awaName = name.awaName,
                        awaMechanismName = name.awaMechanismName,
                        awaSort = name.awaSort,
                        awaCampus = name.awaCampus,
                        awaCountry = name.awaCountry,
                        awaDate = name.awaDate,
                        loginId = loginPreferences.getTeacherId().toInt(),
                        public = name.public,
                    awaId = name.awaId

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
                            (adapter.getDataList()  as ArrayList<AwardBaseData>) .add(
                                    //??????EditViewHolder,??????LicEditData??????????????????
                                    position, AwardBaseData(
                                    awaPlan = t.list.awaPlan,
                                    awaName = t.list.awaName,
                                    awaMechanismName = t.list.awaMechanismName,
                                    awaSort = t.list.awaSort,
                                    awaCampus = t.list.awaCampus,
                                    awaCountry = t.list.awaCountry,
                                    awaDate = t.list.awaDate,
                                    awaId=t.list.awaId,
                                    itemType = Config.EdIT_VIEW_TYPE,
                                    public = t.list.public,

                            )
                            )
                            adapter.setOneData(adapter.getDataList(), position)
                        })
    }

    override fun onEditCancelClick(position: Int, r: AwardBaseData) {
        //??????LicEditData ??????LicOriginData
        var data :AwardBaseData = AwardBaseData()
        data = r
        data.itemType = Config.ORIGIN_VIEW_TYPE
        adapter.getDataList().removeAt(position)
        val list=adapter.getDataList() as ArrayList<AwardBaseData>
        list.add(position,data)
        adapter.setOneData(list,position)
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(AwardViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

    override fun onChangeVisibleClick(r: AwardBaseData, position: Int) {
        //request =LicEditData??????+loginId
        val request=
            AcademicChangeVisibleRequest(
                id = r.awaId,
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