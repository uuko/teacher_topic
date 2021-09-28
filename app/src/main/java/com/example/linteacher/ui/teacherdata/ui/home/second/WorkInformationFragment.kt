package com.example.linteacher.ui.teacherdata.ui.home.second

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.example.linteacher.R
import com.example.linteacher.api.pojo.teacherdata.profile.TeacherProfileResponse
import com.example.linteacher.databinding.FragmentTeacherDetailBinding
import com.example.linteacher.databinding.FragmentWorkInformationBinding
import java.text.SimpleDateFormat
import java.util.*


class WorkInformationFragment : NestedBaseFragment() {

    private var _binding: FragmentWorkInformationBinding? = null
    private val binding get() = _binding!!
    private var response: TeacherProfileResponse = TeacherProfileResponse()
    private var isMainDepartmentInnerVisible = false

    //    tchKindView
    private var switchKindViewInnerVisible = false

    //    tchSceWhemainView
    private var switchSceWhereinViewVisible = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWorkInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun getSubmitData(): TeacherProfileResponse {
        val result = response
        if (binding.tchHureDate.text.isNotEmpty()) {
            result.tchHureDate = binding.tchHureDate.text.toString()
        }
        if (binding.tchKindDepartment.text.isNotEmpty()) {
            result.tchKindDepartment = binding.tchKindDepartment.text.toString()
        }
        if (binding.tchHureDate.text.isNotEmpty()) {
            result.tchFullPartPosition = binding.tchFullPartPosition.text.toString()
        }
        if (binding.tchAdminiJob.text.isNotEmpty()) {
            result.tchAdminiJob = binding.tchAdminiJob.text.toString()
        }
        if (binding.tchSecUnit.text.isNotEmpty()) {
            result.tchSecUnit = binding.tchSecUnit.text.toString()
        }
        if (binding.tchSchDate.text.isNotEmpty()) {
            result.tchSchDate = binding.tchSchDate.text.toString()
        }
        if (binding.tchOriginalUnit.text.isNotEmpty()) {
            result.tchOriginalUnit = binding.tchOriginalUnit.text.toString()
        }
        if (binding.tchResignDate.text.isNotEmpty()) {
            result.tchResignDate = binding.tchResignDate.text.toString()
        }
        if (binding.tchAppointDate.text.isNotEmpty()) {
            result.tchAppointDate = binding.tchAppointDate.text.toString()
        }
        if (binding.tchStopDate.text.isNotEmpty()) {
            result.tchStopDate = binding.tchStopDate.text.toString()
        }
        if (binding.tchReinstateDate.text.isNotEmpty()) {
            result.tchReinstateDate = binding.tchReinstateDate.text.toString()
        }

        if (binding.tchEstablishment.selectedItemPosition != -1) {
            result.tchEstablishment =
                getResponseSpinner(
                    R.array.tchEstablishment_array_en,
                    binding.tchEstablishment,
                )
        }

        if (binding.tchKind.selectedItemPosition != -1) {
            result.tchKind =
                getResponseSpinner(
                    R.array.tchKind_array_en,
                    binding.tchKind,
                )
        }
        if (binding.tchKindIndustry.selectedItemPosition != -1) {
            result.tchKindIndustry =
                getResponseSpinner(
                    R.array.tch_tchkindindustry_array_en,
                    binding.tchKindIndustry,
                )
        }

        if (binding.tchPartAdmini.selectedItemPosition != -1) {
            result.tchPartAdmini =
                getResponseSpinner(
                    R.array.tch_abor_en,
                    binding.tchPartAdmini,
                )
        }

        if (binding.tchMainDepartment.selectedItemPosition != -1) {
            result.tchMainDepartment =
                getResponseSpinner(
                    R.array.tchMainDepartment_array_en,
                    binding.tchMainDepartment,
                )
        }


        if (binding.tchCoeDepartment.selectedItemPosition != -1) {
            result.tchCoeDepartment =
                getResponseSpinner(
                    R.array.tchCoeDepartment_array_en,
                    binding.tchCoeDepartment,
                )
        }
        if (binding.tchState.selectedItemPosition != -1) {
            result.tchState =
                getResponseSpinner(
                    R.array.tch_tchstate_array_en,
                    binding.tchState,
                )
        }

        if (binding.tchSceWhemainTher.selectedItemPosition != -1) {
            result.tchSceWhemain_ther =
                getResponseSpinner(
                    R.array.tch_abor_en,
                    binding.tchSceWhemainTher,
                )
        }
        if (binding.tchScePurpose.selectedItemPosition != -1) {
            result.tchScePurpose =
                getResponseSpinner(
                    R.array.tch_tchscepurpose_array_en,
                    binding.tchScePurpose,
                )
        }
        return result
    }

    private fun getResponseSpinner(arrayEn: Int, spinner: Spinner): String {
        val array = context?.resources?.getStringArray(arrayEn)
        return array?.get(spinner.selectedItemPosition).toString()

    }

    override fun setResponse(response: TeacherProfileResponse) {
        this.response = response
        context?.let { init(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view.context)
        binding.tchMainDepartmentView.setOnClickListener {
            isMainDepartmentInnerVisible = !isMainDepartmentInnerVisible
            toggleView(
                R.id.tchMainDepartmentInnerView,
                isMainDepartmentInnerVisible,
                binding.tchMainDepartmentInnerView
            )
        }
        binding.tchKindView.setOnClickListener {
            switchKindViewInnerVisible = !switchKindViewInnerVisible
            toggleView(R.id.tchKindInnerView, switchKindViewInnerVisible, binding.tchKindInnerView)
        }
        binding.tchSceWhemainView.setOnClickListener {
            switchSceWhereinViewVisible = !switchSceWhereinViewVisible
            toggleView(
                R.id.tchSceWhemainInnerView,
                switchSceWhereinViewVisible,
                binding.tchSceWhemainInnerView
            )
        }

    }

    private fun init(it: Context) {
        initJobInformation(it)
        initJobKind(it)
        initJobTransfer(it)
    }

    private fun initJobTransfer(it: Context) {
        binding.tchSecUnit.setText(response.tchSecUnit.toString())
        setTchWheMainTher(it)
        setTchScePurpose(it)
    }

    private fun initJobKind(it: Context) {
//        編制內/編制外
        setTchEstablishment(it)
//        兼專任
        setTchKind(it)
//        專(兼)職業別
        setTchKindIndustry(it)
//         兼任行政工作
        setTchPartAdmini(it)
//      專(兼)職單位
        binding.tchKindDepartment.setText(response.tchKindDepartment.toString())
//      專(兼)職職務
        binding.tchFullPartPosition.setText(response.tchFullPartPosition.toString())
//        行政工作職務
        binding.tchAdminiJob.setText(response.tchAdminiJob.toString())
    }

    private fun initJobInformation(it: Context) {
        //  原任單位
        binding.tchOriginalUnit.setText(response.tchOriginalUnit.toString())
        //  新任單位
        binding.tchAppointDate.setText(response.tchAppointDate.toString())
        //  主聘系所
        setTchMainDepartment(it)
        //共聘系所
        setTchCoeDepartment(it)
        // 任職狀態
        setTchState(it)
        //聘任日期
        binding.tchHureDate.setText(response.tchHureDate.toString())
        binding.tchHureDate.setOnClickListener {
            datePicker(binding.tchHureDate)
        }
        // 最早到校日
        binding.tchSchDate.setText(response.tchSchDate.toString())
        binding.tchSchDate.setOnClickListener {
            datePicker(binding.tchHureDate)
        }
        // 離職日期
        binding.tchResignDate.setText(response.tchResignDate.toString())
        binding.tchResignDate.setOnClickListener {
            datePicker(binding.tchResignDate)
        }
        //停職日期
        binding.tchStopDate.setText(response.tchStopDate.toString())
        binding.tchStopDate.setOnClickListener {
            datePicker(binding.tchStopDate)
        }
        //復職日期
        binding.tchReinstateDate.setText(response.tchReinstateDate.toString())
        binding.tchReinstateDate.setOnClickListener {
            datePicker(binding.tchReinstateDate)
        }
    }

    private fun setTchScePurpose(it: Context) {
        bindSpinnerAdapter(
            R.array.tch_tchscepurpose_array_en,
            binding.tchScePurpose,
            response.tchScePurpose.toString(),
            it
        )
    }

    private fun setTchWheMainTher(it: Context) {
        bindSpinnerAdapter(
            R.array.tch_abor_en,
            binding.tchSceWhemainTher,
            response.tchSceWhemain_ther.toString(),
            it
        )
    }

    private fun setTchEstablishment(it: Context) {
        bindSpinnerAdapter(
            R.array.tchEstablishment_array_en,
            binding.tchEstablishment,
            response.tchEstablishment.toString(),
            it
        )
    }

    private fun setTchKind(context: Context) {
        bindSpinnerAdapter(
            R.array.tchKind_array_en, binding.tchKind, response.tchKind.toString(), context
        )
    }

    private fun setTchKindIndustry(context: Context) {
        bindSpinnerAdapter(
            R.array.tch_tchkindindustry_array_en,
            binding.tchKindIndustry,
            response.tchKindIndustry.toString(),
            context
        )
    }

    private fun setTchPartAdmini(context: Context) {
        bindSpinnerAdapter(
            R.array.tch_abor_en, binding.tchPartAdmini, response.tchPartAdmini.toString(), context
        )
    }

    private fun setTchState(context: Context) {
        bindSpinnerAdapter(
            R.array.tch_tchstate_array_en, binding.tchState, response.tchState.toString(), context
        )
    }

    private fun setTchCoeDepartment(context: Context) {
        bindSpinnerAdapter(
            R.array.tchCoeDepartment_array_en,
            binding.tchCoeDepartment,
            response.tchCoeDepartment.toString(),
            context
        )
    }

    private fun setTchMainDepartment(context: Context) {
        bindSpinnerAdapter(
            R.array.tchMainDepartment_array_en,
            binding.tchMainDepartment,
            response.tchMainDepartment.toString(),
            context
        )

    }


}