package com.example.linteacher.ui.teacherdata.ui.home.second

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.linteacher.R
import com.example.linteacher.api.pojo.teacherdata.profile.TeacherProfileResponse



/**
 * A simple [Fragment] subclass.
 * Use the [TeacherDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TeacherDetailFragment : NestedBaseFragment() {

    override fun getSubmitData():TeacherProfileResponse {
        TODO("Not yet implemented")
    }

    override fun setResponse(response: TeacherProfileResponse) {
        TODO("Not yet implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_detail, container, false)
    }


}