package com.example.linteacher.ui.main.announce

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.linteacher.R
import com.example.linteacher.api.pojo.banner.BannerGetResponse
import com.example.linteacher.databinding.FragmentAnnounceBinding
import com.example.linteacher.ui.teacherdata.ui.experience.ExpRepository
import com.example.linteacher.ui.teacherdata.ui.experience.ExpViewModel
import com.example.linteacher.ui.teacherdata.ui.experience.ExpViewModelFactory
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AnnounceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AnnounceFragment : Fragment() {

    private var _binding: FragmentAnnounceBinding? = null
    private val binding get() = _binding!!
    private val importantContentAdapter by lazy { ImportantContentAdapter(arrayListOf()) }
    private val contentAdapter by lazy { ContentAdapter(arrayListOf()) }

    private val factory = AnnounceViewFactory(AnnounceRepository())
    private val viewModel: AnnounceViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnnounceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleView()
        observeBannerList()
        observeImportantList(0)
        observeLatest(0)


    }


    private fun observeBannerList() {
        viewModel.getBannerList().observe(
            viewLifecycleOwner,
            { t ->
                val bannerList = mutableListOf<Content.BannerResponse>()
                for (item in t.bannerResponseList) {
                    bannerList.add(
                        Content.BannerResponse(
                            articleId = item.articleId,
                            picUrl = item.picUrl,
                            picId = item.picId
                        )
                    )
                }

                binding.banner.apply {
                    addBannerLifecycleObserver(activity)
                    setIndicator(CircleIndicator(activity))
                    setAdapter(object : BannerImageAdapter<Content.BannerResponse>(bannerList) {
                        override fun onBindView(
                            holder: BannerImageHolder,
                            data: Content.BannerResponse,
                            position: Int,
                            size: Int
                        ) {
                            activity?.let {
                                Glide.with(it)
                                    .load(data.picUrl)
                                    .into(holder.imageView)
                            }
                        }
                    })
                }
            }
        )
    }

    private fun observeImportantList(page: Int) {

        viewModel.getImportantList(page).observe(
            viewLifecycleOwner,
            { t ->
                val importantLst = mutableListOf<Content.ImportantInnerAnnounce>()
                for (item in t.responses) {
                    importantLst.add(
                        Content.ImportantInnerAnnounce(
                            articleImportant = item.articleImportant,
                            articleTag = item.articleTag,
                            articleTitle = item.articleTitle,
                            articleContent = item.articleContent,
                            modifyDate = item.modifyDate,
                        )
                    )
                }
                val mainView = binding.importantContent
                mainView.removeAllViews()
                for (a in importantLst) {
                    val inflater = layoutInflater
                    val view: View = inflater.inflate(R.layout.item_carsoul, null)
                    view.findViewById<TextView>(R.id.articleTitle).text = a.articleTitle
                    view.findViewById<TextView>(R.id.articleTag).text = a.articleTag
                    view.findViewById<TextView>(R.id.modifyDate).text = a.modifyDate
                    mainView.addView(view)
                }
            }
        )

    }


    private fun observeLatest(page: Int) {
        viewModel.getLatestList(page).observe(
            viewLifecycleOwner,
            { t ->
                val responseLst = arrayListOf<Content.Response>()
                for (item in t.responses) {
                    responseLst.add(
                        Content.Response(
                            articleContent = item.articleContent,
                            articleId = item.articleId,
                            articleImportant = item.articleImportant,
                            articleTag = item.articleTag,
                            articleTitle = item.articleTitle,
                            modifyDate = item.modifyDate,
                        )
                    )
                }
                contentAdapter.setDataList(responseLst)
            }
        )
    }

    private fun initRecycleView() {

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        layoutManager.reverseLayout = false
        binding.content.layoutManager = layoutManager
        binding.content.adapter = contentAdapter
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AnnounceFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AnnounceFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}