package com.example.linteacher.ui.teacherdata.ui.home.second

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.linteacher.R
import com.example.linteacher.api.pojo.teacherdata.profile.TeacherProfileResponse
import com.example.linteacher.databinding.FragmentTeacherDetailBinding


/**
 * A simple [Fragment] subclass.
 * Use the [TeacherDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TeacherDetailFragment : NestedBaseFragment() {
    private var _binding: FragmentTeacherDetailBinding? = null
    private val binding get() = _binding!!

    private  var response: TeacherProfileResponse = TeacherProfileResponse()
    override fun getSubmitData():TeacherProfileResponse {
        val result=response

        if (binding.tchHireNumber.text.isNotEmpty())result.tchHireNumber=binding.tchHireNumber.text.toString()
        if (binding.tchCertificateNumber.text.isNotEmpty())result.tchCertificateNumber=binding.tchCertificateNumber.text.toString()
        if (binding.tchmainLicenseNumber.text.isNotEmpty())result.tchmain_licenseNumber=binding.tchmainLicenseNumber.text.toString()

        val tchTypeArray= context?.resources?.getStringArray(R.array.tchType_array_en)
        if (binding.tchType.selectedItemPosition!=-1){
            result.tchType= tchTypeArray?.get(binding.tchType.selectedItemPosition).toString()
        }

        val tchRireRankArray= context?.resources?.getStringArray(R.array.tch_tchcertificaterank_array_en)
        if (binding.tchRireRank.selectedItemPosition!=-1){
            result.tchRireRank = tchRireRankArray?.get(binding.tchRireRank.selectedItemPosition).toString()
        }

        val yesNoArray= context?.resources?.getStringArray(R.array.tch_abor_en)
        if (binding.tchRireYear.selectedItemPosition!=-1){
            result.tchRireYear=yesNoArray?.get(binding.tchRireYear.selectedItemPosition).toString()
        }
        if (binding.tch107PaySalary.selectedItemPosition!=-1){
            result.tch107PaySalary=yesNoArray?.get(binding.tch107PaySalary.selectedItemPosition).toString()
        }
        if (binding.tchFiestAssistant.selectedItemPosition!=-1){
            result.tchFiestAssistant=yesNoArray?.get(binding.tchFiestAssistant.selectedItemPosition).toString()
        }
        if (binding.tchFullTime.selectedItemPosition!=-1){
            result.tchFullTime=yesNoArray?.get(binding.tchFullTime.selectedItemPosition).toString()
        }
        if (binding.tchComplyLaw.selectedItemPosition!=-1){
            result.tchComplyLaw=yesNoArray?.get(binding.tchComplyLaw.selectedItemPosition).toString()
        }
        val tchCertificateRankRankArray= context?.resources?.getStringArray(R.array.tch_tchcertificaterank_array_en)
        if (binding.tchCertificateRank.selectedItemPosition!=-1){
            result.tchCertificateRank = tchCertificateRankRankArray?.get(binding.tchCertificateRank.selectedItemPosition).toString()
        }

        return result
    }

    override fun setResponse(response: TeacherProfileResponse) {
        this.response=response
        context?.let { init(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let { init(it) }
    }
    private fun init(context: Context) {
        binding.tchHireNumber.setText(response.tchHireNumber.toString())
        binding.tchCertificateNumber.setText(response.tchCertificateNumber.toString())
        binding.tchmainLicenseNumber.setText(response.tchmain_licenseNumber.toString())
        setTchTypeSpinner(context)
        setTchRireRankSpinner(context)
        setTchRireYear(context)
        setTchCertificateRank(context)
        setTchFiestAssistant(context)
        setTchFullTime(context)
        setTchComplyLaw(context)
        setTch107PaySalary(context)
    }

    private fun setTch107PaySalary(context: Context) {
        val adapter = ArrayAdapter.createFromResource(
            context,
            R.array.tch_abor_en,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.tch107PaySalary.adapter = adapter
        binding.tch107PaySalary.setSelection(getAbor(response.tch107PaySalary .toString()))
        binding.tch107PaySalary.onItemSelectedListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                binding.tch107PaySalary.setSelection(pos,false)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun setTchComplyLaw(context: Context) {
        val adapter = ArrayAdapter.createFromResource(
            context,
            R.array.tch_abor_en,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.tchComplyLaw.adapter = adapter
        binding.tchComplyLaw.setSelection(getAbor(response.tchComplyLaw .toString()))
        binding.tchComplyLaw.onItemSelectedListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                binding.tchComplyLaw.setSelection(pos,false)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun setTchFullTime(context: Context) {
        val adapter = ArrayAdapter.createFromResource(
            context,
            R.array.tch_abor_en,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.tchFullTime.adapter = adapter
        binding.tchFullTime.setSelection(getAbor(response.tchFullTime .toString()))
        binding.tchFullTime.onItemSelectedListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                binding.tchCertificateRank.setSelection(pos,false)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun setTchFiestAssistant(context: Context) {
        val adapter = ArrayAdapter.createFromResource(
            context,
            R.array.tch_abor_en,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.tchFiestAssistant.adapter = adapter
        binding.tchFiestAssistant.setSelection(getAbor(response.tchFiestAssistant .toString()))
        binding.tchFiestAssistant.onItemSelectedListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                binding.tchCertificateRank.setSelection(pos,false)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun setTchCertificateRank(context: Context) {
        val adapter = ArrayAdapter.createFromResource(
            context,
            R.array.tch_tchcertificaterank_array_en,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.tchCertificateRank.adapter = adapter
        binding.tchCertificateRank.setSelection(getTchType(response.tchCertificateRank .toString()), false)
        binding.tchCertificateRank.onItemSelectedListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                binding.tchCertificateRank.setSelection(pos,false)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun setTchRireYear(context: Context) {
        val adapter = ArrayAdapter.createFromResource(
            context,
            R.array.tch_abor_en,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.tchRireYear.adapter = adapter
        binding.tchRireYear.setSelection(getAbor(response.tchIsAboriginal .toString()), false)
        binding.tchRireYear.onItemSelectedListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                binding.tchRireYear.setSelection(pos,false)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }
    private fun getAbor(toString: String): Int {
        val array= context?.resources?.getStringArray(R.array.tch_abor_en)
        for (i in array!!.indices){
            if (toString == array[i]){
                return i
            }
        }
        return 0
    }
    private fun setTchRireRankSpinner(context: Context) {
        val adapter = ArrayAdapter.createFromResource(
            context,
            R.array.tch_tchcertificaterank_array_en,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.tchRireRank.adapter = adapter
        binding.tchRireRank.setSelection(getTchRireRank(response.tchRireRank.toString()), false)
        binding.tchRireRank.onItemSelectedListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                binding.tchRireRank.setSelection(pos,false)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun getTchRireRank(toString: String): Int {
        val array= context?.resources?.getStringArray(R.array.tch_tchcertificaterank_array_en)
        for (i in array!!.indices){
            if (toString == array[i]){
                return i
            }
        }
        return 0
    }

    private fun setTchTypeSpinner(context: Context) {
        val adapter = ArrayAdapter.createFromResource(
            context,
            R.array.tchType_array_en,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.tchType.adapter = adapter
        binding.tchType.setSelection(getTchType(response.tchType.toString()), false)
        binding.tchType.onItemSelectedListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                binding.tchType.setSelection(pos,false)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun getTchType(toString: String): Int {
        val array= context?.resources?.getStringArray(R.array.tchType_array_en)
        for (i in array!!.indices){
            if (toString == array[i]){
                return i
            }
        }
        return 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTeacherDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


}