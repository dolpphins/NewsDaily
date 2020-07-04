package com.mtan.newsdaily

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.RotateAnimation
import android.widget.AbsListView
import android.widget.AbsListView.OnScrollListener
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.ProgressBar
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mtan.newsdaily.activity.WebViewActivity
import com.mtan.newsdaily.bean.News
import com.mtan.newsdaily.core.Requester
import com.mtan.newsdaily.presenter.MainPresenter
import com.mtan.newsdaily.ui.NewsListAdapter

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener{

    private lateinit var mRefreshView: SwipeRefreshLayout
    private lateinit var mListView: ListView
    private lateinit var mListAdapter: NewsListAdapter
    private lateinit var mLoadView: View
    private lateinit var mProgressView: ProgressBar

    private var mPresenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRefreshView = findViewById(R.id.refresh_view)
        mRefreshView.setOnRefreshListener(this)
        mListView = findViewById(R.id.list_view)

        mLoadView = LayoutInflater.from(this).inflate(R.layout.layout_news_list_loading, null)
        mProgressView = mLoadView.findViewById(R.id.progress_view)
        mListView.addFooterView(mLoadView)

        mListView.setOnScrollListener(object: OnScrollListener {

            override fun onScroll(view: AbsListView, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {

            }

            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
                if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
                    if (view.lastVisiblePosition == view.count - 1 && view.count > 0) {
                        mPresenter.loadMore()
                    }
                }
            }
        })


        mPresenter.loadFirst()
    }

    fun onLoadFirstFinish(list: List<News>) {
        mListAdapter = NewsListAdapter(applicationContext, list)
        mListView.adapter = mListAdapter
        mListView.onItemClickListener = OnItemClickListener {
                adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->

            var url = list[i].url
            var intent = Intent(MainActivity@this, WebViewActivity::class.java)
            intent.putExtra("url", url)
            startActivity(intent)
        }
        mRefreshView.isRefreshing = false
    }

    fun onLoadMoreFinish(list: List<News>) {
        mListAdapter.setData(list)
    }

    override fun onRefresh() {
        mPresenter.loadFirst()
    }
}
