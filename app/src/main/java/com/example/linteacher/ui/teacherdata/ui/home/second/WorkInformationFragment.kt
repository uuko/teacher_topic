package com.example.linteacher.ui.teacherdata.ui.home.second

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.linteacher.R
import com.example.linteacher.api.pojo.teacherdata.profile.TeacherProfileResponse
import com.example.linteacher.databinding.FragmentTeacherDetailBinding
import com.example.linteacher.databinding.FragmentWorkInformationBinding


class WorkInformationFragment : NestedBaseFragment() {

    private var _binding: FragmentWorkInformationBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWorkInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun getSubmitData():TeacherProfileResponse {
       return TeacherProfileResponse()
    }

    override fun setResponse(response: TeacherProfileResponse) {

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}