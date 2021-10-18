package com.example.linteacher.ui.teacherdata.ui.technologytrans.editfirst

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.linteacher.R
import com.example.linteacher.api.pojo.teacherdata.techtransfer.TechInnerDeleteRequest
import com.example.linteacher.api.pojo.teacherdata.techtransfer.TechTransFerPostRequest
import com.example.linteacher.api.pojo.teacherdata.techtransfer.TechTransFerUpdateRequest
import com.example.linteacher.databinding.ActivityAddArticleBinding
import com.example.linteacher.databinding.ActivityTechTransferEditBinding
import com.example.linteacher.ui.addarticle.AddArticleRepository
import com.example.linteacher.ui.addarticle.AddArticleViewModel
import com.example.linteacher.ui.addarticle.AddArticleViewModelFactory
import com.example.linteacher.ui.teacherdata.ui.technologytrans.TechTransFerListener
import com.example.linteacher.ui.teacherdata.ui.technologytrans.TechTransferAdapter
import com.example.linteacher.ui.teacherdata.ui.technologytrans.editinner.TechEditInnerActivity
import com.example.linteacher.util.ActivityNavigator
import com.example.linteacher.util.BaseActivity
import com.example.linteacher.util.Config
import com.example.linteacher.util.preference.LoginPreferences

class TechTransferEditActivity : BaseActivity() {
    private var id = 0;
    private var tecId:Int  =0
    private lateinit var binding: ActivityTechTransferEditBinding
    private val factory = TechTransferEditViewModelFactory(TechTransFerEditRepository())
    private val viewModel: TechTransFerEditViewModel by viewModels {
        factory
    }
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var adapter: TechTransFerInnerAdapter
    private lateinit var loginPreferences: LoginPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTechTransferEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        id = (intent.getSerializableExtra("techId")) as Int //模式
        Log.d("resultLauncher", "onCreate: id"+id)
        loginPreferences = LoginPreferences(this)
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    Log.d("resultLauncher", "onCreate: ")
                    observeGetList()
                }
            }
        if (id != Config.EDIT_TECHCHANGE) {
            observeGetList()
            binding.deleteBtn.visibility=View.VISIBLE
        } else{
            binding.deleteBtn.visibility=View.GONE
            binding.addInnerBtn.visibility=View.GONE
        }


//        binding.addInnerBtn.visibility=View.GONE
            binding.addInnerBtn.setOnClickListener {
                openNextActivity(Config.EDIT_TECHCHANGE)
            }

        binding.deleteBtn.setOnClickListener {
            val request = TechInnerDeleteRequest(tecId)

            viewModel.deleteData(request)
                .observe(this, {
                    setResult(Activity.RESULT_OK,intent)
                    finish()
                })

        }
        binding.saveBtn.setOnClickListener {
            if (id != Config.EDIT_TECHCHANGE) {
                observeUpdateList()
            } else {
                observePostList()
            }
        }

        initRecyclerView()

    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        layoutManager.reverseLayout = false
        binding.recyclerView.layoutManager = layoutManager

        adapter = TechTransFerInnerAdapter(object : TechTransFerListener.View {
            override fun onItemClick(id: Int) {
                openNextActivity(id)

            }

        })

        binding?.recyclerView?.adapter = adapter
    }

    private fun observePostList() {
        val request: TechTransFerPostRequest = TechTransFerPostRequest();
        if (binding.tecContentPatent.selectedItemPosition != -1) {
        request.tecContentPatent = getResponseSpinner(
            R.array.tch_abor_en,
            binding.tecContentPatent,
        )
        }
        request.loginId = loginPreferences.getTeacherId().toInt()
        if (binding.tecTransfer.selectedItemPosition != -1) {
        request.tecTransfer = getResponseSpinner(
            R.array.tch_tecTransfer_array_en,
            binding.tecTransfer,
        )}
        request.tecTransferName = binding.tecTransferName.text.toString()
        request.tecNumber = binding.tecNumber.text.toString()
        viewModel.postData(request)
            .observe(this, {
                if (it.result == Config.RESULT_OK) {
                    observeGetList()
                    binding.addInnerBtn.visibility=View.VISIBLE
                    tecId = it.id
                    id = it.id

                }
            })
    }

    private fun observeUpdateList() {
        val request: TechTransFerUpdateRequest = TechTransFerUpdateRequest();
        request.tecContentPatent = getResponseSpinner(
            R.array.tch_abor_en,
            binding.tecContentPatent,
        )
        request.loginId = loginPreferences.getTeacherId().toInt()
        request.tecTransfer = getResponseSpinner(
            R.array.tch_tecTransfer_array_en,
            binding.tecTransfer,
        )
        request.tecTransferName = binding.tecTransferName.text.toString()
        request.tecNumber = binding.tecNumber.text.toString()
        request.tecId = id
        viewModel.updateData(request)
            .observe(this, {
                if (it.result == Config.RESULT_OK) {
                    observeGetList()
                }
            })
    }

    private fun observeGetList() {
        Log.d("resultLauncher", "observeGetList id: "+id)
        viewModel.getList(id.toString())
            .observe(this, {
                if (it.error == Config.RESULT_OK) {
                   tecId = it.list!!.tecId

                    it.list?.let { data ->
                        bindSpinnerAdapter(
                            R.array.tch_tecTransfer_array_en,
                            binding.tecTransfer,
                            data.tecTransfer,
                            this
                        )

                        binding.tecTransferName.setText(data.tecTransferName)
                        binding.tecNumber.setText(data.tecNumber)
//                        binding.tecNumber.setText(data.tecNumber  )
                        bindSpinnerAdapter(
                            R.array.tch_abor_en,
                            binding.tecContentPatent,
                            data.tecContentPatent,
                            this
                        )

                        if (data.techChgeCompanyModelList.isNotEmpty()) {
//                            binding.innerContent.visibility = View.VISIBLE
                            binding.tecTransferText.text = "${data.tecTransfer}對象"
                            adapter.submitList(data.techChgeCompanyModelList)
                        }else binding.recyclerView.setVisibility(GONE)



                    }
                }

            })
    }

     override fun onBackPressed() {
         setResult(Activity.RESULT_OK,intent)
        super.onBackPressed()

         Log.d("resultLauncher", "onBackPressed: ")

    }

    private fun openNextActivity(id: Int) {
        val bundle = Bundle()
        Log.d("techIdReal", "openNextActivity: "+tecId)
        bundle.putSerializable("techId", id)
        bundle.putSerializable("techIdReal", tecId)
        this.let {
            ActivityNavigator.openFragmentActivityWithData(
                resultLauncher,
                TechEditInnerActivity::class.java,
                it,
                bundle,
            )
        }
    }
}