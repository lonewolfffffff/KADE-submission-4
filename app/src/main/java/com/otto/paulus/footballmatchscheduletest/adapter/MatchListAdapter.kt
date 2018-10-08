package com.otto.paulus.footballmatchscheduletest.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.otto.paulus.footballmatchscheduletest.layout.MatchListItemUI
import com.otto.paulus.footballmatchscheduletest.model.Event
import com.otto.paulus.footballmatchscheduletest.util.bold
import com.otto.paulus.footballmatchscheduletest.util.formatDate
import com.otto.paulus.footballmatchscheduletest.util.normal
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.find

class MatchListAdapter(private val events: List<Event>, private val listener: (Event) -> Unit): RecyclerView.Adapter<MatchListAdapter.MatchViewHolder>(),AnkoLogger {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        return MatchViewHolder(MatchListItemUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bindItem(events[position], listener)
    }

    class MatchViewHolder(view: View) : RecyclerView.ViewHolder(view), AnkoLogger {
        private val eventDate: TextView = view.find(MatchListItemUI.eventDate)
        private val homeTeamName: TextView = view.find(MatchListItemUI.homeTeamName)
        private val homeTeamScore: TextView = view.find(MatchListItemUI.homeTeamScore)
        private val awayTeamScore: TextView = view.find(MatchListItemUI.awayTeamScore)
        private val awayTeamName: TextView = view.find(MatchListItemUI.awayTeamName)

        fun bindItem(events: Event, listener: (Event) -> Unit) {

            eventDate.text = events.eventDate?.formatDate()

            homeTeamName.text = events.homeTeam
            homeTeamScore.text = events.homeScore
            awayTeamScore.text = events.awayScore
            awayTeamName.text = events.awayTeam

            val homeScore = events.homeScore?.toInt()?:0
            val awayScore = events.awayScore?.toInt()?:0

            if(homeScore-awayScore>0) {
                homeTeamName.bold()
                awayTeamName.normal()
            }
            else {
                if(homeScore-awayScore<0) {
                    homeTeamName.normal()
                    awayTeamName.bold()
                }
                else {
                    homeTeamName.normal()
                    awayTeamName.normal()
                }
            }

            itemView.setOnClickListener {
                listener(events)
            }

        }
    }
}

