package com.example.linteacher.ui.teacherdata.ui.experience

import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import com.example.linteacher.R
import com.example.linteacher.api.pojo.teacherdata.ExpAddData
import com.example.linteacher.api.pojo.teacherdata.ExpBaseData
import com.example.linteacher.api.pojo.teacherdata.ExpOriginData
import com.example.linteacher.databinding.ItemExpAddBinding
import com.example.linteacher.databinding.ItemExpOriginBinding
import com.example.linteacher.util.Config
import java.text.SimpleDateFormat
import java.util.*


class ExpAdapter(
    private var list: ArrayList<ExpBaseData>
    , val listener: ExperienceFragment.OnAddClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == Config.ADD_VIEW_TYPE){
            val itemBinding = ItemExpAddBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            AddViewHolder(itemBinding,parent.context)
        } else{
            val itemBinding = ItemExpOriginBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            OriginViewHolder(itemBinding)
        }
    }

    override fun onBindViewHolder(holder:RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        if (viewType == Config.ADD_VIEW_TYPE){
            (holder as AddViewHolder).bind(list.get(position) as ExpAddData,position,listener)
        } else{
            (holder as OriginViewHolder).bind(list.get(position) as ExpOriginData,position,listener)
        }


    }
    fun getDataList():ArrayList<ExpBaseData>{
        return list
    }
    fun setDataList(mDataList: ArrayList<ExpBaseData>) {
        list = mDataList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return list[position].itemType
    }

    class OriginViewHolder(private val binding:  ItemExpOriginBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            items: ExpOriginData,
            position: Int,
            listener: ExperienceFragment.OnAddClickListener
        ){


        }
    }

    class AddViewHolder(private val binding: ItemExpAddBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            items: ExpAddData,
            position: Int,
            listener: ExperienceFragment.OnAddClickListener
        ){

            setExpPfTime()
            setCategory()
            setMechanismsort()
            setDate()
            binding.deleteOrEditBtn.setOnClickListener {
                listener.onCancelClick(position)
            }
            binding.saveBtn.setOnClickListener {

                if(  validateData()){
                    var itemData=ExpAddData(
                        company=binding.expCompany.text.toString(),
                        job = binding.expJob.text.toString(),
                        startDate=binding.expStartDate.text.toString(),
                        endDate=binding.expEndDate.text.toString(),
                        expType=binding.expCategory.selectedItem.toString(),
                        coopAgency=binding.expMechanismsort.selectedItem.toString(),
                        isPublic=false,
                        isPartTime=binding.expPftime.selectedItem.toString()
                    )

                    listener.onSaveClick(itemData,position)
                }


            }

        }

        private fun setDate() {

            binding.expStartDate.setOnClickListener {
                datePicker(it)
            }
            binding.expEndDate.setOnClickListener {
                datePicker(it)
            }
        }

        fun datePicker(v: View) {
            val calendar: Calendar = Calendar.getInstance()
            val year: Int = calendar.get(Calendar.YEAR) //取得現在的日期年月日
            val month: Int = calendar.get(Calendar.MONTH)
            val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(v.context,
                { view, year, month, day ->
                    val calendar = Calendar.getInstance()
                    calendar[year, month] = day

                    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                    val strDate = format.format(calendar.time)
                    (v as EditText).setText(strDate)
                }, year, month, day
            ).show()
        }


        private fun setMechanismsort() {
            val spinner: Spinner = binding.expMechanismsort
            ArrayAdapter.createFromResource(
                context,
                R.array.exp_mechanismsort_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
                spinner.onItemSelectedListener = object :AdapterView.OnItemClickListener,
                    AdapterView.OnItemSelectedListener {
                    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    }
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    }
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }
                }
            }
        }

        private fun setCategory() {
            val spinner: Spinner = binding.expCategory
            ArrayAdapter.createFromResource(
                context,
                R.array.exp_expcategory_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
                spinner.onItemSelectedListener = object :AdapterView.OnItemClickListener,
                    AdapterView.OnItemSelectedListener {
                    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    }
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    }
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }
                }
            }

        }

        private fun setExpPfTime() {
            val spinner: Spinner = binding.expPftime
            ArrayAdapter.createFromResource(
                context,
                R.array.exp_pftime_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
                spinner.onItemSelectedListener = object :AdapterView.OnItemClickListener,
                    AdapterView.OnItemSelectedListener {
                    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    }
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    }
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }
                }
            }
        }

        private fun validateData() :Boolean{
           if (binding.expCompany.text.toString().isEmpty()){
               return false
           }
           else if (binding.expJob.text.toString().isEmpty()){
               return false
           }
           else if (binding.expPftime.selectedItem.toString().isEmpty()){
               return false
           }
           else if (binding.expCategory.selectedItem.toString().isEmpty()){
               return false
           }
           else if (binding.expMechanismsort.selectedItem.toString().isEmpty()){
               return false
           }
           else if (binding.expStartDate.text.toString().isEmpty()){
               return false
           }
           else if (binding.expEndDate.text.toString().isEmpty()){
               return false
           }
            else{
                return  true
            }

        }
    }
}