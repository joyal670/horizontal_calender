package com.calender.ch

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.calender.horizontalcalenderview.adapter.DaysOfMonthAdapter
import com.calender.horizontalcalenderview.model.DateModal
import kotlinx.android.synthetic.main.layout.view.*
import java.util.*
import kotlin.collections.ArrayList

class HoriCal(context: Context) :RecyclerView(context)
{

    private val calendar = Calendar.getInstance()
    private var currentMonth = 0
    private val dateList: MutableList<Date> = mutableListOf()
    private var scrollPosition = 0
    private val dateModifiedList:ArrayList<DateModal> =ArrayList()
    val listMonths = listOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November","December")

    init {
        inflate(getContext(),R.layout.layout,this)


        calendar.time = Date()
        currentMonth = calendar[Calendar.MONTH]
        tvCurrentMonth.text=listMonths[currentMonth]
        setDates(getFutureDatesOfCurrentMonth())
    }

    private fun getFutureDatesOfCurrentMonth(): List<Date> {
        currentMonth = calendar[Calendar.MONTH]
        return getDates(mutableListOf())
    }

    private fun setDates(newDateList: List<Date>) {
        dateList.clear()
        dateModifiedList.clear()
        dateList.addAll(newDateList)
        dateList.forEach {
            dateModifiedList.add(
                    DateModal(
                            it.date,
                            false
                    )
            )
        }
        rvHorizontal.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        rvHorizontal.adapter= DaysOfMonthAdapter(context,dateModifiedList,dateList)

        if (scrollPosition > dateList.size - 1)
            scrollPosition = dateList.size - 1
        rvHorizontal.scrollToPosition(scrollPosition)
    }

    private fun getDates(list: MutableList<Date>): List<Date> {
        calendar.set(Calendar.MONTH, currentMonth)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        list.add(calendar.time)
        while (currentMonth == calendar[Calendar.MONTH]) {
            calendar.add(Calendar.DATE, +1)
            if (calendar[Calendar.MONTH] == currentMonth)
                list.add(calendar.time)
        }
        calendar.add(Calendar.DATE, -1)
        return list
    }
}