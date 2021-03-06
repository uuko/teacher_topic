package com.example.linteacher.ui.main.teacherline.tchsencondline

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.linteacher.R
import com.example.linteacher.api.pojo.teacherline.TeacherSecondLineResponse
import com.example.linteacher.databinding.ActivityTeacherSecondLineBinding
import com.example.linteacher.ui.main.teacherline.tchsencondline.adapter.*


class TeacherSecondLineActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeacherSecondLineBinding

//    private val factory = TeacherSecondViewModelFactory(TeacherSecondRepository())
//    private var viewModel: TeacherSecondViewModel by viewModels {
//        factory
//    }

    private lateinit var viewModel:TeacherSecondViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeacherSecondLineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val response = intent.getSerializableExtra("item") as Int
        val factory = TeacherSecondViewModelFactory(TeacherSecondRepository(),response)
        viewModel = ViewModelProvider(this,factory).get(TeacherSecondViewModel::class.java)

//        initData(response)
        observeData()
        binding.mSwipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            binding.mSwipeRefreshLayout.isRefreshing = false
            initData(response)
        })


    }

    private fun initData(response: Int) {
        viewModel.getTeacherLineData(response.toString())

    }

    private fun observeData(){
        viewModel.teacherLineLiveData
        .observe(this, {
            Log.d("lineOnetec", "initData: " + it.licList)
            initBinding(it)
            dynamicAddViews(it)
        })
    }

    var dialogContentVisible = false
    private fun dynamicAddViews(list: TeacherSecondLineResponse) {

        val relativeNameList = ArrayList<ViewRelativeName>()
        relativeNameList.add(ViewRelativeName("????????????", R.id.top_constraint))
        val innerContent = binding.innerContent as ViewGroup
        val dialogContent = binding.dialogContent as ViewGroup
        innerContent.removeAllViews()
        dialogContent.removeAllViews()
        //????????????
        if (list.oneDashTwoList.isNotEmpty()) {
            Log.d("dynamicAddViews", "dynamicAddViews: ")
            val vi =
                applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val v: View = vi.inflate(R.layout.inner_dynamic_teacher_line, null)

            v.findViewById<TextView>(R.id.title).text = "????????????"
            val recycleView = v.findViewById<RecyclerView>(R.id.recyclerView)
            val layoutManager = LinearLayoutManager(this)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager.reverseLayout = false
            recycleView.layoutManager = layoutManager
            val adapter = OneDashTwoAdapter()
            recycleView.adapter = adapter
            adapter.submitList(list.oneDashTwoList)
            val id = ViewCompat.generateViewId();
            v.id = id
            relativeNameList.add(ViewRelativeName("????????????", id))
            innerContent.addView(
                v,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }
        if (list.proList.isNotEmpty()) {
            Log.d("dynamicAddViews", "??????????????????: ")
            val vi =
                applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val v: View = vi.inflate(R.layout.inner_dynamic_teacher_line, null)
            v.findViewById<TextView>(R.id.title).text = "??????????????????"
            val recycleView = v.findViewById<RecyclerView>(R.id.recyclerView)
            val layoutManager = LinearLayoutManager(this)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager.reverseLayout = false
            recycleView.layoutManager = layoutManager
            val adapter = ProfeServiceAdapter()

            recycleView.adapter = adapter
            adapter.setDataList(list.proList)
            val id = ViewCompat.generateViewId();
            v.id = id
            relativeNameList.add(ViewRelativeName("??????????????????", id))
            innerContent.addView(
                v,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }
        if (list.licList.isNotEmpty()) {
            Log.d("dynamicAddViews", "??????:: ")
            val vi =
                applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val v: View = vi.inflate(R.layout.inner_dynamic_teacher_line, null)
            v.findViewById<TextView>(R.id.title).text = "??????"
            val recycleView = v.findViewById<RecyclerView>(R.id.recyclerView)
            val layoutManager = LinearLayoutManager(this)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager.reverseLayout = false
            recycleView.layoutManager = layoutManager
            val adapter = LicAdapter()

            recycleView.adapter = adapter
            adapter.setDataList(list.licList)
            val id = ViewCompat.generateViewId();
            v.id = id
            relativeNameList.add(ViewRelativeName("??????", id))
            innerContent.addView(
                v,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }
        if (list.eventList.isNotEmpty()) {
            Log.d("dynamicAddViews", "????????????: ")
            val vi =
                applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val v: View = vi.inflate(R.layout.inner_dynamic_teacher_line, null)
            v.findViewById<TextView>(R.id.title).text = "????????????"
            val recycleView = v.findViewById<RecyclerView>(R.id.recyclerView)
            val layoutManager = LinearLayoutManager(this)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager.reverseLayout = false
            recycleView.layoutManager = layoutManager
            val adapter = AcadeMicEventsAdapter()
            recycleView.adapter = adapter
            adapter.setDataList(list.eventList)
            val id = ViewCompat.generateViewId();
            v.id = id
            relativeNameList.add(ViewRelativeName("????????????", id))
            innerContent.addView(
                v,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }
        if (list.awardsList.isNotEmpty()) {
            Log.d("dynamicAddViews", "???????????????:: ")
            val vi =
                applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val v: View = vi.inflate(R.layout.inner_dynamic_teacher_line, null)
            v.findViewById<TextView>(R.id.title).text = "???????????????"
            val recycleView = v.findViewById<RecyclerView>(R.id.recyclerView)
            val layoutManager = LinearLayoutManager(this)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager.reverseLayout = false
            recycleView.layoutManager = layoutManager
            val adapter = TchAwardsAdapter()
            recycleView.adapter = adapter
            adapter.setDataList(list.awardsList)
            val id = ViewCompat.generateViewId();
            v.id = id
            relativeNameList.add(ViewRelativeName("???????????????", id))
            innerContent.addView(
                v,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }
        if (list.tchinfList.isNotEmpty()) {
            Log.d("dynamicAddViews", "??????:: ")
            val vi =
                applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val v: View = vi.inflate(R.layout.inner_dynamic_teacher_line, null)
            v.findViewById<TextView>(R.id.title).text = "??????"
            val recycleView = v.findViewById<RecyclerView>(R.id.recyclerView)
            val layoutManager = LinearLayoutManager(this)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager.reverseLayout = false
            recycleView.layoutManager = layoutManager
            val adapter = TchInfAdapter()
            recycleView.adapter = adapter
            adapter.setDataList(list.tchinfList)
            val id = ViewCompat.generateViewId();
            v.id = id
            relativeNameList.add(ViewRelativeName("??????", id))
            innerContent.addView(
                v,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }
        if (list.theList.isNotEmpty()) {
            Log.d("dynamicAddViews", "?????????????????????:: ")
            val vi =
                applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val v: View = vi.inflate(R.layout.inner_dynamic_teacher_line, null)
            v.findViewById<TextView>(R.id.title).text = "?????????????????????"
            val recycleView = v.findViewById<RecyclerView>(R.id.recyclerView)
            val layoutManager = LinearLayoutManager(this)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager.reverseLayout = false
            recycleView.layoutManager = layoutManager
            val adapter = TchTheAdapter()
            adapter.setDataList(list.theList)
            recycleView.adapter = adapter
            val id = ViewCompat.generateViewId();
            v.id = id
            relativeNameList.add(ViewRelativeName("?????????????????????", id))
            innerContent.addView(
                v,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }
        if (list.govList.isNotEmpty()) {
            Log.d("dynamicAddViews", "???????????????:: ")
            val vi =
                applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val v: View = vi.inflate(R.layout.inner_dynamic_teacher_line, null)
            v.findViewById<TextView>(R.id.title).text = "???????????????"
            val recycleView = v.findViewById<RecyclerView>(R.id.recyclerView)
            val layoutManager = LinearLayoutManager(this)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager.reverseLayout = false
            recycleView.layoutManager = layoutManager
            val adapter = GovAdapter()
            recycleView.adapter = adapter
            adapter.setDataList(list.govList)
            val id = ViewCompat.generateViewId();
            v.id = id
            relativeNameList.add(ViewRelativeName("???????????????", id))
            innerContent.addView(
                v,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }
        if (list.disList.isNotEmpty()) {
            Log.d("dynamicAddViews", "??????????????????:: ")
            val vi =
                applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val v: View = vi.inflate(R.layout.inner_dynamic_teacher_line, null)
            v.findViewById<TextView>(R.id.title).text = "??????????????????"
            val recycleView = v.findViewById<RecyclerView>(R.id.recyclerView)
            val layoutManager = LinearLayoutManager(this)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager.reverseLayout = false
            recycleView.layoutManager = layoutManager
            val adapter = DisAdapter()
            recycleView.adapter = adapter
            adapter.setDataList(list.disList)
            val id = ViewCompat.generateViewId();
            v.id = id
            relativeNameList.add(ViewRelativeName("??????????????????", id))
            innerContent.addView(
                v,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }

        binding.button.setOnClickListener {
            dialogContentVisible = !dialogContentVisible
            if (dialogContentVisible) {
                dialogContent.visibility = View.VISIBLE
                Log.d("dynamicAddViews", "dynamicAddViews: ${relativeNameList.size}")
                dialogContent.removeAllViews()
                for (name in relativeNameList) {
                    val text = TextView(binding.root.context)
                    text.text = name.name
                    text.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black));
                    text.setPadding(20, 20, 20, 20)
                    dialogContent.addView(text)
                    text.setOnClickListener {
                        Log.d("dynamicAddViews", "setOnClickListener: ${name.name}")
                        if (text.text == "????????????") {
                            scrollToView(binding.topConstraint)
                        } else {
                            scrollToView(binding.innerContent.findViewById(name.id))
                        }

                        dialogContent.visibility = View.GONE
                    }
                }

            } else dialogContent.visibility = View.GONE
        }

    }

    fun scrollToView(view: View) {

        var fView = view
        var vTop = fView.top

        while (fView.parent !is NestedScrollView) {
            fView = fView.parent as View
            vTop += fView.top
        }
        Log.d("scrollToView", "scrollToView: haha")
        val scrollPosition = vTop

        Handler(Looper.getMainLooper()).post {
            binding.contentScrollview.smoothScrollTo(
                0,
                scrollPosition
            )
        }

    }

    private fun initBinding(response: TeacherSecondLineResponse) {
        if (response.tchPicUrl != "") {
            Glide.with(binding.root)
                .load(response.tchPicUrl)
                .centerCrop()
                .into(binding.tchPicUrl)
        }
        binding.tchName.text = response.tchName
        binding.tchNameEN.text = response.tchNameEN
        binding.introduce.text = response.introduce
        binding.tchMainDepartment.text = response.tchMainDepartment
        binding.tchRireRank.text = response.tchRireRank
        binding.tchSchool.text = response.tchSchool
        binding.tchDiploma.text = response.tchDiploma
        binding.tchDepartment.text = response.tchDepartment
        binding.email.text = response.eMail
    }
}