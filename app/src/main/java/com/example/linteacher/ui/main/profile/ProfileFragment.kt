package com.example.linteacher.ui.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.linteacher.databinding.FragmentProfileBinding
import com.example.linteacher.ui.admin.adminedituser.AdminEditActivity
import com.example.linteacher.ui.managearticle.EditArticleActivity
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
        val loginPreferences=LoginPreferences(view.context)
        if ( (loginPreferences.getTeacherId()?.length)!! >0 ){

        }
        else if (loginPreferences.getTeacherGrade().equals(Config.ADMIN)){

        }
        else if (loginPreferences.getTeacherGrade().equals(Config.TEACHER)){

        }

        adminChoose()
    }

    private fun adminChoose() {
        binding.manageAccount.setOnClickListener {
            getActivity()?.let { it1 ->
                ActivityNavigator.startActivity(
                    AdminEditActivity::class.java,
                    it1
                )
            }
        }

        binding.gradeAArticle.setOnClickListener {
            ActivityNavigator.startActivity(
                EditArticleActivity::class.java,
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