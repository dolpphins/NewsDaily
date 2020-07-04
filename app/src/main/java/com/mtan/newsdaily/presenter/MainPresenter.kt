package com.mtan.newsdaily.presenter

import com.mtan.newsdaily.MainActivity
import com.mtan.newsdaily.bean.News
import com.mtan.newsdaily.core.Requester
import com.mtan.newsdaily.util.TimeUtil
import kotlinx.coroutines.*

class MainPresenter(var activity: MainActivity) {

    private var mList = ArrayList<News>()
    private var mNewsLastTime: Long = 0

    private var mIsRefreshing = false;
    private var mIsLoadingMore = false;

    fun loadFirst() {
        GlobalScope.launch(Dispatchers.Main) {
            if (mIsRefreshing) {
                return@launch
            }
            if (mIsLoadingMore) {
                mIsLoadingMore = false
            }
            mIsRefreshing = true
            mNewsLastTime = System.currentTimeMillis()
            val list = withContext(Dispatchers.IO) {
                requestTwoDayNews()
            }
            mNewsLastTime -= 2 * TimeUtil.DAY
            mList.clear()
            mList.addAll(list)
            activity.onLoadFirstFinish(mList)
            mIsRefreshing = false
        }
    }

    fun loadMore() {
        GlobalScope.launch(Dispatchers.Main) {
            if (mIsRefreshing) {
                return@launch
            }
            if (mIsLoadingMore) {
                return@launch
            }
            mIsLoadingMore = true
            val list = withContext(Dispatchers.IO) {
                requestTwoDayNews()
            }
            if (mIsRefreshing
                || !mIsLoadingMore) {
                return@launch
            }
            mList.addAll(list)
            activity.onLoadMoreFinish(mList)
            mIsLoadingMore = false
        }
    }

    private fun requestTwoDayNews(): List<News> {
        val requester = Requester()
        val yesterdayList = requester.request(TimeUtil.getDayStr(mNewsLastTime - TimeUtil.DAY))
        val list = ArrayList<News>()
        list.addAll(yesterdayList)
        val todayList = requester.request(TimeUtil.getDayStr(mNewsLastTime))
        list.addAll(todayList)
        return list
    }
}