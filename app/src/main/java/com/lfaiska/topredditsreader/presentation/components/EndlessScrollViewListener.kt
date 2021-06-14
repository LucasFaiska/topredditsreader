package com.lfaiska.topredditsreader.presentation.components

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class EndlessScrollViewListener : RecyclerView.OnScrollListener() {

    private val visibleThreshold = 5
    private var currentPage = 0
    private var previousTotalItemCount = 0
    private var loading = true
    private val startingPageIndex = 0
    private var totalItemCount = 0
    private var lastVisibleItemPosition = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager

        totalItemCount = linearLayoutManager.itemCount
        lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()

        if (totalItemCount < previousTotalItemCount) {
            currentPage = startingPageIndex
            previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                loading = true
            }
        }

        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            currentPage++
            onScrollToTheEnd()
            loading = true
        }
    }

    open fun reset() {
        currentPage = startingPageIndex
        previousTotalItemCount = 0
        loading = true
    }

    abstract fun onScrollToTheEnd()
}