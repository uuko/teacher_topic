package com.example.linteacher.ui.teacherdata.ui.home.second

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.example.linteacher.R
import com.example.linteacher.api.pojo.teacherdata.profile.TeacherProfileResponse
import com.example.linteacher.databinding.FragmentProfileSecondBinding
import com.example.linteacher.util.Config
import java.text.SimpleDateFormat
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProfileSecondFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private  var response: TeacherProfileResponse = TeacherProfileResponse()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private var _binding: FragmentProfileSecondBinding? = null
    private val binding get() = _binding!!
    var isTchNameVisible=false
    var isTchGenderVisible=false
    var isTchEduVisible=false
    var isTchPhoneVisible=false
    var isTchPersonVisible=false
    var isTchBirthdayVisible=false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileSecondBinding.inflate(inflater, container, false)
        response =
            arguments?.getSerializable(Config.PROFLE_SECOND_FRAGMENT) as TeacherProfileResponse;
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context=view.context
        binding.teacherEducation.text= "${response.tchSchool}-${response.tchDepartment}*${response.tchDiploma}"
        binding.tchExpert.setText(response.tchExpertise.toString() )
        binding.tchSchool.setText(response.tchSchool .toString())
        binding.tchDepart.setText(response.tchDepartment.toString())
        binding.teacherName.text="${response.tchName}(${response.tchNameEN})"
        binding.teacherGender.text = "(${response.sex})"
        binding.teacherBirthday.text=response.tchBirthday.toString()
        binding.teacherCountry.text=response.tchIdNumberI.toString()+"國籍:${response.tchCountry}"
        binding.tchNameView.setOnClickListener {
            isTchNameVisible=!isTchNameVisible
            toggleView(R.id.tch_inside_name_view,isTchNameVisible,binding.tchInsideNameView)
            setTchName(context)
        }

        binding.tchGenderView.setOnClickListener {
            isTchGenderVisible=!isTchGenderVisible
            toggleView(R.id.tch_inside_gender_view,isTchGenderVisible,binding.tchInsideGenderView)
            setTchGender(context)



        }

        binding.tchEducationView.setOnClickListener {

            isTchEduVisible=!isTchEduVisible
            toggleView(R.id.tch_inside_education_view,isTchEduVisible,binding.tchInsideEducationView)
            setTchEduCation(context)

        }



        binding.tchBirthdayView.setOnClickListener {
            isTchBirthdayVisible=!isTchBirthdayVisible
            toggleView(R.id.tch_inside_birthday_view,isTchBirthdayVisible,binding.tchInsideBirthdayView)
            setTchBirtday(context)

        }

        binding.tchPersonView.setOnClickListener {
            isTchPersonVisible=!isTchPersonVisible
            toggleView(R.id.tch_person_inside_view,isTchPersonVisible,binding.tchPersonInsideView)
            setTchPerson(context)

        }

    }

    @SuppressLint("SetTextI18n")
    private fun setTchPerson(context: Context) {
        val ingText=binding.teacherCountry.text.split(":")
        val tchPersonId=ingText[0]
        var country=ingText[1]

        val adapter = ArrayAdapter.createFromResource(
            context,
            R.array.exp_id_px_array_en,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.tchPersonSpinner.adapter = adapter
        binding.tchPersonSpinner.setSelection(getTchPerson(response.tchIdType.toString()), false)
//
        val countryAdapter = ArrayAdapter.createFromResource(
            context,
            R.array.tch_country_en,
            android.R.layout.simple_spinner_item
        )
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.tchCountrySpinner.adapter = countryAdapter
        binding.tchCountrySpinner.setSelection(getCountry(response.tchCountry .toString()), false)
        binding.tchPhdSpinner.onItemSelectedListener = object :AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                country=parent?.getItemAtPosition(pos).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        //
        val clanAdapter = ArrayAdapter.createFromResource(
            context,
            R.array.tch_clan_en,
            android.R.layout.simple_spinner_item
        )
        clanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //
        binding.tchClanSpinner.adapter = clanAdapter
        binding.tchClanSpinner.setSelection(getClan(response.tchAboriginal .toString()), false)
        val aborAdapter = ArrayAdapter.createFromResource(
            context,
            R.array.tch_abor_en,
            android.R.layout.simple_spinner_item
        )
        aborAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.tchAborSpinner.adapter = adapter
        binding.tchAborSpinner.setSelection(getAbor(response.tchIsAboriginal .toString()), false)
        if (isTchPhoneVisible){


            binding.teacherCountry.text= "{ $tchPersonId }國籍:${country}"
        }
        else{
            val responseText="{ $tchPersonId }國籍:${country}"
            val array= context?.resources?.getStringArray(R.array.tch_country_en)
            val text= binding.tchPersonId.text.toString()+"國籍:${country}"
            if (text == responseText)binding.teacherCountry.text=responseText
            else binding.teacherCountry.text=text
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

    private fun getClan(toString: String): Int {
        val array= context?.resources?.getStringArray(R.array.tch_clan_en)
        for (i in array!!.indices){
            if (toString == array[i]){
                return i
            }
        }
        return 0
    }

    private fun getCountry(toString: String): Int {
        val array= context?.resources?.getStringArray(R.array.tch_country_en)
        for (i in array!!.indices){
            if (toString == array[i]){
                return i
            }
        }
        return 0
    }

    private fun getTchPerson(toString: String): Int {
        val array= context?.resources?.getStringArray(R.array.exp_id_px_array_en)
        for (i in array!!.indices){
            if (toString == array[i]){
                return i
            }
        }
        return 0
    }

    private fun setTchBirtday(context: Context) {
        if (isTchPhoneVisible){
            datePicker(binding.tchBirthdaySpinner)
            binding.tchBirthdaySpinner.setText(response.tchBirthday.toString())


        }
        else{
            binding.teacherBirthday.text=response.tchBirthday.toString()

            val text = "${binding.tchBirthdaySpinner.text}"
            val responseText = "${response.tchBirthday}"
            if (text == responseText)binding.tchBirthdaySpinner.setText(responseText)
            else binding.tchBirthdaySpinner.setText(text)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setTchGender(context: Context) {
        if(!isTchGenderVisible) {
            val adapter = ArrayAdapter.createFromResource(
                context,
                R.array.exp_gender_array_en,
                android.R.layout.simple_spinner_item
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.tchGenderSpinner.adapter = adapter
            binding.tchGenderSpinner.setSelection(getGender(binding.teacherGender.text.toString()), false)
            binding.tchGenderSpinner.onItemSelectedListener = object :AdapterView.OnItemClickListener,
                AdapterView.OnItemSelectedListener {
                override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    TODO("Not yet implemented")
                }

                override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                    binding.teacherGender.text=parent?.getItemAtPosition(pos).toString()
                    binding.tchGenderSpinner.setSelection(pos, false)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }

        } else{
           val sex= getGender(binding.teacherGender.text.toString())
            binding.teacherGender.text = "(${sex})"
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setTchName(context: Context) {

        if(isTchNameVisible) {
            val cnName=response.tchName.toString()
            val enName=response.tchNameEN.toString()
            binding.cnName.setText(response.tchName.toString())
            binding.enName.setText(response.tchNameEN.toString())
        }
        else{
            val text = "${binding.cnName.text.toString()}(${binding.enName.text.toString()})"
            val responseText = "${response.tchName}(${response.tchNameEN})"
            if (text == responseText)binding.teacherName.text=responseText
            else binding.teacherName.text=text
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setTchEduCation(context: Context) {
        val ingText=binding.teacherEducation.text.split("-")
        val ingText2=ingText[1].split("*")
        val schoolStr=ingText[0]
        val depart=ingText2[0]
        var phdStr=ingText2[1]
        val adapter = ArrayAdapter.createFromResource(
            context,
            R.array.exp_school_type_array_en,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.tchEducationSpinner.setAdapter(adapter)
        binding.tchEducationSpinner.setSelection(getEduacation(response.tchSchoolType.toString()), false)
        var eduStr=""
        binding.tchEducationSpinner.onItemSelectedListener = object :AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                eduStr=parent?.getItemAtPosition(pos).toString()
                binding.tchEducationSpinner.setSelection(pos,false)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        val phdAdapter = ArrayAdapter.createFromResource(
            context,
            R.array.exp_phd_array_en,
            android.R.layout.simple_spinner_item
        )
        phdAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.tchPhdSpinner.adapter = phdAdapter
        binding.tchPhdSpinner.setSelection(getPhd(response.tchDiploma.toString() ), false)

        binding.tchPhdSpinner.onItemSelectedListener = object :AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                phdStr=parent?.getItemAtPosition(pos).toString()
                binding.tchPhdSpinner.setSelection(pos,false)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        if (isTchEduVisible){


            binding.teacherEducation.text="$schoolStr-$depart*${ phdStr}"

        }
        else{
            val responseText="${schoolStr}-${depart}*${phdStr}"
            val array= context?.resources?.getStringArray(R.array.exp_phd_array_en)
            val text= "${binding.tchSchool.text.toString()}-${binding.tchDepart.text.toString()}*${
                array?.get(binding.tchPhdSpinner.selectedItemPosition)
            }"
            if (text == responseText)binding.teacherEducation.text=responseText
            else binding.teacherEducation.text=text



        }
    }

    private fun getPhd(tchDiploma: String?): Int {
        val array= context?.resources?.getStringArray(R.array.exp_phd_array_en)
        for (i in array!!.indices){
            if (tchDiploma == array[i]){
                return i
            }
        }
        return 0
    }

    private fun getEduacation(tchSchoolType: String?): Int {
        val array= context?.resources?.getStringArray(R.array.exp_school_type_array_en)
        for (i in array!!.indices){
            if (tchSchoolType == array[i]){
                return i
            }
        }
        return 0
    }
    private fun getGender(expType: String):Int {
        val array= context?.resources?.getStringArray(R.array.exp_gender_array_en)
        for (i in array!!.indices){
            if (expType == array[i]){
                return i
            }
        }
        return 0
    }
    fun datePicker(v: View) {
        val calendar: Calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR) //取得現在的日期年月日
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(v.context,
            { view, year, month, day ->
                val calendar = Calendar.getInstance()
                calendar[year, month] = day

                val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                val strDate = format.format(calendar.time)
                (v as EditText).setText(strDate)
            }, year, month, day
        ).show()
    }

    private fun toggleView(target:Int,visible:Boolean,childView: View) {
        val transition =  Fade();
        transition.duration = 300;
        transition.addTarget(target);

        TransitionManager.beginDelayedTransition(childView.parent as ViewGroup, transition);
        if (visible){
            childView.visibility = View.VISIBLE;
        }
        else{
            childView.visibility=View.GONE
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileSecondFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}