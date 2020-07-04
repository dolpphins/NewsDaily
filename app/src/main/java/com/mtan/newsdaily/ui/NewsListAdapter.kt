package com.mtan.newsdaily.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.mtan.newsdaily.R
import com.mtan.newsdaily.bean.News

class NewsListAdapter(var context: Context, var list: List<News>) : BaseAdapter() {

    fun setData(newList: List<News>) {
        list = newList
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var holder: ViewHolder
        var view: View
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_news_list_item,
                parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }
        var news = list[position]
        var images = news.images
        var defaultDrawable = ColorDrawable(Color.parseColor("#E4E4E4"))
        if (images == null || images.isEmpty()) {
            holder.iconView.setImageDrawable(defaultDrawable)
        } else {
            Glide.with(context)
                .load(images[0])
                .placeholder(defaultDrawable)
                .into(holder.iconView)
        }
        holder.titleView.text = news.title
        holder.descView.text = news.hint
        return view
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return if (list == null) {
            0
        } else {
            list.size
        }
    }

    private class ViewHolder(view: View) {
        var iconView: ImageView = view.findViewById(R.id.icon_view)
        var titleView: TextView = view.findViewById(R.id.title_view)
        var descView: TextView = view.findViewById(R.id.desc_view)
    }
}