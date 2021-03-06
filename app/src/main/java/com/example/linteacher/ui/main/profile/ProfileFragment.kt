package com.example.linteacher.ui.main.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.linteacher.R
import com.example.linteacher.databinding.FragmentProfileBinding
import com.example.linteacher.ui.admin.adminedituser.AdminEditActivity
import com.example.linteacher.ui.login.LoginActivity
import com.example.linteacher.ui.main.banneredit.BannerEditActivity
import com.example.linteacher.ui.managearticle.EditArticleActivity
import com.example.linteacher.ui.teacherdata.TeacherInformationFirstActivity
import com.example.linteacher.util.ActivityNavigator
import com.example.linteacher.util.Config
import com.example.linteacher.util.preference.LoginPreferences


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentProfileBinding
    private val factory = ProfileViewModelFactory(ProfileRepository())
    private val viewModel: ProfileViewModel by viewModels {
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
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loginPreferences = LoginPreferences(view.context)
//        if ( (loginPreferences.getTeacherId()?.length)!! >0 ){
//
//        }
//        else


        observeData()
        if (loginPreferences.getTeacherGrade().equals(Config.ADMIN)) {
            binding.gradeAArticle.visibility = View.VISIBLE
            binding.gradeABanner.visibility = View.VISIBLE
            binding.gradeAChoose.visibility = View.VISIBLE

            //
            binding.gradeBChoose.visibility = View.GONE
            binding.logout.visibility = View.VISIBLE
            binding.teacherCountry.text = "?????????"
            binding.imageView.setBackgroundResource(
                R.drawable.admin
            )

        } else if (loginPreferences.getTeacherGrade().equals(Config.TEACHER)) {
//            getData(loginPreferences)
            binding.gradeAArticle.visibility = View.GONE
            binding.gradeABanner.visibility = View.GONE
            binding.gradeAChoose.visibility = View.GONE
            //
            binding.gradeBChoose.visibility = View.VISIBLE
            binding.logout.visibility = View.VISIBLE
        } else {
            binding.gradeAArticle.visibility = View.GONE
            binding.gradeABanner.visibility = View.GONE
            binding.gradeAChoose.visibility = View.GONE
            //
            binding.gradeBChoose.visibility = View.GONE
            binding.logout.visibility = View.GONE
            //
            binding.login.visibility = View.VISIBLE
            binding.teacherCountry.text = "??????"
            binding.imageView.setBackgroundResource(
                R.drawable.apple_png
            )

        }

        adminChoose(loginPreferences)
    }

    private fun observeData() {
        viewModel.teacherLiveData
            .observe(viewLifecycleOwner, {
                binding.teacherCountry.text = it.tchName
                if (it.picUrl.isNotEmpty()) {
                    Glide.with(this)
                        .load(it.picUrl)
                        .apply(RequestOptions().override(100, 100))
                        .transform(CircleCrop())
                        .centerCrop()
                        .into(binding.imageView)
                } else {
                    binding.imageView.setImageResource(R.drawable.account)
                }
            })
    }

    private fun getData(loginPreferences: LoginPreferences) {
        viewModel.getTeacherData(loginPreferences.getLoginId())

    }

    private fun adminChoose(loginPreferences: LoginPreferences) {

        binding.teacherEditBtn.setOnClickListener {
            //???????????????(???loginId)
            val bundle = Bundle()
            Log.d("teacherEditBtn", "loginId: ${loginPreferences.getLoginId()}")
            bundle.putInt("loginId", loginPreferences.getLoginId().toInt())
            val i = Intent(activity, TeacherInformationFirstActivity::class.java)
            i.putExtras(bundle);
            startActivity(i)

            (activity as Activity?)!!.overridePendingTransition(0, 0)

//            getActivity()?.let { it1 ->
//                ActivityNavigator.startActivity(
//                    AdminEditActivity::class.java,
//                    it1
//                )
//            }
        }

        binding.login.setOnClickListener {
            //??????finish fragment
            //finish???????????????????
            getActivity()?.let { it1 ->
                ActivityNavigator.startActivity(
                    LoginActivity::class.java,
                    it1
                )
            }
        }

        binding.logout.setOnClickListener {
            //???loginId,teacherId,grade,????????????
            //??????
            loginPreferences.setTeacherGrade("")
            loginPreferences.setTeacherId("")
            loginPreferences.setLoginId("")
            //??????
            binding.gradeAArticle.visibility = View.GONE
            binding.gradeABanner.visibility = View.GONE
            binding.gradeAChoose.visibility = View.GONE
            //
            binding.gradeBChoose.visibility = View.GONE
            binding.logout.visibility = View.GONE
            //
            binding.login.visibility = View.VISIBLE
            binding.teacherCountry.text = "??????"

            Log.d("?????????", "???: ")

            binding.imageView.setBackgroundResource(
                R.drawable.apple_png
            )


            //???????????????framgent?
        }



        binding.manageAccount.setOnClickListener {
            //????????????
            getActivity()?.let { it1 ->
                ActivityNavigator.startActivity(
                    AdminEditActivity::class.java,
                    it1
                )
            }
        }

        binding.gradeAArticle.setOnClickListener {
            //??????
            ActivityNavigator.startActivity(
                EditArticleActivity::class.java,
                requireActivity()
            )
        }
        binding.gradeABanner.setOnClickListener {
            //??????banner
            ActivityNavigator.startActivity(
                BannerEditActivity::class.java,
                requireActivity()
            )
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}