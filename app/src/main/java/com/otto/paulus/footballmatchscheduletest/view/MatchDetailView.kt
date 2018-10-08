package com.otto.paulus.footballmatchscheduletest.view

import com.otto.paulus.footballmatchscheduletest.model.EventDetail
import com.otto.paulus.footballmatchscheduletest.model.Team

interface MatchDetailView {
    fun showLoading()
    fun hideLoading()
    fun showDetailEvent(data: EventDetail)
    fun showDetailTeam(data: Team, isHomeTeam: Boolean)
}