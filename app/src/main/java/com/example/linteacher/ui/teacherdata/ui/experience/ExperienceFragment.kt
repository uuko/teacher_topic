package com.example.linteacher.ui.teacherdata.ui.experience

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.teacherdata.*
import com.example.linteacher.api.pojo.teacherdata.exp.ExpAddRequest
import com.example.linteacher.api.pojo.teacherdata.exp.ExpGetResponse
import com.example.linteacher.api.pojo.teacherdata.exp.ExpUpdateRequest
import com.example.linteacher.databinding.FragmentGalleryBinding
import com.example.linteacher.util.Config
import com.example.linteacher.util.preference.LoginPreferences


class ExperienceFragment : Fragment() {

    private lateinit var expViewModel: ExpViewModel
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private val factory = ExpViewModelFactory(ExpRepository())
    private val viewModel: ExpViewModel by viewModels {
        factory
    }
    private lateinit var   loginPreferences:LoginPreferences
    private lateinit var adapter:ExpAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences=LoginPreferences(view.context)
        initRecycleView()
        viewModelObserveExpLst()

        binding.addExp.setOnClickListener {
            val list= adapter.getDataList()
            list.add(ExpAddData())
            adapter.setDataList(list)
        }
    }

    private fun viewModelObserveExpLst() {
        viewModel.getExpList(loginPreferences.getTeacherId())
            .observe(viewLifecycleOwner,object :Observer<ExpGetAllResponse>{
                override fun onChanged(t: ExpGetAllResponse) {

                    if (t.list.isEmpty()){

                    }
                    else{
                        val valueLst = arrayListOf<ExpBaseData>()
                        for (r:ExpGetResponse in t.list){
                            valueLst.add(
                                ExpOriginData(
                                    r.expNumber,
                                    company = r.expMechanismName,
                                    job = r.expJobtitle,
                                    startDate = r.expStartDate,
                                    endDate = r.expStopDate,
                                    coopAgency = r.expMechanismSort,
                                    expType = r.expCategory
                                ))
                        }
                        adapter.setDataList(valueLst)
                    }


                }

            })
    }

    private fun initRecycleView() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation= LinearLayoutManager.VERTICAL
        layoutManager.reverseLayout=false
        binding.expRecycleView?.layoutManager =layoutManager
        val list= arrayListOf<ExpBaseData>()
        list.add(ExpOriginData())
        adapter= ExpAdapter(list,object :OnAddClickListener{
            override fun onSaveClick(data: ExpAddData, position: Int) {
                val request=ExpAddRequest(
                    expCategory = data.expType,
                    expJobtitle=data.job,
                    expMechanismName=data.company,
                    expMechanismSort=data.coopAgency,
                    expPFtime = data.isPartTime,
                    expStartDate = data.startDate,
                    expStopDate = data.endDate,
                    public = data.isPublic,
                    loginId = loginPreferences.getTeacherId().toInt()
                )

                viewModel.postExpList(request)
                    .observe(viewLifecycleOwner,object :Observer<UnitResponse>{
                        override fun onChanged(t: UnitResponse) {
                            if (t.result != Config.RESULT_OK){

                            }
                            else{
                                viewModelObserveExpLst()
                            }
                        }

                    })
            }

            override fun onCancelClick( position: Int) {
                adapter.getDataList().removeAt(position)
                adapter.setDataList( adapter.getDataList())
            }

            override fun onDeleteClick(expNumber: Int, position: Int) {
                viewModel.deleteExp(expNumber)
                    .observe(viewLifecycleOwner,{
                        if (it.result == Config.RESULT_OK){
                            adapter.getDataList().removeAt(position)
                            adapter.setDataList( adapter.getDataList())
                        }
                    })
            }

            override fun onEditSaveClick(data: ExpEditData, position: Int) {
                val request=ExpUpdateRequest(
                    expNumber=data.expNumber,
                    expCategory = data.expType,
                    expJobtitle=data.job,
                    expMechanismName=data.company,
                    expMechanismSort=data.coopAgency,
                    expPFtime = data.isPartTime,
                    expStartDate = data.startDate,
                    expStopDate = data.endDate,
                    public = data.isPublic,
                    loginId = loginPreferences.getTeacherId().toInt()
                )
                viewModel.updateExpList(request)
                    .observe(viewLifecycleOwner,{
                        t->
                         if (t.result == Config.RESULT_OK){
                             viewModelObserveExpLst()
                         }
                    })
            }

            override fun onEditClick(item: ExpOriginData, position: Int) {

               viewModel.getExpDataByExpNumber(item.expNumber.toString())
                   .observe(viewLifecycleOwner,
                       { t ->
                           adapter.getDataList().removeAt(position)
                           adapter.getDataList().add(position, ExpEditData(
                               expNumber=t.expNumber!!,
                               company=t.expMechanismName!!,
                               job = t.expJobtitle!!,
                               startDate=t.expStartDate!!,
                               endDate=t.expStopDate!!,
                               expType=t.expCategory!!,
                               coopAgency=t.expMechanismSort!!,
                               isPublic=t.public!!,
                               isPartTime=t.expPFtime!!
                           )
                           )
                           adapter.setOneData(adapter.getDataList(),position)
                       })
            }

            override fun onEditCancelClick(position: Int,name: ExpEditData) {
                val data=ExpOriginData(
                    name.expNumber,
                    company = name.company,
                    job = name.job,
                    startDate = name.startDate,
                    endDate = name.endDate,
                    coopAgency = name.coopAgency,
                    expType = name.expType
                )
                adapter.getDataList().removeAt(position)
                val list=adapter.getDataList()
                list.add(position,data)
                adapter.setOneData(list,position)
            }

        })
        _binding?.expRecycleView?.adapter=adapter
    }
    interface OnAddClickListener {
        fun onSaveClick(name: ExpAddData, position: Int)
        fun onCancelClick( position: Int)
        fun onDeleteClick(expNumber: Int, position: Int)
        fun onEditSaveClick(name: ExpEditData, position: Int)
        fun onEditClick(name: ExpOriginData, position: Int)
        fun onEditCancelClick(position: Int,name: ExpEditData)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}