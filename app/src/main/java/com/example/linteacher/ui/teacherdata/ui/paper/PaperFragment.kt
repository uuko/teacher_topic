package com.example.linteacher.ui.teacherdata.ui.paper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.teacherdata.license.LicPostRequest
import com.example.linteacher.api.pojo.teacherdata.license.LicUpdateRequest
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicBaseData
import com.example.linteacher.api.pojo.teacherdata.paper.*
import com.example.linteacher.api.pojo.teacherdata.paper.ui.PaperAddData
import com.example.linteacher.api.pojo.teacherdata.paper.ui.PaperBaseData
import com.example.linteacher.api.pojo.teacherdata.paper.ui.PaperEditData
import com.example.linteacher.api.pojo.teacherdata.paper.ui.PaperOriginData
import com.example.linteacher.databinding.FragmentPaperBinding
import com.example.linteacher.util.Config
import com.example.linteacher.util.preference.LoginPreferences

class PaperFragment : Fragment(),PaperInterface.View {

    companion object {
        fun newInstance() = PaperFragment()
    }

    private val factory = PaperViewModelFactory(PaperRepository())
    private val viewModel: PaperViewModel by viewModels {
        factory
    }
    private var _binding: FragmentPaperBinding? = null
    private lateinit var adapter: PaperAdapter
    private lateinit var loginPreferences:LoginPreferences
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaperBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences= LoginPreferences(view.context)
        initRecycleView()
        viewModelObserveLst()
        binding.addLic.setOnClickListener {
            val list = adapter.getDataList() as ArrayList<PaperBaseData>
            list.add(PaperAddData())
            adapter.setDataList(list)
        }
    }

    private fun viewModelObserveLst() {
        viewModel.getList(loginPreferences.getTeacherId())
            .observe(viewLifecycleOwner, object : Observer<PaperAllResponse> {
                override fun onChanged(t: PaperAllResponse) {

                    if (t.list.isEmpty()) {

                    } else {
                        val valueLst = arrayListOf<PaperBaseData>()
                        for (r: PaperResponse in t.list) {
                            valueLst.add(
                                PaperOriginData(
                                    theId = r.theId,
                                    themain_thesisName = r.themain_thesisName,
                                    theAuthor = r.theAuthor,
                                    thePublishYear = r.thePublishYear,
                                )

                            )
                        }
                        adapter.setDataList(valueLst)
                    }


                }

            })
    }

    private fun initRecycleView() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        layoutManager.reverseLayout = false
        binding.paperRecycleView.layoutManager = layoutManager
        val list = arrayListOf<PaperBaseData>()
        list.add(PaperOriginData())

        adapter = PaperAdapter(list, this)
        _binding?.paperRecycleView?.adapter = adapter
    }

    override fun onSaveClick(name: PaperAddData, position: Int) {
        val request= PaperPostRequest(
            loginId = loginPreferences.getTeacherId().toInt(),
            theAuthor = name.theAuthor,
            theCollCategory = name.theCollCategory,
            theCorreAuthor = name.theCorreAuthor,
            theCoupons = name.theCoupons,
            theProject = name.theProject,
            thePublishMonth = name.thePublishMonth.toInt(),
            thePublishType = name.thePublishType,
            thePublishYear = name.thePublishYear.toInt(),
            thePublishingcountry = name.thePublishingcountry,
            thePubmain_licatinNumber = name.thePubmain_licatinNumber,
            thePubmain_licationName = name.thePubmain_licationName,
            theReviewer = name.theReviewer,
            theTransCooperation = name.theTransCooperation,
            themain_thesisName = name.themain_thesisName,
        )


        viewModel.postData(request)
            .observe(viewLifecycleOwner,object : Observer<UnitResponse> {
                override fun onChanged(t: UnitResponse) {
                    if (t.result != Config.RESULT_OK){

                    }
                    else{
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
        viewModel.delete(expNumber)
            .observe(viewLifecycleOwner, {
                if (it.result == Config.RESULT_OK) {
                    adapter.getDataList().removeAt(position)
                    adapter.setDataList(adapter.getDataList())
                }
            })
    }

    override fun onEditSaveClick(name: PaperEditData, position: Int) {
        val request=
            PaperUpdateRequest(
                theId=name.theId,
                loginId = loginPreferences.getTeacherId().toInt(),
                theAuthor = name.theAuthor,
                theCollCategory = name.theCollCategory,
                theCorreAuthor = name.theCorreAuthor,
                theCoupons = name.theCoupons,
                theProject = name.theProject,
                thePublishMonth = name.thePublishMonth.toInt(),
                thePublishType = name.thePublishType,
                thePublishYear = name.thePublishYear.toInt(),
                thePublishingcountry = name.thePublishingcountry,
                thePubmain_licatinNumber = name.thePubmain_licatinNumber,
                thePubmain_licationName = name.thePubmain_licationName,
                theReviewer = name.theReviewer,
                theTransCooperation = name.theTransCooperation,
                themain_thesisName = name.themain_thesisName,
            )

        viewModel.updateList(request)
            .observe(viewLifecycleOwner,{
                    t->
                if (t.result == Config.RESULT_OK){
                    viewModelObserveLst()
                }
            })
    }

    override fun onEditClick(name: PaperOriginData, position: Int) {
        viewModel.getDataByExpNumber(name.theId.toString())
            .observe(viewLifecycleOwner,
                { t ->
                    val items=t.list
                    adapter.getDataList().removeAt(position)
                    (adapter.getDataList()  as ArrayList<PaperBaseData>) .add(
                        position, PaperEditData(
                            theId = items.theId,
                            themain_thesisName = items.themain_thesisName,
                            theAuthor = items.theAuthor,
                            theProject = items.theProject,
                            theCorreAuthor = items.theCorreAuthor,
                            theCollCategory = items.theCollCategory,
                            thePubmain_licationName = items.thePubmain_licationName,
                            theCoupons = items.theCoupons,
                            thePubmain_licatinNumber = items.thePubmain_licationName,
                            thePublishYear = items.thePublishYear,
                            thePublishMonth = items.thePublishMonth,
                            thePublishType = items.thePublishType,
                            theReviewer = items.theReviewer,
                            theTransCooperation = items.theTransCooperation,
                            thePublishingcountry = items.thePublishingcountry,
                        )
                    )
                    adapter.setOneData(adapter.getDataList(), position)
                })
    }

    override fun onEditCancelClick(position: Int, name: PaperEditData) {
        val data= PaperOriginData(
            theId = name.theId,
            themain_thesisName = name.themain_thesisName,
            theAuthor = name.theAuthor,
            thePublishYear = name.thePublishYear,
        )
        adapter.getDataList().removeAt(position)
        val list=adapter.getDataList() as ArrayList<PaperBaseData>
        list.add(position,data)
        adapter.setOneData(list,position)
    }

}