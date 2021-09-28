package com.example.linteacher.ui.teacherdata.ui.license

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.linteacher.R
import com.example.linteacher.databinding.FragmentLicenseBinding

class LicenseFragment : Fragment() {

    companion object {
        fun newInstance() = LicenseFragment()
    }

    private lateinit var viewModel: LicenseViewModel
    private lateinit var binding:FragmentLicenseBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_license, container, false)
    }


}