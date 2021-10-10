package com.example.linteacher.ui.teacherdata.ui.paper

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.linteacher.R
import com.example.linteacher.api.pojo.teacherdata.paper.ui.PaperAddData
import com.example.linteacher.api.pojo.teacherdata.paper.ui.PaperBaseData
import com.example.linteacher.api.pojo.teacherdata.paper.ui.PaperEditData
import com.example.linteacher.api.pojo.teacherdata.paper.ui.PaperOriginData
import com.example.linteacher.databinding.*
import com.example.linteacher.util.BaseAdapter
import com.example.linteacher.util.BaseViewHolder
import com.example.linteacher.util.Config
import java.util.*

class PaperAdapter(
    list: ArrayList<PaperBaseData>, private val listener: PaperInterface.View
) : BaseAdapter(list) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Config.ADD_VIEW_TYPE) {
            val itemBinding =
                ItemPaperAddBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            AddViewHolder(itemBinding, parent.context)
        } else if (viewType == Config.EdIT_VIEW_TYPE) {
            val itemBinding =
                ItemPaperEditBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            EditViewHolder(itemBinding, parent.context)
        } else {
            val itemBinding =
                ItemPaperOriginBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            OriginViewHolder(itemBinding, parent.context)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        if (viewType == Config.ADD_VIEW_TYPE) {
            (holder as AddViewHolder).bind(list.get(position) as PaperAddData, position, listener)
        } else if (viewType == Config.EdIT_VIEW_TYPE) {
            (holder as EditViewHolder)
                .bind(
                    list[position]
                            as PaperEditData, position, listener
                )
        } else {
            (holder as OriginViewHolder).bind(
                list[position] as PaperOriginData,
                position,
                listener
            )
        }
    }

    private class OriginViewHolder(
        private val binding: ItemPaperOriginBinding,
        private val context: Context
    ) : BaseViewHolder(binding.root) {
        fun bind(
            items: PaperOriginData,
            position: Int,
            listener: PaperInterface.View
        ) {
            binding.theAuthor.text = items.theAuthor
            binding.themainThesisName.text = items.themain_thesisName
            binding.thePublishYear.text = items.thePublishYear
            binding.editButton.setOnClickListener {
                listener.onEditClick(items, position)
            }
        }

    }

    private class EditViewHolder(
        private val binding: ItemPaperEditBinding,
        private val context: Context
    ) :
        BaseViewHolder(binding.root) {
        fun bind(
            items: PaperEditData,
            position: Int,
            listener: PaperInterface.View
        ) {
            binding.theProject.setText(items.theProject)
            binding.themainThesisName.setText(items.themain_thesisName)
            binding.theCoupons.setText(items.theCoupons)
            binding.thePubmainLicationName.setText(items.thePubmain_licationName)
            binding.thePubmainLicatinNumber.setText(items.thePubmain_licatinNumber)

//        thePublishYear

            bindSpinnerArrayAdapter(
                generateArray100Year(),
                binding.thePublishYear,
                items.thePublishYear,
                context
            )
            bindSpinnerAdapter(
                R.array.month_array_en,
                binding.thePublishMonth,
                items.thePublishMonth,
                context
            )
            bindSpinnerAdapter(
                R.array.theTransCooperation_array_en,
                binding.theTransCooperation,
                items.theTransCooperation,
                context
            )
            bindSpinnerAdapter(
                R.array.tch_country_en,
                binding.thePublishingcountry,
                items.thePublishingcountry,
                context
            )
            bindSpinnerAdapter(
                R.array.tch_abor_en,
                binding.theReviewer,
                items.theReviewer,
                context
            )
            bindSpinnerAdapter(
                R.array.thePublishType_array_en,
                binding.thePublishType,
                items.thePublishType,
                context
            )
            bindSpinnerAdapter(
                R.array.theAuthor_array_en,
                binding.theAuthor,
                items.theAuthor,
                context
            )
            bindSpinnerAdapter(
                R.array.tch_abor_en,
                binding.theCorreAuthor,
                items.theCorreAuthor,
                context
            )
            bindSpinnerAdapter(
                R.array.theCollCategory_array_en,
                binding.theCollCategory,
                items.theCollCategory,
                context
            )

            binding.deleteBtn.setOnClickListener {
                listener.onDeleteClick(items.theId, position)
            }

            binding.cancelButton.setOnClickListener {
                listener.onEditCancelClick(position, items)
            }

//
            binding.saveBtn.setOnClickListener {

                if (validateData()) {
                    val itemData = PaperEditData(
                        theId = items.theId,
                        themain_thesisName = items.themain_thesisName,
                        theAuthor = items.theAuthor,
                        theProject = items.theProject,
                        theCorreAuthor = items.theCorreAuthor,
                        theCollCategory = items.theCollCategory,
                        thePubmain_licationName = items.thePubmain_licationName,
                        theCoupons = items.theCoupons,
                        thePubmain_licatinNumber = items.thePubmain_licatinNumber,
                        thePublishYear = items.thePublishYear,
                        thePublishMonth = items.thePublishMonth,
                        thePublishType = items.thePublishType,
                        theReviewer = items.theReviewer,
                        theTransCooperation = items.theTransCooperation,
                        thePublishingcountry = items.thePublishingcountry,
                    )

                    if (binding.theProject.text.isNotEmpty()) {
                        itemData.theProject = binding.theProject.text.toString()
                    }
                    if (binding.themainThesisName.text.isNotEmpty()) {
                        itemData.themain_thesisName = binding.themainThesisName.text.toString()
                    }
                    if (binding.theCoupons.text.isNotEmpty()) {
                        itemData.theCoupons = binding.theCoupons.text.toString()
                    }
                    if (binding.thePubmainLicationName.text.isNotEmpty()) {
                        itemData.thePubmain_licationName =
                            binding.thePubmainLicationName.text.toString()
                    }
                    if (binding.thePubmainLicatinNumber.text.isNotEmpty()) {
                        itemData.thePubmain_licatinNumber =
                            binding.thePubmainLicatinNumber.text.toString()
                    }


                    if (binding.thePublishYear.selectedItemPosition != -1) {
                        itemData.thePublishYear =
                            getResponseArraySpinner(
                                generateArray100Year(),
                                binding.thePublishYear,
                                context
                            )
                    }
                    if (binding.thePublishMonth.selectedItemPosition != -1) {
                        itemData.thePublishMonth =
                            getResponseSpinner(
                                R.array.month_array_en,
                                binding.thePublishMonth,
                                context
                            )
                    }

                    if (binding.theTransCooperation.selectedItemPosition != -1) {
                        itemData.theTransCooperation =
                            getResponseSpinner(
                                R.array.theTransCooperation_array_en,
                                binding.theTransCooperation,
                                context
                            )
                    }

                    if (binding.thePublishingcountry.selectedItemPosition != -1) {
                        itemData.thePublishingcountry =
                            getResponseSpinner(
                                R.array.tch_country_en,
                                binding.thePublishingcountry,
                                context
                            )
                    }
                    if (binding.theReviewer.selectedItemPosition != -1) {
                        itemData.theReviewer =
                            getResponseSpinner(
                                R.array.tch_abor_en,
                                binding.theReviewer,
                                context
                            )
                    }
                    if (binding.thePublishType.selectedItemPosition != -1) {
                        itemData.thePublishType =
                            getResponseSpinner(
                                R.array.thePublishType_array_en,
                                binding.thePublishType,
                                context
                            )
                    }
                    if (binding.theAuthor.selectedItemPosition != -1) {
                        itemData.theAuthor =
                            getResponseSpinner(
                                R.array.theAuthor_array_en,
                                binding.theAuthor,
                                context
                            )
                    }
                    if (binding.theCorreAuthor.selectedItemPosition != -1) {
                        itemData.theCorreAuthor =
                            getResponseSpinner(
                                R.array.tch_abor_en,
                                binding.theCorreAuthor,
                                context
                            )
                    }
                    if (binding.theCollCategory.selectedItemPosition != -1) {
                        itemData.theCollCategory =
                            getResponseSpinner(
                                R.array.theCollCategory_array_en,
                                binding.theCollCategory,
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
        private val binding: ItemPaperAddBinding,
        private val context: Context
    ) :
        BaseViewHolder(binding.root) {
        fun bind(
            items: PaperAddData,
            position: Int,
            listener: PaperInterface.View
        ) {
            binding.theProject.setText(items.theProject)
            binding.themainThesisName.setText(items.themain_thesisName)
            binding.theCoupons.setText(items.theCoupons)
            binding.thePubmainLicationName.setText(items.thePubmain_licationName)
            binding.thePubmainLicatinNumber.setText(items.thePubmain_licatinNumber)

//        thePublishYear

            bindSpinnerArrayAdapter(
                generateArray100Year(),
                binding.thePublishYear,
                items.thePublishYear,
                context
            )
            bindSpinnerAdapter(
                R.array.month_array_en,
                binding.thePublishMonth,
                items.thePublishMonth,
                context
            )
            bindSpinnerAdapter(
                R.array.theTransCooperation_array_en,
                binding.theTransCooperation,
                items.theTransCooperation,
                context
            )
            bindSpinnerAdapter(
                R.array.tch_country_en,
                binding.thePublishingcountry,
                items.thePublishingcountry,
                context
            )
            bindSpinnerAdapter(
                R.array.tch_abor_en,
                binding.theReviewer,
                items.theReviewer,
                context
            )
            bindSpinnerAdapter(
                R.array.thePublishType_array_en,
                binding.thePublishType,
                items.thePublishType,
                context
            )
            bindSpinnerAdapter(
                R.array.theAuthor_array_en,
                binding.theAuthor,
                items.theAuthor,
                context
            )
            bindSpinnerAdapter(
                R.array.tch_abor_en,
                binding.theCorreAuthor,
                items.theCorreAuthor,
                context
            )
            bindSpinnerAdapter(
                R.array.theCollCategory_array_en,
                binding.theCollCategory,
                items.theCollCategory,
                context
            )
            binding.cancelButton.setOnClickListener {
                listener.onCancelClick(position)
            }
            binding.saveBtn.setOnClickListener {

                if (validateData()) {
                    val itemData = PaperAddData(
                        theId = items.theId,
                        themain_thesisName = items.themain_thesisName,
                        theAuthor = items.theAuthor,
                        itemType = items.itemType,
                        theProject = items.theProject,
                        theCorreAuthor = items.theCorreAuthor,
                        theCollCategory = items.theCollCategory,
                        thePubmain_licationName = items.thePubmain_licationName,
                        theCoupons = items.theCoupons,
                        thePubmain_licatinNumber = items.thePubmain_licatinNumber,
                        thePublishYear = items.thePublishYear,
                        thePublishMonth = items.thePublishMonth,
                        thePublishType = items.thePublishType,
                        theReviewer = items.theReviewer,
                        theTransCooperation = items.theTransCooperation,
                        thePublishingcountry = items.thePublishingcountry,
                    )

                    if (binding.theProject.text.isNotEmpty()) {
                        itemData.theProject = binding.theProject.text.toString()
                    }
                    if (binding.themainThesisName.text.isNotEmpty()) {
                        itemData.themain_thesisName = binding.themainThesisName.text.toString()
                    }
                    if (binding.theCoupons.text.isNotEmpty()) {
                        itemData.theCoupons = binding.theCoupons.text.toString()
                    }
                    if (binding.thePubmainLicationName.text.isNotEmpty()) {
                        itemData.thePubmain_licationName = binding.thePubmainLicationName.text.toString()
                    }
                    if (binding.thePubmainLicatinNumber.text.isNotEmpty()) {
                        itemData.thePubmain_licatinNumber = binding.thePubmainLicatinNumber.text.toString()
                    }


                    if (binding.thePublishYear.selectedItemPosition != -1) {
                        itemData.thePublishYear =
                            getResponseArraySpinner(
                                generateArray100Year(),
                                binding.thePublishYear,
                                context
                            )
                    }
                    if (binding.thePublishMonth.selectedItemPosition != -1) {
                        itemData.thePublishMonth =
                            getResponseSpinner(
                                R.array.month_array_en,
                                binding.thePublishMonth,
                                context
                            )
                    }

                    if (binding.theTransCooperation.selectedItemPosition != -1) {
                        itemData.theTransCooperation =
                            getResponseSpinner(
                                R.array.theTransCooperation_array_en,
                                binding.theTransCooperation,
                                context
                            )
                    }

                    if (binding.thePublishingcountry.selectedItemPosition != -1) {
                        itemData.thePublishingcountry =
                            getResponseSpinner(
                                R.array.tch_country_en,
                                binding.thePublishingcountry,
                                context
                            )
                    }
                    if (binding.theReviewer.selectedItemPosition != -1) {
                        itemData.theReviewer =
                            getResponseSpinner(
                                R.array.tch_abor_en,
                                binding.theReviewer,
                                context
                            )
                    }
                    if (binding.thePublishType.selectedItemPosition != -1) {
                        itemData.thePublishType =
                            getResponseSpinner(
                                R.array.thePublishType_array_en,
                                binding.thePublishType,
                                context
                            )
                    }
                    if (binding.theAuthor.selectedItemPosition != -1) {
                        itemData.theAuthor =
                            getResponseSpinner(
                                R.array.theAuthor_array_en,
                                binding.theAuthor,
                                context
                            )
                    }
                    if (binding.theCorreAuthor.selectedItemPosition != -1) {
                        itemData.theCorreAuthor =
                            getResponseSpinner(
                                R.array.tch_abor_en,
                                binding.theCorreAuthor,
                                context
                            )
                    }
                    if (binding.theCollCategory.selectedItemPosition != -1) {
                        itemData.theCollCategory =
                            getResponseSpinner(
                                R.array.theCollCategory_array_en,
                                binding.theCollCategory,
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