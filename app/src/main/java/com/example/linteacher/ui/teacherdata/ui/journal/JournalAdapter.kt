package com.example.linteacher.ui.teacherdata.ui.journal

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.linteacher.R
import com.example.linteacher.api.pojo.teacherdata.dis.ui.DisAddData
import com.example.linteacher.api.pojo.teacherdata.dis.ui.DisBaseData
import com.example.linteacher.api.pojo.teacherdata.dis.ui.DisEditData
import com.example.linteacher.api.pojo.teacherdata.dis.ui.DisOriginData
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicAddData
import com.example.linteacher.api.pojo.teacherdata.license.ui.LicEditData
import com.example.linteacher.databinding.ItemDisAddBinding
import com.example.linteacher.databinding.ItemDisEditBinding
import com.example.linteacher.databinding.ItemDisOriginBinding
import com.example.linteacher.util.BaseAdapter
import com.example.linteacher.util.BaseViewHolder
import com.example.linteacher.util.Config

class JournalAdapter(
    list: ArrayList<DisBaseData>, private val listener: JournalInterface.View
) : BaseAdapter(list) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Config.ADD_VIEW_TYPE) {
            val itemBinding =
                ItemDisAddBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            AddViewHolder(itemBinding, parent.context)
        } else if (viewType == Config.EdIT_VIEW_TYPE) {
            val itemBinding =
                ItemDisEditBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            EditViewHolder(itemBinding, parent.context)
        } else {
            val itemBinding =
                ItemDisOriginBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            OriginViewHolder(itemBinding, parent.context)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        if (viewType == Config.ADD_VIEW_TYPE) {
            (holder as AddViewHolder).bind(list.get(position) as DisAddData, position, listener)
        } else if (viewType == Config.EdIT_VIEW_TYPE) {
            (holder as EditViewHolder)
                .bind(
                    list.get(position)
                            as DisEditData, position, listener
                )
        } else {
            (holder as OriginViewHolder).bind(
                list[position] as DisOriginData,
                position,
                listener
            )
        }
    }

    private class OriginViewHolder(
        private val binding: ItemDisOriginBinding,
        private val context: Context
    ) : BaseViewHolder(binding.root) {
        fun bind(
            items: DisOriginData,
            position: Int,
            listener: JournalInterface.View
        ) {
            binding.disSeminarName.text = items.disSeminarName
            binding.dismainThesisName.text = items.dismain_thesisName
            binding.disFD.text = items.disFD
            binding.disED.text = items.disED
            binding.editButton.setOnClickListener {
                listener.onEditClick(items, position)
            }
        }

    }

    private class EditViewHolder(
        private val binding: ItemDisEditBinding,
        private val context: Context
    ) :
        BaseViewHolder(binding.root) {
        fun bind(
            items: DisEditData,
            position: Int,
            listener: JournalInterface.View
        ) {
            binding.disProject.setText(items.disProject)
            binding.disSeminarName.setText(items.disSeminarName)
            binding.dismainThesisName.setText(items.dismain_thesisName)
            binding.disFD.setText(items.disFD)
            binding.disED.setText(items.disED)
            binding.disHostCity.setText(items.disHostCity)
            binding.disED.setOnClickListener {
                datePicker(binding.disED)
            }
            binding.disFD.setOnClickListener {
                datePicker(binding.disFD)
            }
            bindSpinnerAdapter(
                R.array.disAuthor_array_en,
                binding.disAuthor,
                items.disAuthor,
                context
            )
            bindSpinnerAdapter(
                R.array.tch_not_en,
                binding.disCorreAuthor,
                items.disCorreAuthor,
                context
            )
            bindSpinnerAdapter(
                R.array.tch_not_en,
                binding.disReviewer,
                items.disReviewer,
                context
            )
            bindSpinnerAdapter(
                R.array.tch_country_en,
                binding.disHostCountry,
                items.disHostCountry,
                context
            )
            bindSpinnerArrayAdapter(
                generateArray100Year(),
                binding.disPublishY,
                items.disPublishY,
                context
            )
            binding.deleteBtn.setOnClickListener {
                listener.onDeleteClick(items.disId, position)
            }

            binding.cancelButton.setOnClickListener {
                listener.onEditCancelClick(position, items)
            }


            binding.saveBtn.setOnClickListener {

                if (validateData()) {
                    val itemData = DisEditData(
                        dismain_thesisName = items.dismain_thesisName,
                        disSeminarName = items.disSeminarName,
                        disFD = items.disFD,
                        disED = items.disED,
                        disId = items.disId,
                        disProject = items.disProject,
                        disCorreAuthor = items.disCorreAuthor,
                        disReviewer = items.disReviewer,
                        disHostCity = items.disHostCity,
                        disHostCountry = items.disHostCountry,
                        disPublishY = items.disPublishY,
                        disAuthor = items.disAuthor
                    )
                    if (binding.disSeminarName.text.isNotEmpty()) {
                        itemData.disSeminarName = binding.disSeminarName.text.toString()
                    }
                    if (binding.disProject.text.isNotEmpty()) {
                        itemData.disProject = binding.disProject.text.toString()
                    }
                    if (binding.dismainThesisName.text.isNotEmpty()) {
                        itemData.dismain_thesisName = binding.dismainThesisName.text.toString()
                    }
                    if (binding.disFD.text.isNotEmpty()) {
                        itemData.disFD = binding.disFD.text.toString()
                    }
                    if (binding.disED.text.isNotEmpty()) {
                        itemData.disED = binding.disED.text.toString()
                    }
                    if (binding.disHostCity.text.isNotEmpty()) {
                        itemData.disHostCity = binding.disHostCity.text.toString()
                    }
                    if (binding.disAuthor.selectedItemPosition != -1) {
                        itemData.disAuthor =
                            getResponseSpinner(
                                R.array.disAuthor_array_en,
                                binding.disAuthor,
                                context
                            )
                    }
                    if (binding.disCorreAuthor.selectedItemPosition != -1) {
                        itemData.disCorreAuthor =
                            getResponseSpinner(
                                R.array.tch_not_en,
                                binding.disCorreAuthor,
                                context
                            )
                    }
                    if (binding.disReviewer.selectedItemPosition != -1) {
                        itemData.disReviewer =
                            getResponseSpinner(
                                R.array.tch_not_en,
                                binding.disReviewer,
                                context
                            )
                    }
                    if (binding.disHostCountry.selectedItemPosition != -1) {
                        itemData.disHostCountry =
                            getResponseSpinner(
                                R.array.tch_country_en,
                                binding.disHostCountry,
                                context
                            )
                    }
                    if (binding.disPublishY.selectedItemPosition != -1) {
                        itemData.disPublishY =
                            getResponseArraySpinner(
                                generateArray100Year(),
                                binding.disPublishY,
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
        private val binding: ItemDisAddBinding,
        private val context: Context
    ) :
        BaseViewHolder(binding.root) {
        fun bind(
            items: DisAddData,
            position: Int,
            listener: JournalInterface.View
        ) {
            binding.disProject.setText(items.disProject)
            binding.disSeminarName.setText(items.disSeminarName)
            binding.dismainThesisName.setText(items.dismain_thesisName)
            binding.disFD.setText(items.disFD)
            binding.disED.setText(items.disED)
            binding.disHostCity.setText(items.disHostCity)

            binding.disED.setOnClickListener {
                datePicker(binding.disED)
            }
            binding.disFD.setOnClickListener {
                datePicker(binding.disFD)
            }
            bindSpinnerAdapter(
                R.array.disAuthor_array_en,
                binding.disAuthor,
                items.disAuthor,
                context
            )
            bindSpinnerAdapter(
                R.array.tch_not_en,
                binding.disCorreAuthor,
                items.disCorreAuthor,
                context
            )
            bindSpinnerAdapter(
                R.array.tch_not_en,
                binding.disReviewer,
                items.disReviewer,
                context
            )
            bindSpinnerAdapter(
                R.array.tch_country_en,
                binding.disHostCountry,
                items.disHostCountry,
                context
            )
            bindSpinnerArrayAdapter(
                generateArray100Year(),
                binding.disPublishY,
                items.disPublishY,
                context
            )
            binding.cancelButton.setOnClickListener {
                listener.onCancelClick(position)
            }
            binding.saveBtn.setOnClickListener {

                if (validateData()) {
                    val itemData = DisAddData(
                        dismain_thesisName = items.dismain_thesisName,
                        disSeminarName = items.disSeminarName,
                        disFD = items.disFD,
                        disED = items.disED,
                        disProject = items.disProject,
                        disCorreAuthor = items.disCorreAuthor,
                        disReviewer = items.disReviewer,
                        disHostCity = items.disHostCity,
                        disHostCountry = items.disHostCountry,
                        disPublishY = items.disPublishY,
                        disAuthor = items.disAuthor
                    )
                    if (binding.disProject.text.isNotEmpty()) {
                        itemData.disProject = binding.disProject.text.toString()
                    }
                    if (binding.disSeminarName.text.isNotEmpty()) {
                        itemData.disSeminarName = binding.disSeminarName.text.toString()
                    }
                    if (binding.dismainThesisName.text.isNotEmpty()) {
                        itemData.dismain_thesisName = binding.dismainThesisName.text.toString()
                    }
                    if (binding.disFD.text.isNotEmpty()) {
                        itemData.disFD = binding.disFD.text.toString()
                    }
                    if (binding.disED.text.isNotEmpty()) {
                        itemData.disED = binding.disED.text.toString()
                    }
                    if (binding.disHostCity.text.isNotEmpty()) {
                        itemData.disHostCity = binding.disHostCity.text.toString()
                    }
                    if (binding.disAuthor.selectedItemPosition != -1) {
                        itemData.disAuthor =
                            getResponseSpinner(
                                R.array.disAuthor_array_en,
                                binding.disAuthor,
                                context
                            )
                    }
                    if (binding.disCorreAuthor.selectedItemPosition != -1) {
                        itemData.disCorreAuthor =
                            getResponseSpinner(
                                R.array.tch_not_en,
                                binding.disCorreAuthor,
                                context
                            )
                    }
                    if (binding.disReviewer.selectedItemPosition != -1) {
                        itemData.disReviewer =
                            getResponseSpinner(
                                R.array.tch_not_en,
                                binding.disReviewer,
                                context
                            )
                    }
                    if (binding.disHostCountry.selectedItemPosition != -1) {
                        itemData.disHostCountry =
                            getResponseSpinner(
                                R.array.tch_country_en,
                                binding.disHostCountry,
                                context
                            )
                    }
                    if (binding.disPublishY.selectedItemPosition != -1) {
                        itemData.disPublishY =
                            getResponseArraySpinner(
                                generateArray100Year(),
                                binding.disPublishY,
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