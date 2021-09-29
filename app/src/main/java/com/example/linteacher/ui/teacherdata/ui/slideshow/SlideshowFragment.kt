package com.example.linteacher.ui.teacherdata.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
import com.example.linteacher.ui.teacherdata.ui.off.*
import com.example.linteacher.util.Config
import com.example.linteacher.util.preference.LoginPreferences


class SlideshowFragment : Fragment(){

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

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}