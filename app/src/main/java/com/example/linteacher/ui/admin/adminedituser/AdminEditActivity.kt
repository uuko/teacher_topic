package com.example.linteacher.ui.admin.adminedituser

import android.R
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.linteacher.api.pojo.admin.list.AdminListTeacherResponse
import com.example.linteacher.databinding.ActivityAdminEditBinding
import com.example.linteacher.ui.admin.adminaddteacher.AddTeacherActivity
import com.example.linteacher.ui.teacherdata.TeacherInformationFirstActivity
import com.example.linteacher.util.ActivityNavigator
import com.example.linteacher.util.Config
import com.example.linteacher.util.preference.LoginPreferences


class AdminEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminEditBinding
    private lateinit var adapter: AdminEditAdapter
    private lateinit var resultLauncher:ActivityResultLauncher<Intent>
    private val factory = AdminEditViewModelFactory(AdminEditRepository())
    private val viewModel: AdminEditViewModel by viewModels {
        factory
    }
    private final val TAG="AdminEditActivity"
    private lateinit var owner: LifecycleOwner
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

                result ->
            Log.d(TAG, "registerForActivityResult0000000: "+result.resultCode)
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                Log.d(TAG, "registerForActivityResult: ")
                viewModel.listAllTeacher().observe(owner,
                    { t ->
                        if (t.list.isNotEmpty()){
                            adapter.swapItems(t.list)
                        } else{
                            Toast.makeText(applicationContext, "listAllTeacher Verified !"+t.error, Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        }
        val context=this
        binding = ActivityAdminEditBinding.inflate(layoutInflater)
        owner=this
        setContentView(binding.root)

        binding.addBtn.setOnClickListener(
            object :View.OnClickListener{
                override fun onClick(p0: View?) {

                    ActivityNavigator
                        .openActivity(resultLauncher,AddTeacherActivity::class.java,this@AdminEditActivity)


                }
            }
        )
        initRecycleView()
        listAllTeacher()
        binding.refreshLayout.setOnRefreshListener(OnRefreshListener {
            binding.refreshLayout.isRefreshing = false
            listAllTeacher()
        })




    }

    private fun listAllTeacher() {
        viewModel.listAllTeacher().observe(this,
            { t ->
                if (t.list.isNotEmpty()){
                    adapter.swapItems(t.list)
                } else{
                    Toast.makeText(applicationContext, "listAllTeacher Verified !"+t.error, Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.d(TAG, "onBackPressed: ")
    }

    private fun initRecycleView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation= LinearLayoutManager.VERTICAL
        layoutManager.reverseLayout=false
        binding.adminTeacherRecycleView.layoutManager =layoutManager
        val loginPreferences=LoginPreferences(this)
        adapter= AdminEditAdapter(ArrayList(),listener = object :
            AdminEditActivity.OnHideClickListener {
            override fun onHideClick(item: AdminListTeacherResponse, position: Int) {
                showDialog(item.teacherName, item.teacherName+""+item.isVisible,object :DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        viewModel.changeUserAuthority(loginPreferences).observe(owner,
                            { t ->
                                if (t.result.equals(Config.RESULT_OK)){
                                    Log.d("isVisble", "onClick: "+item.isVisible)
                                    val isVisble=!item.isVisible
                                    adapter.changeItemColors(position,isVisble)
                                    p0?.dismiss()
                                } else{
                                    Toast.makeText(applicationContext, "changeUserAuthority Verified !"+t, Toast.LENGTH_SHORT).show()
                                }
                            })
                    }

                })
            }

            override fun onMoreClick(name: AdminListTeacherResponse, position: Int) {
                val bundle=Bundle()
                Log.d("onMoreClick", "loginId: ${name.loginId}")
                bundle.putInt("loginId",name.loginId)
                ActivityNavigator
                    .openActivityWithData(resultLauncher
                        , TeacherInformationFirstActivity::class.java,this@AdminEditActivity
                        ,bundle)
            }

        })
        binding.adminTeacherRecycleView.adapter=adapter
    }
    fun showDialog(
        text: String?, title: String?, negativeClickListener: DialogInterface.OnClickListener?
    ) {
        AlertDialog.Builder(this)
            .setMessage(text)
            .setTitle(title)
            .setNegativeButton(
               "cancel"
            ) { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            .setPositiveButton(
                "ok",
                negativeClickListener
            )
            .setCancelable(false)
            .create()
            .show()
    }
    interface OnHideClickListener {
        fun onHideClick(name: AdminListTeacherResponse, position: Int)
        fun onMoreClick(name: AdminListTeacherResponse, position: Int)
    }
}