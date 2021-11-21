package com.example.linteacher.ui.teacherdata.ui.license

import android.content.Context
import android.graphics.drawable.Icon
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.linteacher.R
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicAddData
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicBaseData
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicEditData
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicOriginData
import com.example.linteacher.databinding.*
import com.example.linteacher.util.BaseAdapter
import com.example.linteacher.util.BaseViewHolder
import com.example.linteacher.util.Config
import java.util.ArrayList
//list: ArrayList<LicBaseData>便於不同的calss傳入
//listener: LicInterface.View MPV實作
//流程->新增/編輯/原始=>set或add adpter不同list ,每個list的item帶有不同的itemType
// 觸發每個item不同的viewholder就有不同的VIEW
class LicenseAdapter(
    list: ArrayList<LicBaseData>, private val listener: LicInterface.View
) : BaseAdapter(list) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //決定誰是ViewHolder,並new對應的calss 傳入itemView,context
        return if (viewType == Config.ADD_VIEW_TYPE) {
            //itemType = ADD_VIEW_TYPE
            val itemBinding =
                ItemLicAddBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            AddViewHolder(itemBinding, parent.context)
        } else if (viewType == Config.EdIT_VIEW_TYPE) {
            //itemType = EdIT_VIEW_TYPE
            val itemBinding =
                ItemLicEditBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            EditViewHolder(itemBinding, parent.context)
        } else {
            //itemType = ORIGIN_VIEW_TYPE
            val itemBinding =
                ItemLicOriginBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            OriginViewHolder(itemBinding, parent.context)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //有ViewHolder後 決定View的樣子

        val viewType = getItemViewType(position)
        if (viewType == Config.ADD_VIEW_TYPE) {
                //如果item 的viewType = addType
                //決定傳進去AddViewHolder的東西:item = LicAddData,position,context
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
            //監聽編輯按鈕***
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
            //VIEW set list[positon]資料
            binding.licName.setText(items.licName)
            binding.licService.setText(items.licService)
            binding.licNumber.setText(items.licNumber)
            bindSpinnerAdapter(
                R.array.lic_lictype_array_en,
                binding.licType,
                items.licType,
                context
            )

            //監聽刪除按鈕
            binding.deleteBtn.setOnClickListener {
                //->打刪除API
                listener.onDeleteClick(items.licId, position)
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
                    val itemData = LicEditData(
                        licName = items.licName,
                        licService = items.licService,
                        licType = items.licType,
                        licNumber = items.licNumber,
                        licId = items.licId,
                        public = items.public
                    )
                    //如果View 不為空 就set 新的LicEditData
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
        private val binding: ItemLicAddBinding, //itemView
        private val context: Context
    ) :
        BaseViewHolder(binding.root) {
        fun bind(
            items: LicAddData,
            position: Int,
            listener: LicInterface.View
        ) {
            //set空的資料在View上
            binding.licName.setText(items.licName)
            binding.licService.setText(items.licService)
            binding.licNumber.setText(items.licNumber)
            bindSpinnerAdapter(
                R.array.lic_lictype_array_en,
                binding.licType,
                items.licType,
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
                    val itemData = LicAddData(
                        licName = items.licName,
                        licService = items.licService,
                        licType = items.licType,
                        licNumber = items.licNumber,
                        licId = items.licId,
                    )
                    //如果新的LicAddData不為空就抓View上的值
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