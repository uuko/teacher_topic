package com.example.linteacher.ui.main.teacherline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.linteacher.api.pojo.teacherline.TeacherLineResponse
import com.example.linteacher.databinding.FragmentTeacherBinding
import com.example.linteacher.ui.main.teacherline.tchsencondline.TeacherSecondLineActivity
import com.example.linteacher.util.ActivityNavigator

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
    private val factory = TeacherLineViewModelFactory(TeacherLineRepository())
    private val viewModel: TeacherLineViewModel by viewModels {
        factory
    }

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
        initData()
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                _binding?.progressBar?.visibility = View.VISIBLE
            } else _binding?.progressBar?.visibility = View.GONE

        })

        binding.mSwipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            binding.mSwipeRefreshLayout.isRefreshing = false
            initData()
        })


    }

    private fun initData() {
        viewModel.postTeacherList()
            .observe(viewLifecycleOwner, object : Observer<TeacherLineAllResponse> {
                override fun onChanged(t: TeacherLineAllResponse?) {
                    viewModel.isLoading.value = (false)
                    if (t == null) {
                        teacherLineAdapter.swapItems(arrayListOf())
                        return
                    }
                    if (t.list.isNotEmpty()) {
                        teacherLineAdapter.swapItems(t.list)
                    } else {
                        Toast.makeText(context, "連線發生錯誤", Toast.LENGTH_SHORT).show()
                    }
                }

            })
    }

    private fun initRecycleView() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        layoutManager.reverseLayout = false
        _binding?.teacherListRecycleView?.layoutManager = layoutManager
        teacherLineAdapter =
            TeacherLineAdapter(ArrayList(), listener = object : OnItemClickListener {
                override fun onItemClick(item: TeacherLineResponse) {
                    activity?.let {
                        val bundle = Bundle()
                        bundle.putSerializable("item", item.tchNumber)
                        ActivityNavigator.startActivityWithData(
                            TeacherSecondLineActivity::class.java,
                            bundle,
                            it
                        )
                    }
                }

            })
        _binding?.teacherListRecycleView?.adapter = teacherLineAdapter
    }

    interface OnItemClickListener {
        fun onItemClick(item: TeacherLineResponse)
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