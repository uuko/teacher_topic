package com.example.linteacher.ui.teacherdata.ui.technologytrans.editinner

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.example.linteacher.api.pojo.teacherdata.techtransfer.TechInnerDeleteRequest
import com.example.linteacher.api.pojo.teacherdata.techtransfer.TechInnerPostOneRequest
import com.example.linteacher.api.pojo.teacherdata.techtransfer.TechInnerUpdateRequest
import com.example.linteacher.databinding.ActivityTechEditInnerBinding
import com.example.linteacher.databinding.ActivityTechTransferEditBinding
import com.example.linteacher.ui.teacherdata.ui.technologytrans.editfirst.TechTransFerEditRepository
import com.example.linteacher.ui.teacherdata.ui.technologytrans.editfirst.TechTransFerEditViewModel
import com.example.linteacher.ui.teacherdata.ui.technologytrans.editfirst.TechTransferEditViewModelFactory
import com.example.linteacher.util.Config

class TechEditInnerActivity : AppCompatActivity() {
    private var id = 0;
    private var tecId:Int = 0
    private var tecCompanyId:Int =0
    private lateinit var binding: ActivityTechEditInnerBinding
    private val factory = TechEditInnerViewModelFactory(TechEditInnnerRepository())
    private val viewModel: TechEditInnerViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTechEditInnerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        id = (intent.getSerializableExtra("techId")) as Int
        tecId = (intent.getSerializableExtra("techIdReal")) as Int
        Log.d("fuckckkc", "模式: "+id+"父id"+tecId)



        if (id != Config.EDIT_TECHCHANGE) {
            observeList()
            binding.deleteBtn.visibility=View.VISIBLE
        }
        else {
            binding.deleteBtn.visibility= View.GONE
        }
        binding.addBtn.setOnClickListener {
            if (id == Config.EDIT_TECHCHANGE) {
                val request = TechInnerPostOneRequest()
                request.tecEndDate = binding.tecEndDate.text.toString()
                request.tecId = tecId
                request.tecStratDate = binding.tecStratDate.text.toString()
                request.tecTransferAmount = binding.tecTransferAmount.text.toString()
                request.tecTransferNumber = binding.tecTransferAmount.text.toString()
                request.tecTransferVendor = binding.tecTransferAmount.text.toString()
                viewModel.postData(request)
                    .observe(this, {
                        if (it.result == Config.RESULT_OK) {
                            setResult(Activity.RESULT_OK,intent)
                            finish()
                        }
                    })
            } else {
                val request = TechInnerUpdateRequest()
                request.tecEndDate = binding.tecEndDate.text.toString()
                request.tecStratDate = binding.tecStratDate.text.toString()
                request.tecTransferAmount = binding.tecTransferAmount.text.toString()
                request.tecTransferNumber = binding.tecTransferAmount.text.toString()
                request.tecTransferVendor = binding.tecTransferAmount.text.toString()
                request.tecCompanyId = tecCompanyId

                viewModel.updateData(request)
                    .observe(this, {
                        if (it.result == Config.RESULT_OK) {
                            id =-1
                            setResult(Activity.RESULT_OK,intent)
                            finish()
                        }
                    })
            }
        }

        binding.deleteBtn.setOnClickListener {
            if (id != Config.EDIT_TECHCHANGE) {
                val request= TechInnerDeleteRequest(tecCompanyId);
                viewModel.deleteData(request)
                    .observe(this, {
                        setResult(Activity.RESULT_OK,intent)
                        finish()
                    })
            }

        }
    }

    private fun observeList() {
        viewModel.getData(id.toString())
            .observe(this, {
                binding.tecTransferVendor.setText(it.tecTransferVendor)
                binding.tecTransferAmount.setText(it.tecTransferAmount)
                binding.tecStratDate.setText(it.tecStratDate)
                binding.tecEndDate.setText(it.tecEndDate)
                binding.tecTransferNumber.setText(it.tecTransferNumber)
                tecCompanyId = it.tecCompanyId
                id = 0
            })
    }
}