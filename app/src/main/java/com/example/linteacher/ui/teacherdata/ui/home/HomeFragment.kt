package com.example.linteacher.ui.teacherdata.ui.home

import android.R.attr.fragment
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.linteacher.api.pojo.teacherdata.profile.TeacherProfileResponse
import com.example.linteacher.databinding.FragmentHomeBinding
import com.example.linteacher.ui.teacherdata.ui.home.second.ProfileSecondFragment
import com.example.linteacher.ui.teacherdata.ui.home.second.TeacherDetailFragment
import com.example.linteacher.ui.teacherdata.ui.home.second.WorkInformationFragment
import com.example.linteacher.util.Config
import com.example.linteacher.util.preference.LoginPreferences
import java.io.File
import java.io.FileOutputStream


class HomeFragment : Fragment() {

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
    private  var response: TeacherProfileResponse= TeacherProfileResponse()
    private lateinit var loginPreferences:LoginPreferences
    private lateinit var profileSecondFragment:ProfileSecondFragment
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
        profileSecondFragment= ProfileSecondFragment()
        hideFragment(workInformationFragment,teacherDetailFragment)
        showFragment(profileSecondFragment,Config.PROFLE_SECOND_FRAGMENT)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences=LoginPreferences(view.context)
        binding.imagePickButton.setOnClickListener {
            imageChooser()
        }

        binding.teacherProfileButton.setOnClickListener {
            hideFragment(workInformationFragment,teacherDetailFragment)
            showFragment(profileSecondFragment,Config.PROFLE_SECOND_FRAGMENT)
        }
        binding.teacherDetailButton.setOnClickListener {
            hideFragment(profileSecondFragment,workInformationFragment)
            showFragment(teacherDetailFragment,Config.TEACHER_DETAIL_FRAGMENT)
        }
        binding.workInformationWork.setOnClickListener {
            hideFragment(profileSecondFragment,teacherDetailFragment)
            showFragment(workInformationFragment,Config.WORK_INFORM_FRAGMENT)
        }
        viewModel.getTeacherProfile(loginPreferences.getTeacherId())
            .observe(viewLifecycleOwner, {
                if (it.result == Config.RESULT_OK){
                    response=it.data
                    if (!it.data.tchPicUrl.isNullOrEmpty()){
                        Glide.with(view.context)
                            .load( GlideUrl(it.data.tchPicUrl))
                            .apply(RequestOptions.bitmapTransform( CircleCrop()))
                            .into(binding.imagePickButton)
                    }
                }
            })
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

                viewModel.uploadFile(getFile(requireView().context,selectedImageUri),loginPreferences.getTeacherId())
                    .observe(viewLifecycleOwner,{
                        if (it.result==Config.RESULT_OK){
                            view?.let { viewR ->
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

        if (!response.tchName.isNullOrEmpty()){
            val bundle = Bundle()
            bundle.putSerializable(tag,response )
            frag.arguments = bundle
        }

        if (manager.findFragmentByTag(tag) == null) {
            Log.d("showFragment", "showFragment: $tag")
            transaction.add(R.id.forFragment, frag, tag)

        } else {
            transaction.show(frag)
        }
    }

    fun hideFragment(frag: Fragment, frag2: Fragment) {
        if (manager.findFragmentByTag(frag.tag) != null) transaction.hide(frag)
        if (manager.findFragmentByTag(frag2.tag) != null) transaction.hide(frag2)

        transaction.commit()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}