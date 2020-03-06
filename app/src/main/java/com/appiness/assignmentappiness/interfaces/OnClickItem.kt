package com.appiness.assignmentappiness.interfaces

import android.widget.ImageView
import android.widget.TextView

interface OnClickItem<T> {
    fun onClick(position:Int, t:T?=null)
}