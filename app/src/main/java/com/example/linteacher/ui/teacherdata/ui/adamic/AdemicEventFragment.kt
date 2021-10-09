package com.example.linteacher.ui.teacherdata.ui.adamic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.linteacher.R
import com.example.linteacher.databinding.FragmentAwardBinding
import com.example.linteacher.databinding.FragmentLicenseBinding
import com.example.linteacher.ui.teacherdata.ui.award.AwardFragment
import com.example.linteacher.ui.teacherdata.ui.award.AwardRepository
import com.example.linteacher.ui.teacherdata.ui.award.AwardViewModel
import com.example.linteacher.ui.teacherdata.ui.award.AwardViewModelFactory
import com.example.linteacher.ui.teacherdata.ui.license.LicenseAdapter
import com.example.linteacher.ui.teacherdata.ui.license.LicenseRepository
import com.example.linteacher.ui.teacherdata.ui.license.LicenseViewModel
import com.example.linteacher.ui.teacherdata.ui.license.LicenseViewModelFactory
import com.example.linteacher.util.preference.LoginPreferences


class AdemicEventFragment : Fragment() {
    companion object {
        fun newInstance() = AwardFragment()
    }
    private var _binding: FragmentAwardBinding? = null
    private val binding get() = _binding!!
    private lateinit var loginPreferences: LoginPreferences
    //viewmodel
    private val factory = AwardViewModelFactory(AwardRepository())
    private val viewModel: AwardViewModel by viewModels {
        factory
    }
    private lateinit var adapter: LicenseAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ademic_event, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(AwardViewModel::class.java)
        // TODO: Use the ViewModel
    }


}