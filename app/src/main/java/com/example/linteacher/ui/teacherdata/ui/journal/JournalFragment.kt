package com.example.linteacher.ui.teacherdata.ui.journal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.linteacher.R
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.teacherdata.dis.*
import com.example.linteacher.api.pojo.teacherdata.dis.ui.DisAddData
import com.example.linteacher.api.pojo.teacherdata.dis.ui.DisBaseData
import com.example.linteacher.api.pojo.teacherdata.dis.ui.DisEditData
import com.example.linteacher.api.pojo.teacherdata.dis.ui.DisOriginData
import com.example.linteacher.api.pojo.teacherdata.license.LicPostRequest
import com.example.linteacher.api.pojo.teacherdata.license.LicUpdateRequest
import com.example.linteacher.api.pojo.teacherdata.license.LicenseResponse
import com.example.linteacher.api.pojo.teacherdata.license.all.LicenseAllResponse
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicAddData
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicBaseData
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicEditData
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicOriginData
import com.example.linteacher.databinding.FragmentJournalBinding
import com.example.linteacher.databinding.FragmentLicenseBinding
import com.example.linteacher.ui.teacherdata.ui.experience.ExpRepository
import com.example.linteacher.ui.teacherdata.ui.experience.ExpViewModel
import com.example.linteacher.ui.teacherdata.ui.experience.ExpViewModelFactory
import com.example.linteacher.ui.teacherdata.ui.license.LicenseAdapter
import com.example.linteacher.util.Config
import com.example.linteacher.util.preference.LoginPreferences


class JournalFragment : Fragment(), JournalInterface.View {
    private val factory = JournalViewModelFactory(JournalRepository())
    private val viewModel: JournalViewModel by viewModels {
        factory
    }
    private lateinit var loginPreferences: LoginPreferences
    private var _binding: FragmentJournalBinding? = null
    private lateinit var adapter: JournalAdapter
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJournalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences = LoginPreferences(view.context)
        initRecycleView()
        viewModelObserveLst()
        binding.addDis.setOnClickListener {
            val list = adapter.getDataList() as ArrayList<DisBaseData>
            list.add(DisAddData())
            adapter.setDataList(list)
        }
    }

    private fun initRecycleView() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        layoutManager.reverseLayout = false
        binding.disRecycleView.layoutManager = layoutManager
        val list = arrayListOf<DisBaseData>()
        list.add(DisOriginData())
        adapter = JournalAdapter(list, this)
        _binding?.disRecycleView?.adapter = adapter
    }

    private fun viewModelObserveLst() {
        viewModel.getDisList(loginPreferences.getTeacherId())
            .observe(viewLifecycleOwner, object : Observer<DisGetAllResponse> {
                override fun onChanged(t: DisGetAllResponse) {

                    if (t.list.isEmpty()) {

                    } else {
                        val valueLst = arrayListOf<DisBaseData>()
                        for (r: DisGetResponse in t.list) {
                            valueLst.add(
                                DisOriginData(
                                    disId = r.disId,
                                    dismain_thesisName = r.dismainThesisName,
                                    disSeminarName = r.disSeminarName,
                                    disFD = r.disFD,
                                    disED = r.disED,
                                )
                            )
                        }
                        adapter.setDataList(valueLst)
                    }


                }

            })
    }

    override fun onSaveClick(name: DisAddData, position: Int) {
        val request = DisPostRequest(
            loginId = loginPreferences.getTeacherId().toInt(),
            disAuthor = name.disAuthor,
            disCollection = "",
            disCorreAuthor = name.disCorreAuthor,
            disED = name.disED,
            disFD = name.disFD,
            disHostCity = name.disHostCity,
            disHostCountry = name.disHostCountry,
            disIsCountry = "",
            disProject = name.disProject,
            disPublishY = name.disPublishY,
            disReviewer = name.disReviewer,
            disSeminarName = name.disSeminarName,
            dismainThesisName = name.dismain_thesisName,
            main_disYear = "",
        )


        viewModel.postDisData(request)
            .observe(viewLifecycleOwner, object : Observer<UnitResponse> {
                override fun onChanged(t: UnitResponse) {
                    if (t.result != Config.RESULT_OK) {

                    } else {
                        viewModelObserveLst()
                    }
                }

            })
    }

    override fun onCancelClick(position: Int) {
        adapter.getDataList().removeAt(position)
        adapter.setDataList(adapter.getDataList())
    }

    override fun onDeleteClick(expNumber: Int, position: Int) {

        viewModel.deleteDisData(DisDelRequest(expNumber))
            .observe(viewLifecycleOwner, {
                if (it.result == Config.RESULT_OK) {
                    adapter.getDataList().removeAt(position)
                    adapter.setDataList(adapter.getDataList())
                }
            })
    }

    override fun onEditSaveClick(name: DisEditData, position: Int) {
        val request = DisUpdateRequest(
            loginId = loginPreferences.getTeacherId().toInt(),
            disId = name.disId,
            disAuthor = name.disAuthor,
            disCollection = "",
            disCorreAuthor = name.disCorreAuthor,
            disED = name.disED,
            disFD = name.disFD,
            disIsCountry = "",
            disHostCity = name.disHostCity,
            disHostCountry = name.disHostCountry,
            disProject = name.disProject,
            disPublishY = name.disPublishY,
            disReviewer = name.disReviewer,
            disSeminarName = name.disSeminarName,
            dismainThesisName = name.dismain_thesisName,
            main_disYear = "",
            public = false
        )


        viewModel.updateDisData(request)
            .observe(viewLifecycleOwner, { t ->
                if (t.result == Config.RESULT_OK) {
                    viewModelObserveLst()
                }
            })
    }

    override fun onEditClick(name: DisOriginData, position: Int) {
        viewModel.getDisListById(name.disId.toString())
            .observe(viewLifecycleOwner,
                { items ->
                    adapter.getDataList().removeAt(position)
                    (adapter.getDataList() as ArrayList<DisBaseData>).add(
                        position, DisEditData(
                            dismain_thesisName = items.dismainThesisName,
                            disSeminarName = items.disSeminarName,
                            disFD = items.disFD,
                            disED = items.disED,
                            disId = items.disId,
                            disProject = items.disProject,
                            disCorreAuthor = items.disCorreAuthor,
                            disReviewer = items.disReviewer,
                            disHostCity = items.disHostCity,
                            disHostCountry = items.disHostCountry,
                            disPublishY = items.disPublishY,
                            disAuthor = items.disAuthor
                        )
                    )
                    adapter.setOneData(adapter.getDataList(), position)
                })
    }

    override fun onEditCancelClick(position: Int, r: DisEditData) {
        val data = DisOriginData(
            disId = r.disId,
            dismain_thesisName = r.dismain_thesisName,
            disSeminarName = r.disSeminarName,
            disFD = r.disFD,
            disED = r.disED,
        )
        adapter.getDataList().removeAt(position)
        val list = adapter.getDataList() as ArrayList<DisBaseData>
        list.add(position, data)
        adapter.setOneData(list, position)
    }
}