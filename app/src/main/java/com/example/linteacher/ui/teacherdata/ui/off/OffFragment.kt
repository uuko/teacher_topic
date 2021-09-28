package com.example.linteacher.ui.teacherdata.ui.off

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.teacherdata.off.OffGetAllResponse
import com.example.linteacher.api.pojo.teacherdata.off.OffGetResponse
import com.example.linteacher.api.pojo.teacherdata.off.OffPostRequest
import com.example.linteacher.api.pojo.teacherdata.off.OffUpdateRequest
import com.example.linteacher.api.pojo.teacherdata.off.ui.OffAddData
import com.example.linteacher.api.pojo.teacherdata.off.ui.OffBaseData
import com.example.linteacher.api.pojo.teacherdata.off.ui.OffEditData
import com.example.linteacher.api.pojo.teacherdata.off.ui.OffOriginData
import com.example.linteacher.databinding.FragmentSlideshowBinding
import com.example.linteacher.ui.teacherdata.ui.slideshow.*
import com.example.linteacher.util.BaseFragment
import com.example.linteacher.util.Config
import com.example.linteacher.util.preference.LoginPreferences

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OffFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OffFragment : BaseFragment(), OffInterface.View {
    private lateinit var offCampusViewModel: OffCampusViewModel
    private var _binding: FragmentSlideshowBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var loginPreferences: LoginPreferences
    private val factory = OffCampusViewModelFactory(OffCampusRepository())
    private val viewModel: OffCampusViewModel by viewModels {
        factory
    }
    private lateinit var adapter: OffAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences = LoginPreferences(view.context)
        initRecycleView()
        viewModelObserveLst()
        binding.addOff.setOnClickListener {
            val list = adapter.getDataList()
            list.add(OffAddData())
            adapter.setDataList(list)
        }
    }

    private fun initRecycleView() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        layoutManager.reverseLayout = false
        binding.offRecycleView.layoutManager = layoutManager
        val list = arrayListOf<OffBaseData>()
        list.add(OffOriginData())
        adapter = OffAdapter(list, this)
        _binding?.offRecycleView?.adapter = adapter
    }

    private fun viewModelObserveLst() {
        viewModel.getList(loginPreferences.getTeacherId())
            .observe(viewLifecycleOwner, object : Observer<OffGetAllResponse> {
                override fun onChanged(t: OffGetAllResponse) {

                    if (t.list.isEmpty()) {

                    } else {
                        val valueLst = arrayListOf<OffBaseData>()
                        for (r: OffGetResponse in t.list) {
                            valueLst.add(
                                OffOriginData(
                                    proId=r.proId,
                                    proVendor = r.proVendor,
                                    proNature = r.proNature
                                )
                            )
                        }
                        adapter.setDataList(valueLst)
                    }


                }

            })
    }

    override fun onSaveClick(item: OffAddData, position: Int) {
        val request= OffPostRequest(
            loginId = loginPreferences.getTeacherId().toInt(),
            proCaseName = item.proCaseName,
            proCaseNumber = item.proCaseNumber,
            proContent = item.proContent,
            proNature = item.proNature,
            proRebate = item.proRebate,
            proSign = item.proSign,
            proStartDate = item.proStartDate,
            proStopDate = item.proStopDate,
            proVendor = item.proVendor,
            public = false
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

    override fun onEditSaveClick(item: OffEditData, position: Int) {
        val request=
            OffUpdateRequest(
                proId = item.proId,
                proCaseName = item.proCaseName,
                proCaseNumber = item.proCaseNumber,
                proContent = item.proContent,
                proNature = item.proNature,
                proRebate = item.proRebate,
                proSign = item.proSign,
                proStartDate = item.proStartDate,
                proStopDate = item.proStopDate,
                proVendor = item.proVendor,
                public = false,
                loginId = loginPreferences.getTeacherId().toInt()
            )


            viewModel.updateList(request)
                .observe(viewLifecycleOwner,{
                        t->
                    if (t.result == Config.RESULT_OK){
                        viewModelObserveLst()
                    }
                })

    }

    override fun onEditClick(item: OffOriginData, position: Int) {
        viewModel.getDataByExpNumber(item.proId.toString())
            .observe(viewLifecycleOwner,
                { t ->
                    adapter.getDataList().removeAt(position)
                    adapter.getDataList().add(
                        position, OffEditData(
                            proVendor = t.list.proVendor,
                            proNature = t.list.proNature,
                            proId = t.list.proId,
                            proSign = t.list.proSign,
                            proCaseNumber = t.list.proCaseNumber,
                            proCaseName = t.list.proCaseName,
                            proContent = t.list.proContent,
                            proStartDate = t.list.proStartDate,
                            proStopDate = t.list.proStopDate,
                            proRebate = t.list.proRebate,
                        )
                    )
                    adapter.setOneData(adapter.getDataList(), position)
                })
    }

    override fun onEditCancelClick(position: Int, r: OffEditData) {
        val data= OffOriginData(
            proId=r.proId,
            proVendor = r.proVendor,
            proNature = r.proNature
        )
        adapter.getDataList().removeAt(position)
        val list=adapter.getDataList()
        list.add(position,data)
        adapter.setOneData(list,position)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}