package com.example.linteacher.ui.teacherdata.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.linteacher.R
import com.example.linteacher.api.pojo.UnitResponse
import com.example.linteacher.api.pojo.teacherdata.profile.TeacherProfileResponse
import com.example.linteacher.databinding.FragmentHomeBinding
import com.example.linteacher.ui.teacherdata.ui.home.second.NestedBaseFragment
import com.example.linteacher.ui.teacherdata.ui.home.second.ProfileSecondFragment
import com.example.linteacher.ui.teacherdata.ui.home.second.TeacherDetailFragment
import com.example.linteacher.ui.teacherdata.ui.home.second.WorkInformationFragment
import com.example.linteacher.util.BaseFragment
import com.example.linteacher.util.Config
import com.example.linteacher.util.preference.LoginPreferences
import io.reactivex.Observer
import java.io.File
import java.io.FileOutputStream


class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var manager: FragmentManager
    private lateinit var transaction: FragmentTransaction
    // This property is only valid between onCreateView and
    // onDestroyView.

    private val factory = HomeViewModelFactory(HomeRepository())
    private val viewModel: HomeViewModel by viewModels {
        factory
    }
    var loginId :Int?=0
    lateinit var currrentFragment:NestedBaseFragment
    private  var response: TeacherProfileResponse= TeacherProfileResponse()
    private lateinit var loginPreferences:LoginPreferences
    private var profileSecondFragment:ProfileSecondFragment=ProfileSecondFragment()
    private var workInformationFragment=WorkInformationFragment()
    private var teacherDetailFragment=TeacherDetailFragment()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        manager = childFragmentManager


        transaction = manager.beginTransaction()

        showFragment(profileSecondFragment, Config.PROFLE_SECOND_FRAGMENT)
        hideFragment(workInformationFragment,teacherDetailFragment)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences=LoginPreferences(view.context)
        binding.imagePickButton.setOnClickListener {
            imageChooser()
        }
        view.findViewById<Button>(R.id.submit_btn)
            .setOnClickListener {
                Log.d("submit_btn", "onViewCreated: ")
                sendDataToNestedFragment()
            }

        loginId= arguments?.getInt("loginId")
        Log.d("loginId", "onCreateView: $loginId")
        binding.teacherProfileButton.setOnClickListener {
            showFragment(profileSecondFragment,Config.PROFLE_SECOND_FRAGMENT)
            hideFragment(workInformationFragment,teacherDetailFragment)

        }
        binding.teacherDetailButton.setOnClickListener {
            showFragment(teacherDetailFragment,Config.TEACHER_DETAIL_FRAGMENT)
            hideFragment(profileSecondFragment,workInformationFragment)

        }
        binding.workInformationWork.setOnClickListener {
            showFragment(workInformationFragment,Config.WORK_INFORM_FRAGMENT)
            hideFragment(profileSecondFragment,teacherDetailFragment)

        }
        viewModel.getTeacherProfile(loginId.toString())
            .observe(viewLifecycleOwner, {
                if (it.result == Config.RESULT_OK){
                    response=it.data
                    Log.d("HomeFragment", "setResponse before : "+it.data)

                    currrentFragment.setResponse(response)
                    if (!it.data.tchPicUrl.isNullOrEmpty()){
                        Log.d("tchPicUrl", "Glide: "+it.data.tchPicUrl)
                        Glide.with(view.context)
                            .load( GlideUrl(it.data.tchPicUrl))
                            .centerCrop()
                            .into(binding.imagePickButton)
                    }
                }
            })
    }

    private fun sendDataToNestedFragment() {
        val currentFragment = childFragmentManager.fragments.last() as NestedBaseFragment
        var submitData=currentFragment.getSubmitData()
        if (teacherDetailFragment.isAdded){
             submitData=setDetailData(profileSecondFragment.getSubmitData(),teacherDetailFragment.getSubmitData())
            if (workInformationFragment.isAdded){
                submitData=setWorkData(submitData,workInformationFragment.getSubmitData())
            }
        }
        if (workInformationFragment.isAdded){
            submitData=setWorkData(profileSecondFragment.getSubmitData(),workInformationFragment.getSubmitData())
        }



//        val submitData=currentFragment.getSubmitData()
        Log.d("tchPicUrl", "sendDataToNestedFragment: "+response.tchPicUrl)
        submitData.tchPicUrl=response.tchPicUrl
        viewModel.updateTeacherProfile(
            submitData,
            loginId = loginId.toString()
        ).observe(viewLifecycleOwner,{
            if (it.result == Config.RESULT_OK){
                Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setWorkData(submitData: TeacherProfileResponse, detailData: TeacherProfileResponse): TeacherProfileResponse {
        submitData.tchMainDepartment =detailData.tchMainDepartment
        submitData.tchCoeDepartment  =detailData.tchCoeDepartment
        submitData.tchState  =detailData.tchState
        submitData.tchHureDate  =detailData.tchHureDate
        submitData.tchSchDate  =detailData.tchSchDate
        submitData.tchOriginalUnit =detailData.tchOriginalUnit
        submitData.tchResignDate =detailData.tchResignDate
        submitData.tchStopDate =detailData.tchStopDate
        submitData.tchAppointDate  =detailData.tchAppointDate
        submitData.tchReinstateDate =detailData.tchReinstateDate

        submitData.tchEstablishment  =detailData.tchEstablishment
        submitData.tchKind   =detailData.tchKind
        submitData.tchKindIndustry  =detailData.tchKindIndustry
        submitData.tchKindDepartment   =detailData.tchKindDepartment
        submitData.tchFullPartPosition   =detailData.tchFullPartPosition
        submitData.tchPartAdmini   =detailData.tchPartAdmini
        submitData.tchAdminiJob    =detailData.tchAdminiJob


        submitData.tchSceWhemain_ther    =detailData.tchSceWhemain_ther
        submitData.tchScePurpose     =detailData.tchScePurpose
        submitData.tchSecUnit     =detailData.tchSecUnit
        return submitData
    }

    private fun setDetailData(submitData: TeacherProfileResponse, detailData: TeacherProfileResponse): TeacherProfileResponse {
        submitData.tchType=detailData.tchType
        submitData.tchRireYear =detailData.tchRireYear
        submitData.tchRireRank =detailData.tchRireRank
        submitData.tchHireNumber =detailData.tchHireNumber
        submitData.tchCertificateRank =detailData.tchCertificateRank
        submitData.tchCertificateNumber=detailData.tchCertificateNumber
        submitData.tchmain_licenseNumber =detailData.tchmain_licenseNumber
        submitData.tchEvaNumber =detailData.tchEvaNumber
        submitData.tch107PaySalary =detailData.tch107PaySalary
        submitData.tchFiestAssistant=detailData.tchFiestAssistant
        submitData.tchFullTime =detailData.tchFullTime
        submitData.tchComplyLaw  =detailData.tchComplyLaw
        submitData.tchTwoFour  =detailData.tchTwoFour

        return submitData
    }

    fun imageChooser() {

        // create an instance of the
        // intent of the type image
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT

        // pass the constant to compare it
        // with the returned requestCode
        resultLauncher.launch(Intent.createChooser(i, "Select Picture"))
    }
    var resultLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            val selectedImageUri: Uri? = data?.data

            if (null != selectedImageUri) {
                // update the preview image in the layout

                viewModel.uploadFile(getFile(requireView().context,selectedImageUri)
                    ,loginId.toString())
                    .observe(viewLifecycleOwner,{
                        if (it.result==Config.RESULT_OK){
                            view?.let { viewR ->
                                response.tchPicUrl=it.picUrl
                                Glide.with(viewR.context)
                                    .load( GlideUrl(it.picUrl))
                                    .apply(RequestOptions.bitmapTransform( CircleCrop()))
                                    .into(binding.imagePickButton)
                            }
                        }
                    })

            }
        }
    }

    fun getFile(mContext: Context, documentUri: Uri): File {
        val inputStream = mContext?.contentResolver?.openInputStream(documentUri)
        var file =  File("")
        inputStream.use { input ->
            file =
                File(mContext?.cacheDir, System.currentTimeMillis().toString()+".jpg")
            FileOutputStream(file).use { output ->
                val buffer =
                    ByteArray(4 * 1024) // or other buffer size
                var read: Int = -1
                while (input?.read(buffer).also {
                        if (it != null) {
                            read = it
                        }
                    } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
        }
        return file
    }


    fun showFragment(frag: Fragment, tag: String) {
        transaction = manager.beginTransaction()
        currrentFragment= frag as NestedBaseFragment
        currrentFragment.setResponse(response)
        if (manager.findFragmentByTag(tag) == null) {
            Log.d("showFragment", "showFragment: $tag")
            transaction.add(R.id.forFragment, frag, tag)

        } else {
            transaction.show(frag)
        }
    }

    fun hideFragment(hfrag_1: Fragment, hfrag_2: Fragment) {
        if (manager.findFragmentByTag(hfrag_1.tag) != null) transaction.hide(hfrag_1)
        if (manager.findFragmentByTag(hfrag_2.tag) != null) transaction.hide(hfrag_2)

        transaction.commit()
    }

    override fun onFragmentBackPressed() {
        // add code in super class when override
        Log.d("HomeFragment", "onFragmentBackPressed")
//        requireActivity()
//            .onBackPressedDispatcher
//            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
//                override fun handleOnBackPressed() {
//                    Log.d("HomeFragment", "Fragment back pressed invoked")
//                    // Do custom work here
//                    // if you want onBackPressed() to be called as normal afterwards
//
//
//                    if (isEnabled) {
//                        isEnabled = false
//                        requireActivity().onBackPressed()
//                    }
//                }
//            }
//            )
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}