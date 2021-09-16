package com.example.linteacher.ui.main.teacherline

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.linteacher.databinding.FragmentTeacherBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TeacherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TeacherFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var teacherLineAdapter: TeacherLineAdapter
    private var _binding: FragmentTeacherBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTeacherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleView()
    }

    private fun initRecycleView() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation= LinearLayoutManager.VERTICAL
        layoutManager.reverseLayout=false
        _binding?.teacherListRecycleView?.layoutManager =layoutManager
        teacherLineAdapter= TeacherLineAdapter(ArrayList())
        _binding?.teacherListRecycleView?.adapter=teacherLineAdapter
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TeacherFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}