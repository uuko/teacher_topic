package com.example.linteacher.ui.teacherdata.ui.gov

import android.content.Context
import android.graphics.drawable.Icon
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.linteacher.R
import com.example.linteacher.api.pojo.teacherdata.gov.data.GovBaseData
import com.example.linteacher.databinding.ItemGovAddBinding
import com.example.linteacher.databinding.ItemGovEditBinding
import com.example.linteacher.databinding.ItemGovOriginBinding
import com.example.linteacher.ui.teacherdata.ui.gov.GovAdapter
import com.example.linteacher.ui.teacherdata.ui.gov.GovInterface
import com.example.linteacher.util.BaseAdapter
import com.example.linteacher.util.BaseViewHolder
import com.example.linteacher.util.Config
import java.util.ArrayList

class GovAdapter (list: ArrayList<GovBaseData>, private val listener: GovInterface.View)
    : BaseAdapter(list) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //決定誰是ViewHolder,並new對應的calss 傳入itemView,context
        return if (viewType == Config.ADD_VIEW_TYPE) {
            //itemType = ADD_VIEW_TYPE
            val itemBinding =
                    ItemGovAddBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            GovAdapter.AddViewHolder(itemBinding, parent.context)
        } else if (viewType == Config.EdIT_VIEW_TYPE) {
            //itemType = EdIT_VIEW_TYPE
            val itemBinding =
                    ItemGovEditBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            GovAdapter.EditViewHolder(itemBinding, parent.context)
        } else {
            //itemType = ORIGIN_VIEW_TYPE
            val itemBinding =
                    ItemGovOriginBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            GovAdapter.OriginViewHolder(itemBinding, parent.context)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //有ViewHolder後 決定View的樣子

        val viewType = getItemViewType(position)
        if (viewType == Config.ADD_VIEW_TYPE) {
            //如果item 的viewType = addType
            //決定傳進去AddViewHolder的東西:item = LicAddData,position,context
            (holder as AddViewHolder).bind(list.get(position) as GovBaseData, position, listener)
        } else if (viewType == Config.EdIT_VIEW_TYPE) {
            (holder as GovAdapter.EditViewHolder)
                    .bind(
                            list.get(position)
                                    as GovBaseData, position, listener
                    )
        } else {
            (holder as GovAdapter.OriginViewHolder).bind(
                    list[position] as GovBaseData,
                    position,
                    listener
            )
        }
    }

    private class OriginViewHolder(
            private val binding: ItemGovOriginBinding,
            private val context: Context
    ) : BaseViewHolder(binding.root) {
        fun bind(
                items: GovBaseData,
                position: Int,
                listener: GovInterface.View
        ) {
            Log.d("chnageGovPublic", "OriginViewHolder 最初item public"+items.public)


            binding.govProjectName.text = items.govProjectName
            binding.govProjectType.text = items.govProjectType
            binding.govJobType.text = items.govJobType
            //監聽編輯按鈕***
            binding.editButton.setOnClickListener {
                listener.onEditClick(items.govId.toString(), position)
            }

            //勾勾設定監聽按鈕
            binding.tick.setOnClickListener {
                Log.d("clickaaa", "click:tick ")
                Log.d("chnageGovPublic", "click:tick 原本"+items.public)

                items.public  = !items.public
                Log.d("chnageGovPublic", "click:tick 改變"+items.public)

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
            private val binding: ItemGovEditBinding,
            private val context: Context
    ) :
            BaseViewHolder(binding.root) {
        fun bind(
                items: GovBaseData,
                position: Int,
                listener: GovInterface.View
        ) {
            //VIEW set list[positon]資料
            //14
            binding.govProjectName.setText(items.govProjectName)
            binding.govProbjectNumber.setText(items.govProbjectNumber)
            binding.govFD.setText(items.govFD )
            binding.govED.setText(items.govED)
            binding.govUnitName.setText(items.govUnitName)
            binding.govSecAund.setText(items.govSecAund )
            binding.govBenefitDepartment.setText(items.govBenefitDepartment)
            binding.govComUnit.setText(items.govComUnit)
            binding.govTeamworkUnit.setText(items.govTeamworkUnit )
            binding.govProjectAmount.setText(items.govProjectAmount)
            binding.govmainGovAmount.setText(items.govmainGovAmount)
            binding.govEntAmount.setText(items.govEntAmount )
            binding.govOthAmount.setText(items.govOthAmount)
            binding.govSchAmount.setText(items.govSchAmount)
            //8
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.govProbjectType,
                    items.govProbjectType,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.govProjectType,
                    items.govProjectType,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.govProjectNature,
                    items.govProjectNature,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.govJobType,
                    items.govJobType,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.govMoneyState,
                    items.govMoneyState,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.govMainfund,
                    items.govMainfund,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.govOthIn,
                    items.govOthIn,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.govToOth,
                    items.govToOth,
                    context
            )




            //監聽刪除按鈕
            binding.deleteBtn.setOnClickListener {
                //->打刪除API
                listener.onDeleteClick(items.govId, position)
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
                    var itemData : GovBaseData = GovBaseData()
                    itemData=items
                    itemData.itemType = Config.EdIT_VIEW_TYPE


                    //如果View 不為空 就set 新的LicEditData
                    //14
                    if (binding.govProjectName.text.isNotEmpty()) {
                        itemData.govProjectName = binding.govProjectName.text.toString()
                    }
                    if (binding.govProbjectNumber.text.isNotEmpty()) {
                        itemData.govProbjectNumber = binding.govProbjectNumber.text.toString()
                    }
                    if (binding.govFD.text.isNotEmpty()) {
                        itemData.govFD = binding.govFD.text.toString()
                    }
                    if (binding.govED.text.isNotEmpty()) {
                        itemData.govED = binding.govED.text.toString()
                    }
                    if (binding.govUnitName.text.isNotEmpty()) {
                        itemData.govUnitName = binding.govUnitName.text.toString()
                    }
                    if (binding.govSecAund.text.isNotEmpty()) {
                        itemData.govSecAund = binding.govSecAund.text.toString()
                    }
                    if (binding.govBenefitDepartment.text.isNotEmpty()) {
                        itemData.govBenefitDepartment = binding.govBenefitDepartment.text.toString()
                    }
                    if (binding.govComUnit.text.isNotEmpty()) {
                        itemData.govComUnit = binding.govComUnit.text.toString()
                    }
                    if (binding.govTeamworkUnit.text.isNotEmpty()) {
                        itemData.govTeamworkUnit = binding.govTeamworkUnit.text.toString()
                    }
                    if (binding.govProjectAmount.text.isNotEmpty()) {
                        itemData.govProjectAmount = binding.govProjectAmount.text.toString()
                    }
                    if (binding.govmainGovAmount.text.isNotEmpty()) {
                        itemData.govmainGovAmount = binding.govmainGovAmount.text.toString()
                    }
                    if (binding.govEntAmount.text.isNotEmpty()) {
                        itemData.govEntAmount = binding.govEntAmount.text.toString()
                    }
                    if (binding.govOthAmount.text.isNotEmpty()) {
                        itemData.govOthAmount = binding.govOthAmount.text.toString()
                    }
                    if (binding.govSchAmount.text.isNotEmpty()) {
                        itemData.govSchAmount = binding.govSchAmount.text.toString()
                    }
                    //8
                    if (binding.govProbjectType.selectedItemPosition != -1) {
                        itemData.govProbjectType =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.govProbjectType,
                                        context
                                )
                    }
                    if (binding.govProjectType.selectedItemPosition != -1) {
                        itemData.govProjectType =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.govProjectType,
                                        context
                                )
                    }
                    if (binding.govProjectNature.selectedItemPosition != -1) {
                        itemData.govProjectNature =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.govProjectNature,
                                        context
                                )
                    }
                    if (binding.govJobType.selectedItemPosition != -1) {
                        itemData.govJobType =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.govJobType,
                                        context
                                )
                    }
                    if (binding.govMoneyState.selectedItemPosition != -1) {
                        itemData.govMoneyState =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.govMoneyState,
                                        context
                                )
                    }
                    if (binding.govMainfund.selectedItemPosition != -1) {
                        itemData.govMainfund =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.govMainfund,
                                        context
                                )
                    }
                    if (binding.govOthIn.selectedItemPosition != -1) {
                        itemData.govOthIn =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.govOthIn,
                                        context
                                )
                    }
                    if (binding.govToOth.selectedItemPosition != -1) {
                        itemData.govToOth =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.govToOth,
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
            private val binding: ItemGovAddBinding, //itemView
            private val context: Context
    ) :
            BaseViewHolder(binding.root) {
        fun bind(
                items: GovBaseData,
                position: Int,
                listener: GovInterface.View
        ) {
            //set空的資料在View上

//14
            binding.govProjectName.setText(items.govProjectName)
            binding.govProbjectNumber.setText(items.govProbjectNumber)
            binding.govFD.setText(items.govFD )
            binding.govED.setText(items.govED)
            binding.govUnitName.setText(items.govUnitName)
            binding.govSecAund.setText(items.govSecAund )
            binding.govBenefitDepartment.setText(items.govBenefitDepartment)
            binding.govComUnit.setText(items.govComUnit)
            binding.govTeamworkUnit.setText(items.govTeamworkUnit )
            binding.govProjectAmount.setText(items.govProjectAmount)
            binding.govmainGovAmount.setText(items.govmainGovAmount)
            binding.govEntAmount.setText(items.govEntAmount )
            binding.govOthAmount.setText(items.govOthAmount)
            binding.govSchAmount.setText(items.govSchAmount)
            //8
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.govProbjectType,
                    items.govProbjectType,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.govProjectType,
                    items.govProjectType,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.govProjectNature,
                    items.govProjectNature,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.govJobType,
                    items.govJobType,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.govMoneyState,
                    items.govMoneyState,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.govMainfund,
                    items.govMainfund,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.govOthIn,
                    items.govOthIn,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.govToOth,
                    items.govToOth,
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

                    var itemData : GovBaseData = GovBaseData()
                    itemData=items
                    itemData.itemType = Config.ADD_VIEW_TYPE
//                    val itemData = GovBaseData(
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
                    //14
                    if (binding.govProjectName.text.isNotEmpty()) {
                        itemData.govProjectName = binding.govProjectName.text.toString()
                    }
                    if (binding.govProbjectNumber.text.isNotEmpty()) {
                        itemData.govProbjectNumber = binding.govProbjectNumber.text.toString()
                    }
                    if (binding.govFD.text.isNotEmpty()) {
                        itemData.govFD = binding.govFD.text.toString()
                    }
                    if (binding.govED.text.isNotEmpty()) {
                        itemData.govED = binding.govED.text.toString()
                    }
                    if (binding.govUnitName.text.isNotEmpty()) {
                        itemData.govUnitName = binding.govUnitName.text.toString()
                    }
                    if (binding.govSecAund.text.isNotEmpty()) {
                        itemData.govSecAund = binding.govSecAund.text.toString()
                    }
                    if (binding.govBenefitDepartment.text.isNotEmpty()) {
                        itemData.govBenefitDepartment = binding.govBenefitDepartment.text.toString()
                    }
                    if (binding.govComUnit.text.isNotEmpty()) {
                        itemData.govComUnit = binding.govComUnit.text.toString()
                    }
                    if (binding.govTeamworkUnit.text.isNotEmpty()) {
                        itemData.govTeamworkUnit = binding.govTeamworkUnit.text.toString()
                    }
                    if (binding.govProjectAmount.text.isNotEmpty()) {
                        itemData.govProjectAmount = binding.govProjectAmount.text.toString()
                    }
                    if (binding.govmainGovAmount.text.isNotEmpty()) {
                        itemData.govmainGovAmount = binding.govmainGovAmount.text.toString()
                    }
                    if (binding.govEntAmount.text.isNotEmpty()) {
                        itemData.govEntAmount = binding.govEntAmount.text.toString()
                    }
                    if (binding.govOthAmount.text.isNotEmpty()) {
                        itemData.govOthAmount = binding.govOthAmount.text.toString()
                    }
                    if (binding.govSchAmount.text.isNotEmpty()) {
                        itemData.govSchAmount = binding.govSchAmount.text.toString()
                    }
                    //8
                    if (binding.govProbjectType.selectedItemPosition != -1) {
                        itemData.govProbjectType =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.govProbjectType,
                                        context
                                )
                    }
                    if (binding.govProjectType.selectedItemPosition != -1) {
                        itemData.govProjectType =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.govProjectType,
                                        context
                                )
                    }
                    if (binding.govProjectNature.selectedItemPosition != -1) {
                        itemData.govProjectNature =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.govProjectNature,
                                        context
                                )
                    }
                    if (binding.govJobType.selectedItemPosition != -1) {
                        itemData.govJobType =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.govJobType,
                                        context
                                )
                    }
                    if (binding.govMoneyState.selectedItemPosition != -1) {
                        itemData.govMoneyState =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.govMoneyState,
                                        context
                                )
                    }
                    if (binding.govMainfund.selectedItemPosition != -1) {
                        itemData.govMainfund =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.govMainfund,
                                        context
                                )
                    }
                    if (binding.govOthIn.selectedItemPosition != -1) {
                        itemData.govOthIn =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.govOthIn,
                                        context
                                )
                    }
                    if (binding.govToOth.selectedItemPosition != -1) {
                        itemData.govToOth =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.govToOth,
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