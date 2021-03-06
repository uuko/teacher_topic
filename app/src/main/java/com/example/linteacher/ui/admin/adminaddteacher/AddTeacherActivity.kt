package com.example.linteacher.ui.admin.adminaddteacher

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.linteacher.api.pojo.admin.addteacher.AddTeacherAllResponse
import com.example.linteacher.databinding.ActivityAddTeacherBinding
import com.example.linteacher.util.Config

class AddTeacherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTeacherBinding
    private lateinit var owner: LifecycleOwner
    private val factory = AddTeacherViewModelFactory(AddTeacherRepository())
    private val viewModel: AddTeacherViewModel by viewModels {
        factory
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTeacherBinding.inflate(layoutInflater)
        owner=this
        setContentView(binding.root)

//        TextUtils.isEmpty(edit.getText())

        binding.checkBox.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                if (p1){
                    binding.teacherCheckPasswd.transformationMethod =
                        HideReturnsTransformationMethod.getInstance();
                    binding.teacherPwd.transformationMethod=
                        HideReturnsTransformationMethod.getInstance();
                }
                else{
                    binding.teacherCheckPasswd.transformationMethod =
                        PasswordTransformationMethod.getInstance();
                    binding.teacherPwd.transformationMethod =
                        PasswordTransformationMethod.getInstance();
                }


            }

        })
        binding.addTeacher.setOnClickListener {
            if (checkTextEmpty()){
                viewModel.registerTeacher(
                    name = binding.teacherName.text.toString(),
                    pwd = binding.teacherPwd.text.toString(),
                    email = binding.teacherEmail.text.toString()
                )
                    .observe(owner,object :Observer<AddTeacherAllResponse>{
                        override fun onChanged(t: AddTeacherAllResponse) {
                            if (t.result.equals(Config.RESULT_OK)){
                                Log.d("addTeacher", "onChanged: ")
                                setResult(Activity.RESULT_OK,intent)
                                finish()

                            }

                        }
                    })
            }
            else{
                Log.d("addTeacher", "error: ")
            }
        }
    }

    private fun checkTextEmpty() :Boolean{
        if (TextUtils.isEmpty(binding.teacherName.text.toString())){
            return false
        }
        else if (TextUtils.isEmpty(binding.teacherEmail.text.toString())){
            return false
        }
        else if (TextUtils.isEmpty(binding.teacherPwd.text.toString())){
            return false
        }
        else if (TextUtils.isEmpty(binding.teacherCheckPasswd.text.toString())){
            return false
        }
        else if ( ! (binding.teacherCheckPasswd.text.toString().equals(binding.teacherPwd.text.toString()))){
            return false
        }
        else {
            return true
        }
    }
}