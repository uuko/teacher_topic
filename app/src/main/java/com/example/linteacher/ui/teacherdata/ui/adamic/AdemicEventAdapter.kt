package com.example.linteacher.ui.teacherdata.ui.adamic

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.linteacher.R
import com.example.linteacher.api.pojo.teacherdata.adamic.data.AdemicEventBaseData
import com.example.linteacher.databinding.*
import com.example.linteacher.util.BaseAdapter
import com.example.linteacher.util.BaseViewHolder
import com.example.linteacher.util.Config
import java.util.*

class AdemicEventAdapter(list: ArrayList<AdemicEventBaseData>, private val listener: AdemicEventInterface.View)
    : BaseAdapter(list) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //決定誰是ViewHolder,並new對應的calss 傳入itemView,context
        return if (viewType == Config.ADD_VIEW_TYPE) {
            //itemType = ADD_VIEW_TYPE
            val itemBinding =
                    ItemAdemicEventAddBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            AdemicEventAdapter.AddViewHolder(itemBinding, parent.context)
        } else if (viewType == Config.EdIT_VIEW_TYPE) {
            //itemType = EdIT_VIEW_TYPE
            val itemBinding =
                    ItemAdemicEventEditBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            AdemicEventAdapter.EditViewHolder(itemBinding, parent.context)
        } else {
            //itemType = ORIGIN_VIEW_TYPE
            val itemBinding =
                    ItemAdemicEventOriginBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            AdemicEventAdapter.OriginViewHolder(itemBinding, parent.context)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //有ViewHolder後 決定View的樣子

        val viewType = getItemViewType(position)
        if (viewType == Config.ADD_VIEW_TYPE) {
            //如果item 的viewType = addType
            //決定傳進去AddViewHolder的東西:item = LicAddData,position,context
            (holder as AddViewHolder).bind(list.get(position) as AdemicEventBaseData, position, listener)
        } else if (viewType == Config.EdIT_VIEW_TYPE) {
            (holder as AdemicEventAdapter.EditViewHolder)
                    .bind(
                            list.get(position)
                                    as AdemicEventBaseData, position, listener
                    )
        } else {
            (holder as AdemicEventAdapter.OriginViewHolder).bind(
                    list[position] as AdemicEventBaseData,
                    position,
                    listener
            )
        }
    }

    private class OriginViewHolder(
            private val binding: ItemAdemicEventOriginBinding,
            private val context: Context
    ) : BaseViewHolder(binding.root) {
        fun bind(
                items: AdemicEventBaseData,
                position: Int,
                listener: AdemicEventInterface.View
        ) {
            binding.eveCategory.text = items.eveCategory
            binding.eveName.text = items.eveName
            binding.eveOrganizer.text = items.eveOrganizer
            //監聽編輯按鈕***
            binding.editButton.setOnClickListener {
                listener.onEditClick(items.eveNumber.toString(), position)
            }
        }

    }

    private class EditViewHolder(
            private val binding: ItemAdemicEventEditBinding,
            private val context: Context
    ) :
            BaseViewHolder(binding.root) {
        fun bind(
                items: AdemicEventBaseData,
                position: Int,
                listener: AdemicEventInterface.View
        ) {
            //VIEW set list[positon]資料
            //7個
            binding.eveName.setText(items.eveName)
            binding.eveOrganizer.setText(items.eveOrganizer)
            binding.eveHours.setText(items.eveHours)
            binding.eveStratDate.setText(items.eveStratDate)
            binding.eveStopDate.setText(items.eveStopDate)
            binding.eveSchSubsidy.setText(items.eveSchSubsidy)
            binding.eveCertificateNumber.setText(items.eveCertificateNumber)


            //6個
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.eveCategory,
                    items.eveCategory,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.eveSort,
                    items.eveSort,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.eveLocation,
                    items.eveLocation,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.eveParticimainPation,
                    items.eveParticimainPation,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.eveClassRelated,
                    items.eveClassRelated,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.eveStudyCertificate,
                    items.eveStudyCertificate,
                    context
            )


            //監聽刪除按鈕
            binding.deleteBtn.setOnClickListener {
                //->打刪除API
                listener.onDeleteClick(items.eveNumber, position)
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
                    var itemData : AdemicEventBaseData = AdemicEventBaseData()
                    itemData=items
                    itemData.itemType = Config.EdIT_VIEW_TYPE


                    //如果View 不為空 就set 新的LicEditData
                    if (binding.eveName.text.isNotEmpty()) {
                        itemData.eveName = binding.eveName.text.toString()
                    }
                    if (binding.eveOrganizer.text.isNotEmpty()) {
                        itemData.eveOrganizer = binding.eveOrganizer.text.toString()
                    }
                    if (binding.eveHours .text.isNotEmpty()) {
                        itemData.eveHours = binding.eveHours.text.toString()
                    }

                    if (binding.eveStratDate .text.isNotEmpty()) {
                        itemData.eveStratDate  = binding.eveStratDate .text.toString()
                    }
                    if (binding.eveStopDate .text.isNotEmpty()) {
                        itemData.eveStopDate= binding.eveStopDate .text.toString()
                    }
                    if (binding.eveCertificateNumber.text.isNotEmpty()) {
                        itemData.eveCertificateNumber= binding.eveCertificateNumber.text.toString()
                    }
                    if (binding.eveSchSubsidy.text.isNotEmpty()) {
                        itemData.eveSchSubsidy= binding.eveSchSubsidy  .text.toString()
                    }
                    if (binding.eveCategory.selectedItemPosition != -1) {
                        itemData.eveCategory =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.eveCategory,
                                        context
                                )
                    }
                    if (binding.eveSort.selectedItemPosition != -1) {
                        itemData.eveSort =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.eveSort,
                                        context
                                )
                    }
                    if (binding.eveLocation.selectedItemPosition != -1) {
                        itemData.eveLocation =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.eveLocation,
                                        context
                                )
                    }
                    if (binding.eveParticimainPation.selectedItemPosition != -1) {
                        itemData.eveParticimainPation  =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.eveParticimainPation ,
                                        context
                                )
                    }
                    if (binding.eveClassRelated.selectedItemPosition != -1) {
                        itemData.eveClassRelated  =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.eveClassRelated,
                                        context
                                )
                    }
                    if (binding.eveStudyCertificate .selectedItemPosition != -1) {
                        itemData.eveStudyCertificate  =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.eveLocation,
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
            private val binding: ItemAdemicEventAddBinding, //itemView
            private val context: Context
    ) :
            BaseViewHolder(binding.root) {
        fun bind(
                items: AdemicEventBaseData,
                position: Int,
                listener: AdemicEventInterface.View
        ) {
            //set空的資料在View上
            //7個
            binding.eveName.setText(items.eveName)
            binding.eveOrganizer.setText(items.eveOrganizer)
            binding.eveHours.setText(items.eveHours)
            binding.eveStratDate.setText(items.eveStratDate)
            binding.eveStopDate.setText(items.eveStopDate)
            binding.eveSchSubsidy.setText(items.eveSchSubsidy)
            binding.eveCertificateNumber.setText(items.eveCertificateNumber)


            //6個
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.eveCategory,
                    items.eveCategory,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.eveSort,
                    items.eveSort,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.eveLocation,
                    items.eveLocation,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.eveParticimainPation,
                    items.eveParticimainPation,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.eveClassRelated,
                    items.eveClassRelated,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.eveStudyCertificate,
                    items.eveStudyCertificate,
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

                    var itemData : AdemicEventBaseData = AdemicEventBaseData()
                    itemData=items
                    itemData.itemType = Config.ADD_VIEW_TYPE
//                    val itemData = AdemicEventBaseData(
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
                    if (binding.eveName.text.isNotEmpty()) {
                        itemData.eveName = binding.eveName.text.toString()
                    }
                    if (binding.eveOrganizer.text.isNotEmpty()) {
                        itemData.eveOrganizer = binding.eveOrganizer.text.toString()
                    }
                    if (binding.eveHours .text.isNotEmpty()) {
                        itemData.eveHours = binding.eveHours.text.toString()
                    }

                    if (binding.eveStratDate .text.isNotEmpty()) {
                        itemData.eveStratDate  = binding.eveStratDate .text.toString()
                    }
                    if (binding.eveStopDate .text.isNotEmpty()) {
                        itemData.eveStopDate= binding.eveStopDate .text.toString()
                    }
                    if (binding.eveCertificateNumber.text.isNotEmpty()) {
                        itemData.eveCertificateNumber= binding.eveCertificateNumber.text.toString()
                    }
                    if (binding.eveSchSubsidy.text.isNotEmpty()) {
                        itemData.eveSchSubsidy= binding.eveSchSubsidy  .text.toString()
                    }
                    if (binding.eveCategory.selectedItemPosition != -1) {
                        itemData.eveCategory =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.eveCategory,
                                        context
                                )
                    }
                    if (binding.eveSort.selectedItemPosition != -1) {
                        itemData.eveSort =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.eveSort,
                                        context
                                )
                    }
                    if (binding.eveLocation.selectedItemPosition != -1) {
                        itemData.eveLocation =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.eveLocation,
                                        context
                                )
                    }
                    if (binding.eveParticimainPation.selectedItemPosition != -1) {
                        itemData.eveParticimainPation  =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.eveParticimainPation ,
                                        context
                                )
                    }
                    if (binding.eveClassRelated.selectedItemPosition != -1) {
                        itemData.eveClassRelated  =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.eveClassRelated,
                                        context
                                )
                    }
                    if (binding.eveStudyCertificate.selectedItemPosition != -1) {
                        itemData.eveStudyCertificate  =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.eveLocation,
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



