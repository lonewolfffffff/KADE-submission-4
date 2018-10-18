package com.otto.paulus.footballmatchscheduletest.presenter

import com.google.gson.Gson
import com.otto.paulus.footballmatchscheduletest.api.ApiRepository
import com.otto.paulus.footballmatchscheduletest.api.TheSportDBApi
import com.otto.paulus.footballmatchscheduletest.model.EventDetailResponse
import com.otto.paulus.footballmatchscheduletest.model.TeamResponse
import com.otto.paulus.footballmatchscheduletest.util.CoroutineContextProvider
import com.otto.paulus.footballmatchscheduletest.view.MatchDetailView
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchDetailPresenter(private val view: MatchDetailView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider()):AnkoLogger {
    fun getEventDetail(eventId: String) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getEventDetail(eventId)),
                        EventDetailResponse::class.java
                )
            }

            view.showDetailEvent(data.await().events.get(0))
            view.hideLoading()
        }
    }

    fun getTeamDetail(teamId: String, isHomeTeam: Boolean=true) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getTeamDetail(teamId)),
                        TeamResponse::class.java
                )
            }

            view.showDetailTeam(data.await().teams.get(0), isHomeTeam)
            view.hideLoading()
        }
    }
}