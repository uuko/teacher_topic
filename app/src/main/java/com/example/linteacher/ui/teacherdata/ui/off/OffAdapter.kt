package com.example.linteacher.ui.teacherdata.ui.off

import android.content.Context
import android.graphics.drawable.Icon
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.linteacher.R
import com.example.linteacher.api.pojo.teacherdata.off.ui.OffAddData
import com.example.linteacher.api.pojo.teacherdata.off.ui.OffBaseData
import com.example.linteacher.api.pojo.teacherdata.off.ui.OffEditData
import com.example.linteacher.api.pojo.teacherdata.off.ui.OffOriginData
import com.example.linteacher.databinding.*
import com.example.linteacher.util.BaseViewHolder
import com.example.linteacher.util.Config
import java.util.*

class OffAdapter(
    private var list: ArrayList<OffBaseData>, val listener: OffInterface.View
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun setDataList(mDataList: ArrayList<OffBaseData>) {
        list = mDataList
        notifyDataSetChanged()
    }

    fun getDataList(): ArrayList<OffBaseData> {
        return list
    }

    fun setOneData(mDataList: ArrayList<OffBaseData>, position: Int) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Config.ADD_VIEW_TYPE) {
            val itemBinding =
                ItemOffAddBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            AddViewHolder(itemBinding, parent.context)
        } else if (viewType == Config.EdIT_VIEW_TYPE) {
            val itemBinding =
                ItemOffEditBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            EditViewHolder(itemBinding, parent.context)
        } else {
            val itemBinding =
                ItemOffOriginBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            OriginViewHolder(itemBinding, parent.context)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        if (viewType == Config.ADD_VIEW_TYPE) {
            (holder as AddViewHolder).bind(list.get(position) as OffAddData, position, listener)
        } else if (viewType == Config.EdIT_VIEW_TYPE) {
            (holder as EditViewHolder).bind(list.get(position) as OffEditData, position, listener)
        } else {
            (holder as OriginViewHolder).bind(
                list.get(position) as OffOriginData,
                position,
                listener
            )
        }
    }

    class OriginViewHolder(
        private val binding: ItemOffOriginBinding,
        private val context: Context
    ) : BaseViewHolder(binding.root) {
        fun bind(
            items: OffOriginData,
            position: Int,
            listener: OffInterface.View
        ) {
            binding.proVendor.text = items.proVendor
            binding.proNature.text = items.proNature
            binding.editButton.setOnClickListener {
                listener.onEditClick(items, position)
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

    }

    class EditViewHolder(private val binding: ItemOffEditBinding, private val context: Context) :
        BaseViewHolder(binding.root) {
        fun bind(
            items: OffEditData,
            position: Int,
            listener: OffInterface.View
        ) {
            binding.proVendor.setText(items.proVendor)
            binding.proCaseNumber.setText(items.proCaseNumber)
            binding.proCaseName.setText(items.proCaseName)
            binding.proRebate.setText(items.proRebate)
            binding.proStartDate.setText(items.proStartDate)
            binding.proStopDate.setText(items.proStopDate)
//            proRebate
            binding.proStartDate.setOnClickListener {
                datePicker(binding.proStartDate)
            }
            binding.proStopDate.setOnClickListener {
                datePicker(binding.proStopDate)
            }
//            proStartDate
//            proCaseName
//            proContent
            bindSpinnerAdapter(
                R.array.tch_not_en,
                binding.proContent,
                items.proContent.toString(),
                context
            )
            bindSpinnerAdapter(
                R.array.pro_pronature_array_en,
                binding.proNature,
                items.proNature.toString(),
                context
            )
            bindSpinnerAdapter(
                R.array.tch_abor_en,
                binding.proSign,
                items.proSign.toString(),
                context
            )



            binding.deleteBtn.setOnClickListener {
                listener.onDeleteClick(items.proId, position)
            }

            binding.cancelButton.setOnClickListener {
                listener.onEditCancelClick(position, items)
            }


            binding.saveBtn.setOnClickListener {

                if (validateData()) {

                    var itemData = OffEditData(
                        proVendor = binding.proVendor.text.toString(),

                        proId = items.proId,

                        proCaseNumber = items.proCaseNumber,
                        proCaseName = items.proCaseName,

                        proStartDate = items.proStartDate,
                        proStopDate = items.proStopDate,
                        proRebate = items.proRebate,

                        proSign = items.proSign,
                        proContent = items.proContent,
                        public = items.public,
                        proNature = binding.proVendor.text.toString(),
                    )

                    if (binding.proVendor.text.isNotEmpty()) {
                        itemData.proVendor = binding.proVendor.text.toString()
                    }
                    if (binding.proCaseNumber.text.isNotEmpty()) {
                        itemData.proCaseNumber = binding.proCaseNumber.text.toString()
                    }
                    if (binding.proCaseName.text.isNotEmpty()) {
                        itemData.proCaseName = binding.proCaseName.text.toString()
                    }
                    if (binding.proStartDate.text.isNotEmpty()) {
                        itemData.proStartDate = binding.proStartDate.text.toString()
                    }
                    if (binding.proStopDate.text.isNotEmpty()) {
                        itemData.proStopDate = binding.proStopDate.text.toString()
                    }
                    if (binding.proRebate.text.isNotEmpty()) {
                        itemData.proRebate = binding.proRebate.text.toString()
                    }
                    if (binding.proNature.selectedItemPosition != -1) {
                        itemData.proNature =
                            getResponseSpinner(
                                R.array.pro_pronature_array_en,
                                binding.proNature,
                                context
                            )
                    }
                    if (binding.proContent.selectedItemPosition != -1) {
                        itemData.proContent =
                            getResponseSpinner(
                                R.array.tch_not_en,
                                binding.proContent,
                                context
                            )
                    }
                    if (binding.proSign.selectedItemPosition != -1) {
                        itemData.proSign =
                            getResponseSpinner(
                                R.array.tch_abor_en,
                                binding.proSign,
                                context
                            )
                    }


                    listener.onEditSaveClick(itemData, position)
                }


            }

        }

        private fun validateData(): Boolean {
            return true
        }
    }

    class AddViewHolder(private val binding: ItemOffAddBinding, private val context: Context) :
        BaseViewHolder(binding.root) {
        fun bind(
            items: OffAddData,
            position: Int,
            listener: OffInterface.View
        ) {
            binding.proVendor.setText(items.proVendor)
            binding.proCaseNumber.setText(items.proCaseNumber)
            binding.proCaseName.setText(items.proCaseName)
            binding.proRebate.setText(items.proRebate)
            binding.proStartDate.setText(items.proStartDate)
            binding.proStopDate.setText(items.proStopDate)
//            proRebate
            binding.proStartDate.setOnClickListener {
                datePicker(binding.proStartDate)
            }
            binding.proStopDate.setOnClickListener {
                datePicker(binding.proStopDate)
            }
            bindSpinnerAdapter(
                R.array.tch_not_en,
                binding.proContent,
                items.proContent.toString(),
                context
            )
            bindSpinnerAdapter(
                R.array.pro_pronature_array_en,
                binding.proNature,
                items.proNature.toString(),
                context
            )
            bindSpinnerAdapter(
                R.array.tch_abor_en,
                binding.proSign,
                items.proSign.toString(),
                context
            )
            binding.cancelButton.setOnClickListener {
                listener.onCancelClick(position)
            }
            binding.saveBtn.setOnClickListener {

                if (validateData()) {
                    var itemData = OffAddData(
                        proVendor = binding.proVendor.text.toString(),

                        proId = items.proId,

                        proCaseNumber = items.proCaseNumber,
                        proCaseName = items.proCaseName,

                        proStartDate = items.proStartDate,
                        proStopDate = items.proStopDate,
                        proRebate = items.proRebate,

                        proSign = items.proSign,
                        proContent = items.proContent,
                        proNature = binding.proVendor.text.toString(),
                    )

                    if (binding.proVendor.text.isNotEmpty()) {
                        itemData.proVendor = binding.proVendor.text.toString()
                    }
                    if (binding.proCaseNumber.text.isNotEmpty()) {
                        itemData.proCaseNumber = binding.proCaseNumber.text.toString()
                    }
                    if (binding.proCaseName.text.isNotEmpty()) {
                        itemData.proCaseName = binding.proCaseName.text.toString()
                    }
                    if (binding.proStartDate.text.isNotEmpty()) {
                        itemData.proStartDate = binding.proStartDate.text.toString()
                    }
                    if (binding.proStopDate.text.isNotEmpty()) {
                        itemData.proStopDate = binding.proStopDate.text.toString()
                    }
                    if (binding.proRebate.text.isNotEmpty()) {
                        itemData.proRebate = binding.proRebate.text.toString()
                    }
                    if (binding.proNature.selectedItemPosition != -1) {
                        itemData.proNature =
                            getResponseSpinner(
                                R.array.pro_pronature_array_en,
                                binding.proNature,
                                context
                            )
                    }
                    if (binding.proContent.selectedItemPosition != -1) {
                        itemData.proContent =
                            getResponseSpinner(
                                R.array.tch_not_en,
                                binding.proContent,
                                context
                            )
                    }
                    if (binding.proSign.selectedItemPosition != -1) {
                        itemData.proSign =
                            getResponseSpinner(
                                R.array.tch_abor_en,
                                binding.proSign,
                                context
                            )
                    }


                    listener.onSaveClick(itemData, position)
                }


            }

        }

        private fun validateData(): Boolean {
            return true
        }

    }


}
