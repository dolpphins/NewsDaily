package com.mtan.newsdaily.core

import java.net.URL

object HttpApi {

    private const val DAY_URL = "http://news-at.zhihu.com/api/3/news/before/%s"

    fun get(day: String): String {
        val url = DAY_URL.format(day)
        return URL(url).readText()
    }
}