package com.mtan.newsdaily.core

import android.util.Log
import com.mtan.newsdaily.bean.News
import org.json.JSONObject

class Requester {

    private val TAG = "Requester"

    fun request(day: String): List<News> {
        // 请求
        val json = HttpApi.get(day)
        Log.i(TAG, json)
        // 解析
        val list: ArrayList<News> = ArrayList()
        val array = JSONObject(json).getJSONArray("stories")
        for (i in 0 until array.length()) {
            var e: JSONObject = array.get(i) as JSONObject
            val title = e.getString("title")
            val url = e.getString("url")
            val hint = e.getString("hint")
            val images: ArrayList<String> = ArrayList()

            if (!e.isNull("images")) {
                val imagesArray = e.getJSONArray("images")
                for (j in 0 until imagesArray.length()) {
                    images.add(imagesArray[j] as String)
                }
            }

            list.add(News(title, url, hint, images))
        }

        // 打印日志
        for (i in 0 until list.size) {
            val news = list[i]
            Log.i(TAG, news.title)
            Log.i(TAG, news.url)
            Log.i(TAG, news.hint)
            Log.i(TAG, "$news.images.size()")
        }

        return list

    }
}