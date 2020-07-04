package com.mtan.newsdaily.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ListView

class ExtListView(context: Context, attrs: AttributeSet): ListView(context, attrs) {

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
    }


}