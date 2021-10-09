package com.example.linteacher.ui.teacherdata.ui.patent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.linteacher.R
import com.example.linteacher.ui.teacherdata.ui.award.AwardFragment
import com.example.linteacher.ui.teacherdata.ui.award.AwardViewModel

class PatentFragment:Fragment() {
    companion object {
        fun newInstance() = AwardFragment()
    }

    private lateinit var viewModel: AwardViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_patent, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AwardViewModel::class.java)
        // TODO: Use the ViewModel
    }
}