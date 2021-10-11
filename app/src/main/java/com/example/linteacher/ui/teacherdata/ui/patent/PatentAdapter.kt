package com.example.linteacher.ui.teacherdata.ui.patent

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.linteacher.R
import com.example.linteacher.api.pojo.teacherdata.patent.data.PatentBaseData
import com.example.linteacher.databinding.ItemPatentAddBinding
import com.example.linteacher.databinding.ItemPatentEditBinding
import com.example.linteacher.databinding.ItemPatentOriginBinding
import com.example.linteacher.ui.teacherdata.ui.patent.PatentAdapter
import com.example.linteacher.ui.teacherdata.ui.patent.PatentInterface
import com.example.linteacher.util.BaseAdapter
import com.example.linteacher.util.BaseViewHolder
import com.example.linteacher.util.Config
import java.util.ArrayList

class PatentAdapter(list: ArrayList<PatentBaseData>, private val listener: PatentInterface.View)
    : BaseAdapter(list) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //決定誰是ViewHolder,並new對應的calss 傳入itemView,context
        return if (viewType == Config.ADD_VIEW_TYPE) {
            //itemType = ADD_VIEW_TYPE
            val itemBinding =
                    ItemPatentAddBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            PatentAdapter.AddViewHolder(itemBinding, parent.context)
        } else if (viewType == Config.EdIT_VIEW_TYPE) {
            //itemType = EdIT_VIEW_TYPE
            val itemBinding =
                    ItemPatentEditBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            PatentAdapter.EditViewHolder(itemBinding, parent.context)
        } else {
            //itemType = ORIGIN_VIEW_TYPE
            val itemBinding =
                    ItemPatentOriginBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            PatentAdapter.OriginViewHolder(itemBinding, parent.context)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //有ViewHolder後 決定View的樣子

        val viewType = getItemViewType(position)
        if (viewType == Config.ADD_VIEW_TYPE) {
            //如果item 的viewType = addType
            //決定傳進去AddViewHolder的東西:item = LicAddData,position,context
            (holder as AddViewHolder).bind(list.get(position) as PatentBaseData, position, listener)
        } else if (viewType == Config.EdIT_VIEW_TYPE) {
            (holder as PatentAdapter.EditViewHolder)
                    .bind(
                            list.get(position)
                                    as PatentBaseData, position, listener
                    )
        } else {
            (holder as PatentAdapter.OriginViewHolder).bind(
                    list[position] as PatentBaseData,
                    position,
                    listener
            )
        }
    }

    private class OriginViewHolder(
            private val binding: ItemPatentOriginBinding,
            private val context: Context
    ) : BaseViewHolder(binding.root) {
        fun bind(
                items: PatentBaseData,
                position: Int,
                listener: PatentInterface.View
        ) {
            binding.patmainPatentName.text = items.patmainPatentName
            binding.patType.text = items.patType
            binding.patProgressStatus.text = items.patProgressStatus
            //監聽編輯按鈕***
            binding.editButton.setOnClickListener {
                listener.onEditClick(items.patId.toString(), position)
            }
        }

    }

    private class EditViewHolder(
            private val binding: ItemPatentEditBinding,
            private val context: Context
    ) :
            BaseViewHolder(binding.root) {
        fun bind(
                items: PatentBaseData,
                position: Int,
                listener: PatentInterface.View
        ) {
            //VIEW set list[positon]資料
            //10
            binding.patProject.setText(items.patProject)
            binding.patmainPatentName.setText(items.patmainPatentName)
            binding.patReportCode.setText(items.patReportCode )
            binding.patReportEdata.setText(items.patReportEdata)
            binding.patAppmainLicant.setText(items.patAppmainLicant )
            binding.patAppmainLicationDate.setText(items.patAppmainLicationDate)
            binding.patEndDate.setText(items.patEndDate)
            binding.patmainLicensingAgency.setText(items.patmainLicensingAgency )
            binding.patCertificateNumber.setText(items.patCertificateNumber)

            //4
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.patAuthor,
                    items.patAuthor,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.patCountry,
                    items.patCountry,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.patType,
                    items.patType,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.patProgressStatus,
                    items.patProgressStatus,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.patAppmainLicantType,
                    items.patAppmainLicantType,
                    context
            )



            //監聽刪除按鈕
            binding.deleteBtn.setOnClickListener {
                //->打刪除API
                listener.onDeleteClick(items.patId, position)
            }

            //監聽取消按紐
            binding.cancelButton.setOnClickListener {
                listener.onEditCancelClick(position, items)
            }


            //監聽儲存按鈕
            binding.saveBtn.setOnClickListener {
                //validateData return =true
                if (validateData()) {
                    //新的LicEditData 拿舊的LicEditData
                    var itemData : PatentBaseData = PatentBaseData()
                    itemData=items
                    itemData.itemType = Config.EdIT_VIEW_TYPE


                    //如果View 不為空 就set 新的LicEditData
                    //10
                    if (binding.patProject.text.isNotEmpty()) {
                        itemData.patProject = binding.patProject.text.toString()
                    }
                    if (binding.patmainPatentName.text.isNotEmpty()) {
                        itemData.patmainPatentName = binding.patmainPatentName.text.toString()
                    }
                    if (binding.patReportCode.text.isNotEmpty()) {
                        itemData.patReportCode = binding.patReportCode.text.toString()
                    }
                    if (binding.patReportEdata.text.isNotEmpty()) {
                        itemData.patReportEdata = binding.patReportEdata.text.toString()
                    }

                    if (binding.patAppmainLicant.text.isNotEmpty()) {
                        itemData.patAppmainLicant = binding.patAppmainLicant.text.toString()
                    }
                    if (binding.patAppmainLicationDate.text.isNotEmpty()) {
                        itemData.patAppmainLicationDate = binding.patAppmainLicationDate.text.toString()
                    }
                    if (binding.patEndDate.text.isNotEmpty()) {
                        itemData.patEndDate = binding.patEndDate.text.toString()
                    }
                    if (binding.patmainLicensingAgency.text.isNotEmpty()) {
                        itemData.patmainLicensingAgency = binding.patmainLicensingAgency.text.toString()
                    }
                    if (binding.patCertificateNumber.text.isNotEmpty()) {
                        itemData.patCertificateNumber = binding.patCertificateNumber.text.toString()
                    }
                    //4
                    if (binding.patAuthor.selectedItemPosition != -1) {
                        itemData.patAuthor =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.patAuthor,
                                        context
                                )
                    }
                    if (binding.patCountry.selectedItemPosition != -1) {
                        itemData.patCountry =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.patCountry,
                                        context
                                )
                    }
                    if (binding.patType.selectedItemPosition != -1) {
                        itemData.patType =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.patType,
                                        context
                                )
                    }
                    if (binding.patProgressStatus.selectedItemPosition != -1) {
                        itemData.patProgressStatus =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.patProgressStatus,
                                        context
                                )
                    }
                    if (binding.patAppmainLicantType.selectedItemPosition != -1) {
                        itemData.patAppmainLicantType =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.patAppmainLicantType,
                                        context
                                )
                    }

                    //listener.onEditSaveClick(新的LicEditData, position)
                    listener.onEditSaveClick(itemData, position)
                }


            }

        }


        private fun validateData(): Boolean {
            return true
        }
    }

    //class繼承BaseViewHolder
    private class AddViewHolder(
            private val binding: ItemPatentAddBinding, //itemView
            private val context: Context
    ) :
            BaseViewHolder(binding.root) {
        fun bind(
                items: PatentBaseData,
                position: Int,
                listener: PatentInterface.View
        ) {
            //set空的資料在View上

//10
            binding.patProject.setText(items.patProject)
            binding.patmainPatentName.setText(items.patmainPatentName)
            binding.patReportCode.setText(items.patReportCode )
            binding.patReportEdata.setText(items.patReportEdata)
            binding.patAppmainLicant.setText(items.patAppmainLicant )
            binding.patAppmainLicationDate.setText(items.patAppmainLicationDate)
            binding.patEndDate.setText(items.patEndDate)
            binding.patmainLicensingAgency.setText(items.patmainLicensingAgency )
            binding.patCertificateNumber.setText(items.patCertificateNumber)

            //4
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.patAuthor,
                    items.patAuthor,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.patCountry,
                    items.patCountry,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.patType,
                    items.patType,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.patProgressStatus,
                    items.patProgressStatus,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.patAppmainLicantType,
                    items.patAppmainLicantType,
                    context
            )


            //監聽取消按鈕
            binding.cancelButton.setOnClickListener {
                listener.onCancelClick(position)
            }

            //監聽確定按鈕
            binding.saveBtn.setOnClickListener {
                //validateData() return ture
                if (validateData()) {
                    //新的LicAddData = 一筆空的

                    var itemData : PatentBaseData = PatentBaseData()
                    itemData=items
                    itemData.itemType = Config.ADD_VIEW_TYPE
//                    val itemData = PatentBaseData(
//                            awaPlan = items.awaPlan,
//                            awaName = items.awaName,
//                            awaMechanismName = items.awaMechanismName,
//                            awaSort = items.awaSort,
//                            awaCampus = items.awaCampus,
//                            awaCountry = items.awaCountry,
//                            awaDate = items.awaDate,
//                            awaId = items.awaId,
//                            itemType = Config.ADD_VIEW_TYPE
//                    )
                    //如果新的LicAddData不為空就抓View上的值
                    //10
                    if (binding.patProject.text.isNotEmpty()) {
                        itemData.patProject = binding.patProject.text.toString()
                    }
                    if (binding.patmainPatentName.text.isNotEmpty()) {
                        itemData.patmainPatentName = binding.patmainPatentName.text.toString()
                    }
                    if (binding.patReportCode.text.isNotEmpty()) {
                        itemData.patReportCode = binding.patReportCode.text.toString()
                    }
                    if (binding.patReportEdata.text.isNotEmpty()) {
                        itemData.patReportEdata = binding.patReportEdata.text.toString()
                    }
                    if (binding.patAppmainLicant.text.isNotEmpty()) {
                        itemData.patAppmainLicant = binding.patAppmainLicant.text.toString()
                    }
                    if (binding.patAppmainLicationDate.text.isNotEmpty()) {
                        itemData.patAppmainLicationDate = binding.patAppmainLicationDate.text.toString()
                    }
                    if (binding.patEndDate.text.isNotEmpty()) {
                        itemData.patEndDate = binding.patEndDate.text.toString()
                    }
                    if (binding.patmainLicensingAgency.text.isNotEmpty()) {
                        itemData.patmainLicensingAgency = binding.patmainLicensingAgency.text.toString()
                    }
                    if (binding.patCertificateNumber.text.isNotEmpty()) {
                        itemData.patCertificateNumber = binding.patCertificateNumber.text.toString()
                    }
                    //4
                    if (binding.patAuthor.selectedItemPosition != -1) {
                        itemData.patAuthor =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.patAuthor,
                                        context
                                )
                    }
                    if (binding.patCountry.selectedItemPosition != -1) {
                        itemData.patCountry =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.patCountry,
                                        context
                                )
                    }
                    if (binding.patType.selectedItemPosition != -1) {
                        itemData.patType =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.patType,
                                        context
                                )
                    }
                    if (binding.patProgressStatus.selectedItemPosition != -1) {
                        itemData.patProgressStatus =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.patProgressStatus,
                                        context
                                )
                    }
                    if (binding.patAppmainLicantType.selectedItemPosition != -1) {
                        itemData.patAppmainLicantType =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.patAppmainLicantType,
                                        context
                                )
                    }

                    //listener= licenseFragment  ,licenseFragment.onSaveClick(新增的LicAddData,positon)->打API
                    listener.onSaveClick(itemData, position)
                }


            }

        }

        private fun validateData(): Boolean {
            return true
        }

    }





}