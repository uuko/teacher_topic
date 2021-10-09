package com.example.linteacher.ui.teacherdata.ui.award

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.linteacher.R
import com.example.linteacher.api.pojo.teacherdata.award.AwardResponse
import com.example.linteacher.api.pojo.teacherdata.award.data.AwardBaseData
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicBaseData
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicEditData
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicOriginData
import com.example.linteacher.databinding.*
import com.example.linteacher.ui.teacherdata.ui.license.LicInterface
import com.example.linteacher.ui.teacherdata.ui.license.LicenseAdapter
import com.example.linteacher.util.BaseAdapter
import com.example.linteacher.util.BaseViewHolder
import com.example.linteacher.util.Config
import java.util.*

class AwardAdapter(list: ArrayList<AwardBaseData>, private val listener: AwardInterface.View)
    : BaseAdapter(list) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //決定誰是ViewHolder,並new對應的calss 傳入itemView,context
        return if (viewType == Config.ADD_VIEW_TYPE) {
            //itemType = ADD_VIEW_TYPE
            val itemBinding =
                    ItemAwardAddBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            AwardAdapter.AddViewHolder(itemBinding, parent.context)
        } else if (viewType == Config.EdIT_VIEW_TYPE) {
            //itemType = EdIT_VIEW_TYPE
            val itemBinding =
                    ItemAwardEditBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            AwardAdapter.EditViewHolder(itemBinding, parent.context)
        } else {
            //itemType = ORIGIN_VIEW_TYPE
            val itemBinding =
                    ItemAwardOriginBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            AwardAdapter.OriginViewHolder(itemBinding, parent.context)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //有ViewHolder後 決定View的樣子

        val viewType = getItemViewType(position)
        if (viewType == Config.ADD_VIEW_TYPE) {
            //如果item 的viewType = addType
            //決定傳進去AddViewHolder的東西:item = LicAddData,position,context
            (holder as AddViewHolder).bind(list.get(position) as AwardBaseData, position, listener)
        } else if (viewType == Config.EdIT_VIEW_TYPE) {
            (holder as AwardAdapter.EditViewHolder)
                    .bind(
                            list.get(position)
                                    as AwardBaseData, position, listener
                    )
        } else {
            (holder as AwardAdapter.OriginViewHolder).bind(
                    list[position] as AwardBaseData,
                    position,
                    listener
            )
        }
    }

    private class OriginViewHolder(
            private val binding: ItemAwardOriginBinding,
            private val context: Context
    ) : BaseViewHolder(binding.root) {
        fun bind(
                items: AwardBaseData,
                position: Int,
                listener: AwardInterface.View
        ) {
            binding.awaName.text = items.awaName
            binding.awaMechanismName.text = items.awaMechanismName
            binding.awaDate.text = items.awaDate
            //監聽編輯按鈕***
            binding.editButton.setOnClickListener {
                listener.onEditClick(items.awaId.toString(), position)
            }
        }

    }

    private class EditViewHolder(
            private val binding: ItemAwardEditBinding,
            private val context: Context
    ) :
            BaseViewHolder(binding.root) {
        fun bind(
                items: AwardBaseData,
                position: Int,
                listener: AwardInterface.View
        ) {
            //VIEW set list[positon]資料
            binding.awaPlan.setText(items.awaPlan)
            binding.awaName.setText(items.awaName)
            binding.awaMechanismName.setText(items.awaMechanismName )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.awaSort,
                    items.awaSort,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.awaCountry,
                    items.awaCountry,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.awaCampus,
                    items.awaCampus,
                    context
            )
            binding.awaDate.setText(items.awaDate)


            //監聽刪除按鈕
            binding.deleteBtn.setOnClickListener {
                //->打刪除API
                listener.onDeleteClick(items.awaId, position)
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
                    var itemData :AwardBaseData =AwardBaseData()
                    itemData=items
                    itemData.itemType = Config.EdIT_VIEW_TYPE


                    //如果View 不為空 就set 新的LicEditData
                    if (binding.awaPlan.text.isNotEmpty()) {
                        itemData.awaPlan = binding.awaPlan.text.toString()
                    }
                    if (binding.awaName.text.isNotEmpty()) {
                        itemData.awaName = binding.awaName.text.toString()
                    }
                    if (binding.awaMechanismName.text.isNotEmpty()) {
                        itemData.awaMechanismName = binding.awaMechanismName.text.toString()
                    }
                    if (binding.awaSort.selectedItemPosition != -1) {
                        itemData.awaSort =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.awaSort,
                                        context
                                )
                    }
                    if (binding.awaSort.selectedItemPosition != -1) {
                        itemData.awaCampus =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.awaCampus,
                                        context
                                )
                    }
                    if (binding.awaCountry.selectedItemPosition != -1) {
                        itemData.awaCountry =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.awaCountry,
                                        context
                                )
                    }
                    if (binding.awaDate.text.isNotEmpty()) {
                        itemData.awaDate = binding.awaDate.text.toString()
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
            private val binding: ItemAwardAddBinding, //itemView
            private val context: Context
    ) :
            BaseViewHolder(binding.root) {
        fun bind(
                items: AwardBaseData,
                position: Int,
                listener: AwardInterface.View
        ) {
            //set空的資料在View上
            binding.awaPlan.setText(items.awaPlan)
            binding.awaName.setText(items.awaName)
            binding.awaMechanismName.setText(items.awaMechanismName )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.awaSort,
                    items.awaSort,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.awaCountry,
                    items.awaCountry,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.awaCampus,
                    items.awaCampus,
                    context
            )
            binding.awaDate.setText(items.awaDate)


            //監聽取消按鈕
            binding.cancelButton.setOnClickListener {
                listener.onCancelClick(position)
            }

            //監聽確定按鈕
            binding.saveBtn.setOnClickListener {
                //validateData() return ture
                if (validateData()) {
                    //新的LicAddData = 一筆空的

                    var itemData :AwardBaseData =AwardBaseData()
                    itemData=items
                    itemData.itemType = Config.ADD_VIEW_TYPE
//                    val itemData = AwardBaseData(
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
                    if (binding.awaPlan.text.isNotEmpty()) {
                        itemData.awaPlan = binding.awaPlan.text.toString()
                    }
                    if (binding.awaName.text.isNotEmpty()) {
                        itemData.awaName = binding.awaName.text.toString()
                    }
                    if (binding.awaMechanismName.text.isNotEmpty()) {
                        itemData.awaMechanismName = binding.awaMechanismName.text.toString()
                    }
                    if (binding.awaSort.selectedItemPosition != -1) {
                        itemData.awaSort =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.awaSort,
                                        context
                                )
                    }
                    if (binding.awaSort.selectedItemPosition != -1) {
                        itemData.awaCampus =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.awaCampus,
                                        context
                                )
                    }
                    if (binding.awaCountry.selectedItemPosition != -1) {
                        itemData.awaCountry =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.awaCountry,
                                        context
                                )
                    }
                    if (binding.awaDate.text.isNotEmpty()) {
                        itemData.awaDate = binding.awaDate.text.toString()
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