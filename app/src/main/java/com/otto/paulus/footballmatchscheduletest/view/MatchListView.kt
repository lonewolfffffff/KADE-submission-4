package com.otto.paulus.footballmatchscheduletest.view

import com.otto.paulus.footballmatchscheduletest.model.Event

interface MatchListView {
    fun showLoading()
    fun hideLoading()
    fun showEventList(data: List<Event>)
}