package com.example.linteacher.ui.teacherdata.ui.license

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.linteacher.R
import com.example.linteacher.api.BaseData
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicAddData
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicBaseData
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicEditData
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicOriginData
import com.example.linteacher.api.pojo.teacherdata.off.ui.OffAddData
import com.example.linteacher.api.pojo.teacherdata.off.ui.OffEditData
import com.example.linteacher.databinding.*
import com.example.linteacher.util.BaseAdapter
import com.example.linteacher.util.BaseViewHolder
import com.example.linteacher.util.Config
import java.util.ArrayList

class LicenseAdapter(
    list: ArrayList<LicBaseData>, private val listener: LicInterface.View
) : BaseAdapter(list) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Config.ADD_VIEW_TYPE) {
            val itemBinding =
                ItemLicAddBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            AddViewHolder(itemBinding, parent.context)
        } else if (viewType == Config.EdIT_VIEW_TYPE) {
            val itemBinding =
                ItemLicEditBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            EditViewHolder(itemBinding, parent.context)
        } else {
            val itemBinding =
                ItemLicOriginBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            OriginViewHolder(itemBinding, parent.context)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        if (viewType == Config.ADD_VIEW_TYPE) {
            (holder as AddViewHolder).bind(list.get(position) as LicAddData, position, listener)
        } else if (viewType == Config.EdIT_VIEW_TYPE) {
            (holder as EditViewHolder)
                .bind(
                    list.get(position)
                            as LicEditData, position, listener
                )
        } else {
            (holder as OriginViewHolder).bind(
                list[position] as LicOriginData,
                position,
                listener
            )
        }
    }

    private class OriginViewHolder(
        private val binding: ItemLicOriginBinding,
        private val context: Context
    ) : BaseViewHolder(binding.root) {
        fun bind(
            items: LicOriginData,
            position: Int,
            listener: LicInterface.View
        ) {
            binding.licName.text = items.licName
            binding.licService.text = items.licService
            binding.licType.text = items.licType
            binding.editButton.setOnClickListener {
                listener.onEditClick(items, position)
            }
        }

    }

    private class EditViewHolder(
        private val binding: ItemLicEditBinding,
        private val context: Context
    ) :
        BaseViewHolder(binding.root) {
        fun bind(
            items: LicEditData,
            position: Int,
            listener: LicInterface.View
        ) {
            binding.licName.setText(items.licName)
            binding.licService.setText(items.licService)
            binding.licNumber.setText(items.licNumber)
            bindSpinnerAdapter(
                R.array.lic_lictype_array_en,
                binding.licType,
                items.licType,
                context
            )

            binding.deleteBtn.setOnClickListener {
                listener.onDeleteClick(items.licId, position)
            }

            binding.cancelButton.setOnClickListener {
                listener.onEditCancelClick(position, items)
            }


            binding.saveBtn.setOnClickListener {

                if (validateData()) {
                    val itemData = LicEditData(
                        licName = items.licName,
                        licService = items.licService,
                        licType = items.licType,
                        licNumber = items.licNumber,
                        licId = items.licId,
                    )
                    if (binding.licName.text.isNotEmpty()) {
                        itemData.licName = binding.licName.text.toString()
                    }
                    if (binding.licService.text.isNotEmpty()) {
                        itemData.licService = binding.licService.text.toString()
                    }
                    if (binding.licNumber.text.isNotEmpty()) {
                        itemData.licNumber = binding.licNumber.text.toString()
                    }
                    if (binding.licType.selectedItemPosition != -1) {
                        itemData.licType =
                            getResponseSpinner(
                                R.array.lic_lictype_array_en,
                                binding.licType,
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

    private class AddViewHolder(
        private val binding: ItemLicAddBinding,
        private val context: Context
    ) :
        BaseViewHolder(binding.root) {
        fun bind(
            items: LicAddData,
            position: Int,
            listener: LicInterface.View
        ) {
            binding.licName.setText(items.licName)
            binding.licService.setText(items.licService)
            binding.licNumber.setText(items.licNumber)
            bindSpinnerAdapter(
                R.array.lic_lictype_array_en,
                binding.licType,
                items.licType,
                context
            )
            binding.cancelButton.setOnClickListener {
                listener.onCancelClick(position)
            }
            binding.saveBtn.setOnClickListener {

                if (validateData()) {
                    val itemData = LicAddData(
                        licName = items.licName,
                        licService = items.licService,
                        licType = items.licType,
                        licNumber = items.licNumber,
                        licId = items.licId,
                    )
                    if (binding.licName.text.isNotEmpty()) {
                        itemData.licName = binding.licName.text.toString()
                    }
                    if (binding.licService.text.isNotEmpty()) {
                        itemData.licService = binding.licService.text.toString()
                    }
                    if (binding.licNumber.text.isNotEmpty()) {
                        itemData.licNumber = binding.licNumber.text.toString()
                    }
                    if (binding.licType.selectedItemPosition != -1) {
                        itemData.licType =
                            getResponseSpinner(
                                R.array.lic_lictype_array_en,
                                binding.licType,
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