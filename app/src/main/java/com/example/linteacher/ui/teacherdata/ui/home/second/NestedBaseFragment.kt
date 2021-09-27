package com.example.linteacher.ui.teacherdata.ui.home.second

import androidx.fragment.app.Fragment
import com.example.linteacher.api.pojo.teacherdata.profile.TeacherProfileResponse
import com.example.linteacher.util.BaseFragment

abstract class NestedBaseFragment : BaseFragment() {
     abstract fun getSubmitData():TeacherProfileResponse
     abstract fun setResponse( response: TeacherProfileResponse)
}