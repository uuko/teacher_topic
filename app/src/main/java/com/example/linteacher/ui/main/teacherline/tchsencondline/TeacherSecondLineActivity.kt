package com.example.linteacher.ui.main.teacherline.tchsencondline

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.linteacher.R
import com.example.linteacher.api.pojo.teacherline.TeacherSecondLineResponse
import com.example.linteacher.databinding.ActivityTeacherSecondLineBinding
import com.example.linteacher.ui.main.teacherline.tchsencondline.adapter.*


class TeacherSecondLineActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeacherSecondLineBinding
    private val factory = TeacherSecondViewModelFactory(TeacherSecondRepository())
    private val viewModel: TeacherSecondViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeacherSecondLineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val response = intent.getSerializableExtra("item") as Int
        viewModel.getTeacherLineData(response.toString())
            .observe(this, {
                initBinding(it)
                dynamicAddViews(it)
            })


//        initBinding(response)
//        initRecycleView()

    }

    private fun dynamicAddViews(list: TeacherSecondLineResponse) {

        val innerContent = binding.innerContent as ViewGroup
        //實務經驗
        if (list.oneDashTwoList.isNotEmpty()) {
            Log.d("dynamicAddViews", "dynamicAddViews: ")
            val vi =
                applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val v: View = vi.inflate(R.layout.inner_dynamic_teacher_line, null)
            v.findViewById<TextView>(R.id.title).text = "實務經驗"
            val recycleView = v.findViewById<RecyclerView>(R.id.recyclerView)
            val layoutManager = LinearLayoutManager(this)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager.reverseLayout = false
            recycleView.layoutManager = layoutManager
            val adapter = OneDashTwoAdapter(list.oneDashTwoList)
            recycleView.adapter = adapter
            innerContent.addView(
                v,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }


        if (list.proList.isNotEmpty()) {
            Log.d("dynamicAddViews", "校外服務經驗: ")
            val vi =
                applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val v: View = vi.inflate(R.layout.inner_dynamic_teacher_line, null)
            v.findViewById<TextView>(R.id.title).text = "校外服務經驗"
            val recycleView = v.findViewById<RecyclerView>(R.id.recyclerView)
            val layoutManager = LinearLayoutManager(this)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager.reverseLayout = false
            recycleView.layoutManager = layoutManager
            val adapter = ProfeServiceAdapter(list.proList)
            recycleView.adapter = adapter
            innerContent.addView(
                v,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }

        if (list.licList.isNotEmpty()) {
            Log.d("dynamicAddViews", "證照:: ")
            val vi =
                applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val v: View = vi.inflate(R.layout.inner_dynamic_teacher_line, null)
            v.findViewById<TextView>(R.id.title).text = "證照"
            val recycleView = v.findViewById<RecyclerView>(R.id.recyclerView)
            val layoutManager = LinearLayoutManager(this)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager.reverseLayout = false
            recycleView.layoutManager = layoutManager
            val adapter = LicAdapter(list.licList)
            recycleView.adapter = adapter
            innerContent.addView(
                v,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }

        if (list.eventList.isNotEmpty()) {
            Log.d("dynamicAddViews", "學術活動: ")
            val vi =
                applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val v: View = vi.inflate(R.layout.inner_dynamic_teacher_line, null)
            v.findViewById<TextView>(R.id.title).text = "學術活動"
            val recycleView = v.findViewById<RecyclerView>(R.id.recyclerView)
            val layoutManager = LinearLayoutManager(this)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager.reverseLayout = false
            recycleView.layoutManager = layoutManager
            val adapter = AcadeMicEventsAdapter(list.eventList)
            recycleView.adapter = adapter
            innerContent.addView(
                v,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }


        if (list.awardsList.isNotEmpty()) {
            Log.d("dynamicAddViews", "獎項或榮譽:: ")
            val vi =
                applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val v: View = vi.inflate(R.layout.inner_dynamic_teacher_line, null)
            v.findViewById<TextView>(R.id.title).text = "獎項或榮譽"
            val recycleView = v.findViewById<RecyclerView>(R.id.recyclerView)
            val layoutManager = LinearLayoutManager(this)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager.reverseLayout = false
            recycleView.layoutManager = layoutManager
            val adapter = TchAwardsAdapter(list.awardsList)
            recycleView.adapter = adapter
            innerContent.addView(
                v,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }

        if (list.tchinfList.isNotEmpty()) {
            Log.d("dynamicAddViews", "專書:: ")
            val vi =
                applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val v: View = vi.inflate(R.layout.inner_dynamic_teacher_line, null)
            v.findViewById<TextView>(R.id.title).text = "專書"
            val recycleView = v.findViewById<RecyclerView>(R.id.recyclerView)
            val layoutManager = LinearLayoutManager(this)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager.reverseLayout = false
            recycleView.layoutManager = layoutManager
            val adapter = TchInfAdapter(list.tchinfList)
            recycleView.adapter = adapter
            innerContent.addView(
                v,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }

        if (list.theList.isNotEmpty()) {
            Log.d("dynamicAddViews", "期刊論文:: ")
            val vi =
                applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val v: View = vi.inflate(R.layout.inner_dynamic_teacher_line, null)
            v.findViewById<TextView>(R.id.title).text = "期刊論文"
            val recycleView = v.findViewById<RecyclerView>(R.id.recyclerView)
            val layoutManager = LinearLayoutManager(this)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager.reverseLayout = false
            recycleView.layoutManager = layoutManager
            val adapter = TchTheAdapter(list.theList)
            recycleView.adapter = adapter
            innerContent.addView(
                v,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }

        if (list.govList.isNotEmpty()) {
            Log.d("dynamicAddViews", "計畫案產學:: ")
            val vi =
                applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val v: View = vi.inflate(R.layout.inner_dynamic_teacher_line, null)
            v.findViewById<TextView>(R.id.title).text = "計畫案產學"
            val recycleView = v.findViewById<RecyclerView>(R.id.recyclerView)
            val layoutManager = LinearLayoutManager(this)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager.reverseLayout = false
            recycleView.layoutManager = layoutManager
            val adapter = GovAdapter(list.govList)
            recycleView.adapter = adapter
            innerContent.addView(
                v,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }

        if (list.disList.isNotEmpty()) {
            Log.d("dynamicAddViews", "研討會:: ")
            val vi =
                applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val v: View = vi.inflate(R.layout.inner_dynamic_teacher_line, null)
            v.findViewById<TextView>(R.id.title).text = "研討會"
            val recycleView = v.findViewById<RecyclerView>(R.id.recyclerView)
            val layoutManager = LinearLayoutManager(this)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager.reverseLayout = false
            recycleView.layoutManager = layoutManager
            val adapter = DisAdapter(list.disList)
            recycleView.adapter = adapter
            innerContent.addView(
                v,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }


    }


    private fun initBinding(response: TeacherSecondLineResponse) {
        if (response.tchPicUrl != "") {
            Glide.with(binding.root)
                .load(response.tchPicUrl)
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