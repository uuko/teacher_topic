package com.example.linteacher.ui.teacherdata.ui.technologytrans

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.paging.Config
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.linteacher.R
import com.example.linteacher.api.pojo.teacherdata.paper.ui.PaperBaseData
import com.example.linteacher.api.pojo.teacherdata.paper.ui.PaperOriginData
import com.example.linteacher.databinding.FragmentPaperBinding
import com.example.linteacher.databinding.FragmentTechTransferBinding
import com.example.linteacher.ui.managearticle.editinner.EditInnerActivity
import com.example.linteacher.ui.teacherdata.ui.paper.PaperAdapter
import com.example.linteacher.ui.teacherdata.ui.paper.PaperRepository
import com.example.linteacher.ui.teacherdata.ui.paper.PaperViewModel
import com.example.linteacher.ui.teacherdata.ui.paper.PaperViewModelFactory
import com.example.linteacher.ui.teacherdata.ui.technologytrans.editfirst.TechTransferEditActivity
import com.example.linteacher.util.ActivityNavigator
import com.example.linteacher.util.preference.LoginPreferences

/**
 * A simple [Fragment] subclass.
 * Use the [TechTransferFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TechTransferFragment : Fragment() {

    private lateinit var adapter :TechTransferAdapter
    private var _binding: FragmentTechTransferBinding? = null
    private val factory = TechTransViewModelFactory(TechTransRepository())
    private val viewModel: TechTransViewModel by viewModels {
        factory
    }
    private lateinit var resultLauncher:ActivityResultLauncher<Intent>

    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTechTransferBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

                Log.d("resultLauncher", "onCreate:1 ")

                if (result.resultCode == Activity.RESULT_OK) {
                    Log.d("resultLauncher", "onCreate:2 ")
                    observeGetList()
                }
            }
        initRecycleView()
        observeGetList()

        binding.addBtn.setOnClickListener {
            openNextActivity(com.example.linteacher.util.Config.EDIT_TECHCHANGE)
        }
    }

    private fun observeGetList() {
        val loginPreferences=LoginPreferences(requireView().context)
        viewModel.getList(loginPreferences.getTeacherId())
            .observe(viewLifecycleOwner,{
                if (it.error== com.example.linteacher.util.Config.RESULT_OK){
                    adapter.setDataList(it.list)
                }

            })
    }


    private fun initRecycleView() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        layoutManager.reverseLayout = false
        binding.recyclerView.layoutManager = layoutManager

        adapter= TechTransferAdapter(arrayListOf(),object  : TechTransFerListener.View{
            override fun onItemClick(id: Int) {
                openNextActivity(id)

            }

        })
        _binding?.recyclerView?.adapter = adapter
    }

    private fun openNextActivity(id: Int) {
        val bundle = Bundle()
        bundle.putSerializable("techId", id)
        Log.d("resultLauncher", "傳進來的id: "+id)
        this@TechTransferFragment.activity?.let {
            ActivityNavigator.openFragmentActivityWithData(
                resultLauncher,
                TechTransferEditActivity::class.java,

                it,
                bundle,
            )
        }
    }

    companion object {
    }
}