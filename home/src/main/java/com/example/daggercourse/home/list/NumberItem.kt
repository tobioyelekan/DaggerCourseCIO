package com.example.daggercourse.home.list

import com.example.daggercourse.poweradapter.RecyclerItem

data class NumberItem(val number: Int) : RecyclerItem {
    override fun getId(): Long {
        return number.toLong()
    }
}