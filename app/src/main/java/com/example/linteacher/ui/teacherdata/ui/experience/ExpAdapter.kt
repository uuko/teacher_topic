package com.example.linteacher.ui.teacherdata.ui.experience

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.drawable.Icon
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import com.example.linteacher.R
import com.example.linteacher.api.pojo.teacherdata.exp.ui.ExpAddData
import com.example.linteacher.api.pojo.teacherdata.exp.ui.ExpBaseData
import com.example.linteacher.api.pojo.teacherdata.exp.ui.ExpEditData
import com.example.linteacher.api.pojo.teacherdata.exp.ui.ExpOriginData
import com.example.linteacher.databinding.ItemExpAddBinding
import com.example.linteacher.databinding.ItemExpEditBinding
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
        }
        else if (viewType == Config.EdIT_VIEW_TYPE){
            val itemBinding = ItemExpEditBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            EditViewHolder(itemBinding,parent.context)
        } else{
            val itemBinding = ItemExpOriginBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            OriginViewHolder(itemBinding,parent.context)
        }
    }

    override fun onBindViewHolder(holder:RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        if (viewType == Config.ADD_VIEW_TYPE){
            (holder as AddViewHolder).bind(list.get(position) as ExpAddData,position,listener)
        }
        else if (viewType == Config.EdIT_VIEW_TYPE){
            (holder as EditViewHolder).bind(list.get(position) as ExpEditData,position,listener)
        }
        else{
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
    fun setOneData(mDataList: ArrayList<ExpBaseData>, position: Int) {
        list = mDataList
        notifyItemChanged(position)
    }
    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return list[position].itemType
    }

    class OriginViewHolder(private val binding:  ItemExpOriginBinding,private val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            items: ExpOriginData,
            position: Int,
            listener: ExperienceFragment.OnAddClickListener
        ){
            binding.positionId.text=position.toString()
            binding.expCompany.text=items.company
            binding.expCategory.text=compareCategory(items.expType)
            binding.expJob.text=items.job
            binding.expMechanismsort.text=compareMachanisSort(items.coopAgency)
            binding.expStartDate.text=items.startDate
            binding.expEndDate.text=items.endDate


            binding.editButton.setOnClickListener {
                listener.onEditClick(items,position)
            }
            binding.tick.setOnClickListener {
                Log.d("clickaaa", "click:tick ")
                items.public  = !items.public
                listener.onChangeVisibleClick(items, position)
            }

            if(items.public==true)
            {
                binding.tick.setImageIcon(Icon.createWithResource(context ,R.mipmap.ic_launcher))
                Log.d("tickkkkkk", "勾勾 "+position)
            }else
            {
                binding.tick.setImageIcon(Icon.createWithResource(context ,R.mipmap.ic_launcher2))
                Log.d("tickkkkkk", "沒勾勾 "+position)

            }
        }
        fun compareCategory(company:String):String{
            val array= context.resources.getStringArray(R.array.exp_expcategory_array)
            val arrayEn= context.resources.getStringArray(R.array.exp_expcategory_array_en)
            for (i in array.indices){
                if (company == array[i]){
                    return arrayEn[i]
                }
            }
            return arrayEn[0]
        }

        fun compareMachanisSort(company:String):String{
            val array= context.resources.getStringArray(R.array.exp_mechanismsort_array)
            val arrayEn= context.resources.getStringArray(R.array.exp_mechanismsort_array_en)
            for (i in array.indices){
                if (company == array[i]){
                    return arrayEn[i]
                }
            }
            return arrayEn[0]
        }
    }
    class EditViewHolder(private val binding:  ItemExpEditBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            items: ExpEditData,
            position: Int,
            listener: ExperienceFragment.OnAddClickListener
        )
            {
                setExpPfTime(items.isPartTime)
                setCategory()
                setMechanismsort()
                setDate()
            binding.expCompany.setText(items.company)
            binding.expCategory.setSelection( changeExpCateGory(items.expType),false)
            binding.expJob.setText(items.job)
            binding.expMechanismsort.setSelection( changeExpMechan(items.coopAgency),false)
            binding.expStartDate.setText(items.startDate)
            binding.expEndDate.setText(items.endDate)
            binding.expPftime.setSelection(changeExpPf(items.isPartTime))


            binding.deleteBtn.setOnClickListener {
                listener.onDeleteClick(items.expNumber,position)
            }

            binding.cancelButton.setOnClickListener {
                listener.onEditCancelClick(position,items)
            }
            binding.saveBtn.setOnClickListener {

                if(  validateData()){
                    val itemData= ExpEditData(
                        expNumber=items.expNumber,
                        company=binding.expCompany.text.toString(),
                        job = binding.expJob.text.toString(),
                        startDate=binding.expStartDate.text.toString(),
                        endDate=binding.expEndDate.text.toString(),
                        expType=getExpCateGory(binding.expCategory.selectedItem.toString()),
                        coopAgency=getExpMeachen(binding.expMechanismsort.selectedItem.toString()),
                        public=items.public,
                        isPartTime=binding.expPftime.selectedItem.toString()
                    )

                    listener.onEditSaveClick(itemData,position)
                }


            }

        }

        private fun changeExpPf(partTime: String): Int {
            val array= context.resources.getStringArray(R.array.exp_pftime_array)
            for (i in array.indices){
                if (partTime == array[i]){
                    return i
                }
            }
            return 0
        }

        private fun changeExpMechan(coopAgency: String): Int {
            val array= context.resources.getStringArray(R.array.exp_mechanismsort_array)
            for (i in array.indices){
                if (coopAgency == array[i]){
                    return i
                }
            }
            return 0
        }
        private fun getExpMeachen(expType: String):String {
            val array= context.resources.getStringArray(R.array.exp_mechanismsort_array_en)
            val arrayCN= context.resources.getStringArray(R.array.exp_mechanismsort_array)
            for (i in array.indices){
                if (expType == array[i]){
                    return arrayCN[i]
                }
            }
            return arrayCN[0]
        }
        private fun changeExpCateGory(expType: String):Int {
           val array= context.resources.getStringArray(R.array.exp_expcategory_array)

            for (i in array.indices){
                if (expType == array[i]){
                    return i
                }
            }
            return 0
        }
        private fun getExpCateGory(expType: String):String {
            val array= context.resources.getStringArray(R.array.exp_expcategory_array_en)
            val arrayCN= context.resources.getStringArray(R.array.exp_expcategory_array)
            for (i in array.indices){
                if (expType == array[i]){
                    return arrayCN[i]
                }
            }
            return arrayCN[0]
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
                R.array.exp_mechanismsort_array_en,
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
                R.array.exp_expcategory_array_en,
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

        private fun setExpPfTime(partTime: String) {
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
                    var itemData= ExpAddData(
                        company=binding.expCompany.text.toString(),
                        job = binding.expJob.text.toString(),
                        startDate=binding.expStartDate.text.toString(),
                        endDate=binding.expEndDate.text.toString(),
                        expType=binding.expCategory.selectedItem.toString(),
                        coopAgency=binding.expMechanismsort.selectedItem.toString(),
                        public=false,
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