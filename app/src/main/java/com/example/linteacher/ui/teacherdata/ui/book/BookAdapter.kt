package com.example.linteacher.ui.teacherdata.ui.book


import android.content.Context
import android.graphics.drawable.Icon
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.linteacher.R
import com.example.linteacher.api.pojo.teacherdata.book.data.BookBaseData
import com.example.linteacher.databinding.*
import com.example.linteacher.ui.teacherdata.ui.book.BookInterface
import com.example.linteacher.util.BaseAdapter
import com.example.linteacher.util.BaseViewHolder
import com.example.linteacher.util.Config
import java.util.*

class BookAdapter(list: ArrayList<BookBaseData>, private val listener: BookInterface.View)
    : BaseAdapter(list) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //決定誰是ViewHolder,並new對應的calss 傳入itemView,context
        return if (viewType == Config.ADD_VIEW_TYPE) {
            //itemType = ADD_VIEW_TYPE
            val itemBinding =
                    ItemBookAddBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            BookAdapter.AddViewHolder(itemBinding, parent.context)
        } else if (viewType == Config.EdIT_VIEW_TYPE) {
            //itemType = EdIT_VIEW_TYPE
            val itemBinding =
                    ItemBookEditBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            BookAdapter.EditViewHolder(itemBinding, parent.context)
        } else {
            //itemType = ORIGIN_VIEW_TYPE
            val itemBinding =
                    ItemBookOriginBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            BookAdapter.OriginViewHolder(itemBinding, parent.context)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //有ViewHolder後 決定View的樣子

        val viewType = getItemViewType(position)
        if (viewType == Config.ADD_VIEW_TYPE) {
            //如果item 的viewType = addType
            //決定傳進去AddViewHolder的東西:item = LicAddData,position,context
            (holder as AddViewHolder).bind(list.get(position) as BookBaseData, position, listener)
        } else if (viewType == Config.EdIT_VIEW_TYPE) {
            (holder as BookAdapter.EditViewHolder)
                    .bind(
                            list.get(position)
                                    as BookBaseData, position, listener
                    )
        } else {
            (holder as BookAdapter.OriginViewHolder).bind(
                    list[position] as BookBaseData,
                    position,
                    listener
            )
        }
    }

    private class OriginViewHolder(
            private val binding: ItemBookOriginBinding,
            private val context: Context
    ) : BaseViewHolder(binding.root) {
        fun bind(
                items: BookBaseData,
                position: Int,
                listener: BookInterface.View
        ) {
            binding.infName.text = items.infName
            binding.infISBN.text = items.infISBN
            binding.infAuthorOrder.text = items.infAuthorOrder
                    //監聽編輯按鈕***
            binding.editButton.setOnClickListener {
                Log.d("orginid", "items.infNumber"+items.infNumber)
                listener.onEditClick(items.infNumber.toString(), position)
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
            private val binding: ItemBookEditBinding,
            private val context: Context
    ) :
            BaseViewHolder(binding.root) {
        fun bind(
                items: BookBaseData,
                position: Int,
                listener: BookInterface.View
        ) {
            //VIEW set list[positon]資料
            //4
            binding.infName.setText(items.infName)
            binding.infPlan.setText(items.infPlan)
            binding.infPublishHouse.setText(items.infPublishHouse )
            binding.infISBN.setText(items.infISBN )
            //9
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.infCategory,
                    items.infCategory ,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.infAuthorOrder,
                    items.infAuthorOrder,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.infCorreAuthor,
                    items.infCorreAuthor,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.infAudit,
                    items.infAudit,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.infLanguage,
                    items.infLanguage,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.infCoop,
                    items.infCoop,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.infPubmainLicYear,
                    items.infPubmainLicYear,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.infPubmainLicMonth,
                    items.infPubmainLicMonth,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.infWhemainTher,
                    items.infWhemainTher,
                    context
            )



            //監聽刪除按鈕
            binding.deleteBtn.setOnClickListener {
                //->打刪除API
                listener.onDeleteClick(items.infNumber, position)
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
                    var itemData :BookBaseData =BookBaseData()
                    itemData=items
                    itemData.itemType = Config.EdIT_VIEW_TYPE


                    //如果View 不為空 就set 新的LicEditData
                    if (binding.infName.text.isNotEmpty()) {
                        itemData.infName = binding.infName.text.toString()
                    }
                    if (binding.infPlan.text.isNotEmpty()) {
                        itemData.infPlan = binding.infPlan.text.toString()
                    }
                    if (binding.infPublishHouse.text.isNotEmpty()) {
                        itemData.infPublishHouse = binding.infPublishHouse.text.toString()
                    }
                    if (binding.infISBN.text.isNotEmpty()) {
                        itemData.infISBN = binding.infISBN.text.toString()
                    }
                    //9
                    if (binding.infCategory.selectedItemPosition != -1) {
                        itemData.infCategory =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.infCategory,
                                        context
                                )
                    }
                    if (binding.infAuthorOrder.selectedItemPosition != -1) {
                        itemData.infAuthorOrder =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.infAuthorOrder,
                                        context
                                )
                    }
                    if (binding.infCorreAuthor.selectedItemPosition != -1) {
                        itemData.infCorreAuthor =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.infCorreAuthor,
                                        context
                                )
                    }
                    if (binding.infAudit.selectedItemPosition != -1) {
                        itemData.infAudit =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.infAudit,
                                        context
                                )
                    }
                    if (binding.infLanguage.selectedItemPosition != -1) {
                        itemData.infLanguage =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.infLanguage,
                                        context
                                )
                    }
                    if (binding.infCoop.selectedItemPosition != -1) {
                        itemData.infCoop =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.infCoop,
                                        context
                                )
                    }
                    if (binding.infPubmainLicYear.selectedItemPosition != -1) {
                        itemData.infPubmainLicYear =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.infPubmainLicYear,
                                        context
                                )
                    }
                    if (binding.infPubmainLicMonth.selectedItemPosition != -1) {
                        itemData.infPubmainLicMonth =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.infPubmainLicMonth,
                                        context
                                )
                    }
                    if (binding.infWhemainTher.selectedItemPosition != -1) {
                        itemData.infWhemainTher =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.infWhemainTher,
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
            private val binding: ItemBookAddBinding, //itemView
            private val context: Context
    ) :
            BaseViewHolder(binding.root) {
        fun bind(
                items: BookBaseData,
                position: Int,
                listener: BookInterface.View
        ) {
            //set空的資料在View上
            binding.infName.setText(items.infName)
            binding.infPlan.setText(items.infPlan)
            binding.infPublishHouse.setText(items.infPublishHouse )
            binding.infISBN.setText(items.infISBN )
            //9
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.infCategory,
                    items.infCategory ,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.infAuthorOrder,
                    items.infAuthorOrder,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.infCorreAuthor,
                    items.infCorreAuthor,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.infAudit,
                    items.infAudit,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.infLanguage,
                    items.infLanguage,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.infCoop,
                    items.infCoop,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.infPubmainLicYear,
                    items.infPubmainLicYear,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.infPubmainLicMonth,
                    items.infPubmainLicMonth,
                    context
            )
            bindSpinnerAdapter(
                    R.array.lic_lictype_array_en,
                    binding.infWhemainTher,
                    items.infWhemainTher,
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

                    var itemData :BookBaseData =BookBaseData()
                    itemData=items
                    itemData.itemType = Config.ADD_VIEW_TYPE
//                    val itemData = BookBaseData(
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
                    if (binding.infName.text.isNotEmpty()) {
                        itemData.infName = binding.infName.text.toString()
                    }
                    if (binding.infPlan.text.isNotEmpty()) {
                        itemData.infPlan = binding.infPlan.text.toString()
                    }
                    if (binding.infPublishHouse.text.isNotEmpty()) {
                        itemData.infPublishHouse = binding.infPublishHouse.text.toString()
                    }
                    if (binding.infISBN.text.isNotEmpty()) {
                        itemData.infISBN = binding.infISBN.text.toString()
                    }
                    //9
                    if (binding.infCategory.selectedItemPosition != -1) {
                        itemData.infCategory =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.infCategory,
                                        context
                                )
                    }
                    if (binding.infAuthorOrder.selectedItemPosition != -1) {
                        itemData.infAuthorOrder =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.infAuthorOrder,
                                        context
                                )
                    }
                    if (binding.infCorreAuthor.selectedItemPosition != -1) {
                        itemData.infCorreAuthor =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.infCorreAuthor,
                                        context
                                )
                    }
                    if (binding.infAudit.selectedItemPosition != -1) {
                        itemData.infAudit =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.infAudit,
                                        context
                                )
                    }
                    if (binding.infLanguage.selectedItemPosition != -1) {
                        itemData.infLanguage =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.infLanguage,
                                        context
                                )
                    }
                    if (binding.infCoop.selectedItemPosition != -1) {
                        itemData.infCoop =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.infCoop,
                                        context
                                )
                    }
                    if (binding.infPubmainLicYear.selectedItemPosition != -1) {
                        itemData.infPubmainLicYear =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.infPubmainLicYear,
                                        context
                                )
                    }
                    if (binding.infPubmainLicMonth.selectedItemPosition != -1) {
                        itemData.infPubmainLicMonth =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.infPubmainLicMonth,
                                        context
                                )
                    }
                    if (binding.infWhemainTher.selectedItemPosition != -1) {
                        itemData.infWhemainTher =
                                getResponseSpinner(
                                        R.array.lic_lictype_array_en,
                                        binding.infWhemainTher,
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