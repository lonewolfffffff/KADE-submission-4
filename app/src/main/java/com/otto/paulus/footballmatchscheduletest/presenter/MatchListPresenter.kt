package com.otto.paulus.footballmatchscheduletest.presenter

import com.google.gson.Gson
import com.otto.paulus.footballmatchscheduletest.api.ApiRepository
import com.otto.paulus.footballmatchscheduletest.api.TheSportDBApi
import com.otto.paulus.footballmatchscheduletest.model.EventResponse
import com.otto.paulus.footballmatchscheduletest.util.CoroutineContextProvider
import com.otto.paulus.footballmatchscheduletest.view.MatchListView
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchListPresenter(private val view: MatchListView,
                         private val apiRepository: ApiRepository,
                         private val gson: Gson,
                         private val context: CoroutineContextProvider = CoroutineContextProvider()):AnkoLogger {
    fun getLast15EventsList(leagueId: Int?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getLast15Events(leagueId)),
                        EventResponse::class.java
                )
            }

            view.showEventList(data.await().events)
            view.hideLoading()
        }
    }

    fun getNext15EventsList(leagueId: Int?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getNext15Events(leagueId)),
                        EventResponse::class.java
                )
            }

            view.showEventList(data.await().events)
            view.hideLoading()
        }
    }
}