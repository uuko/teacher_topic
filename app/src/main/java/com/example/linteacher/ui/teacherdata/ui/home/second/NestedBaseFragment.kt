package com.example.linteacher.ui.teacherdata.ui.home.second

import androidx.fragment.app.Fragment
import com.example.linteacher.api.pojo.teacherdata.profile.TeacherProfileResponse

abstract class NestedBaseFragment : Fragment() {
     abstract fun getSubmitData():TeacherProfileResponse
     abstract fun setResponse( response: TeacherProfileResponse)
}