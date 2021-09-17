package com.example.linteacher.ui.teacherdata.ui.award

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.linteacher.R

class AwardFragment : Fragment() {

    companion object {
        fun newInstance() = AwardFragment()
    }

    private lateinit var viewModel: AwardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_award, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AwardViewModel::class.java)
        // TODO: Use the ViewModel
    }

}