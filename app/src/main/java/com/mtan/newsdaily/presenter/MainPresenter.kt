package com.mtan.newsdaily.presenter

import android.os.Handler
import android.os.Looper
import com.mtan.newsdaily.MainActivity
import com.mtan.newsdaily.bean.News
import com.mtan.newsdaily.core.Requester
import com.mtan.newsdaily.util.TimeUtil

class MainPresenter(var activity: MainActivity) {

    private var mList = ArrayList<News>()
    private var mNewsLastTime: Long = 0

    private var mIsRefreshing = false;
    private var mIsLoadingMore = false;

    fun loadFirst() {
        if (mIsRefreshing) {
            return
        }
        if (mIsLoadingMore) {
            mIsLoadingMore = false
        }
        mIsRefreshing = true
        mList.clear()
        Thread{
            val requester = Requester()
            mNewsLastTime = System.currentTimeMillis()
            val yesterdayList = requester.request(TimeUtil.getDayStr(mNewsLastTime - TimeUtil.DAY))
            mList.addAll(yesterdayList)
            val todayList = requester.request(TimeUtil.getDayStr(mNewsLastTime))
            mList.addAll(todayList)
            mNewsLastTime -= 2 * TimeUtil.DAY
            Handler(Looper.getMainLooper()).post {
                activity.onLoadFirstFinish(mList)
                mIsRefreshing = false
            }
        }.start()
    }

    fun loadMore() {
        if (mIsRefreshing) {
            return
        }
        if (mIsLoadingMore) {
            return
        }
        mIsLoadingMore = true
        Thread{
            val requester = Requester()
            val yesterdayList = requester.request(TimeUtil.getDayStr(mNewsLastTime - TimeUtil.DAY))
            mList.addAll(yesterdayList)
            val todayList = requester.request(TimeUtil.getDayStr(mNewsLastTime))
            mList.addAll(todayList)
            mNewsLastTime -= 2 * TimeUtil.DAY
            Handler(Looper.getMainLooper()).post {
                if (mIsRefreshing
                        || !mIsLoadingMore) {
                    return@post
                }
                activity.onLoadMoreFinish(mList)
                mIsLoadingMore = false

            }
        }.start()
    }
}