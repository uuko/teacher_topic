package com.example.linteacher.util

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import com.example.linteacher.ui.main.announce.Content
import java.text.SimpleDateFormat
import java.util.*

open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun pareDate(data: String): String {

        return "${data.split("T")[0]}-${data.split("T")[1].split(":")[0]}:${
            data.split("T")[1].split(
                ":"
            )[1]
        }"

    }

    fun handleContent(articleContent: String): List<Content.ContentData> {
        val contentLst = mutableListOf<Content.ContentData>()
        if (articleContent.contains("<img>")) {
            val splitString = articleContent.split("<img>")
            Log.d("splitString", "handleContent: $splitString  size ${splitString.size}")
            for (i in splitString.indices) {
                if (i > 3) break
                if (i % 2 == 1) {
                    contentLst.add(Content.ContentData(splitString[i], Config.PIC))
                } else {
                    contentLst.add(Content.ContentData(splitString[i], Config.TEXTVIEW))
                }
            }
        } else {
            var data = ""
            if (articleContent.length > 50) data = articleContent.substring(0, 50) + "...."
            else data = articleContent
            contentLst.add(Content.ContentData(data, Config.TEXTVIEW))
        }
        return contentLst
    }

    fun bindSpinnerAdapter(array: Int, spinner: Spinner, data: String, context: Context) {
        val adapter =
            ArrayAdapter.createFromResource(
                context,
                array,
                android.R.layout.simple_spinner_item
            )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(getArraySelection(data, array, context), false)
        spinner.onItemSelectedListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                spinner.setSelection(pos, false)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    fun datePicker(v: View) {
        val calendar: Calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR) //取得現在的日期年月日
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(
            v.context,
            { view, year, month, day ->
                val calendar = Calendar.getInstance()
                calendar[year, month] = day

                val format = SimpleDateFormat("yyyy-MM-dd")
                val strDate = format.format(calendar.time)
                (v as EditText).setText(strDate)
            }, year, month, day
        ).show()
    }

    private fun getArraySelection(data: String, arrayRes: Int, context: Context): Int {
        val array = context?.resources?.getStringArray(arrayRes)
        for (i in array!!.indices) {
            if (data == array[i]) {
                return i
            }
        }
        return 0
    }

    private fun getArrayChgSelection(
        data: String,
        arrayRes: ArrayList<String>,
        context: Context
    ): Int {

        for (i in arrayRes.indices) {
            if (data == arrayRes[i]) {
                return i
            }
        }
        return 0
    }

    fun getResponseSpinner(arrayEn: Int, spinner: Spinner, context: Context): String {
        val array = context?.resources?.getStringArray(arrayEn)
        return array?.get(spinner.selectedItemPosition).toString()
    }

    fun getResponseArraySpinner(
        array: ArrayList<String>,
        spinner: Spinner,
        context: Context
    ): String {
        return array?.get(spinner.selectedItemPosition).toString()
    }

    fun bindSpinnerArrayAdapter(
        array: ArrayList<String>,
        spinner: Spinner,
        data: String,
        context: Context
    ) {
        val adapter = ArrayAdapter(
            context, android.R.layout.simple_spinner_dropdown_item, array
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(getArrayChgSelection(data, array, context), false)
        spinner.onItemSelectedListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                spinner.setSelection(pos, false)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    fun generateArray100Year(): ArrayList<String> {
        val prev100Year: Calendar = Calendar.getInstance()
        prev100Year.add(Calendar.YEAR, -100)
        val nowYear: Calendar = Calendar.getInstance()
        nowYear.add(Calendar.YEAR, 0)
        val last100 = prev100Year.get(Calendar.YEAR)
        val now = nowYear.get(Calendar.YEAR)
        val array = arrayListOf<String>()
        for (i in last100..now) {
            array.add(i.toString())
        }
        return array
    }
}