package com.example.linteacher.util

import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.transition.Fade
import androidx.transition.TransitionManager
import java.text.SimpleDateFormat
import java.util.*

abstract class BaseFragment : Fragment() {
    open fun onFragmentBackPressed() {
        // add code in super class when override
    }

    fun pareDate(data: String): String {

        return "${data.split("T")[0]}-${data.split("T")[1].split(":")[0]}:${
            data.split("T")[1].split(
                ":"
            )[1]
        }"

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
        spinner.setSelection(getArraySelection(data, array), false)
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
    private fun getArraySelection(data: String,arrayRes:Int): Int {
        val array = context?.resources?.getStringArray(arrayRes)
        for (i in array!!.indices) {
            if (data == array[i]) {
                return i
            }
        }
        return 0
    }

     fun toggleView(target:Int,visible:Boolean,childView: View) {
        val transition =  Fade();
        transition.duration = 300;
        transition.addTarget(target);

        TransitionManager.beginDelayedTransition(childView.parent as ViewGroup, transition);
        if (visible){
            childView.visibility = View.VISIBLE;
        }
        else{
            childView.visibility=View.GONE
        }

    }

}