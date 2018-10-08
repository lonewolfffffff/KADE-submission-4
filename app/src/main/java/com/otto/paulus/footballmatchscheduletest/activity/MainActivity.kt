package com.otto.paulus.footballmatchscheduletest.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.otto.paulus.footballmatchscheduletest.R
import com.otto.paulus.footballmatchscheduletest.activity.fragment.MatchListFragment
import com.otto.paulus.footballmatchscheduletest.model.Event
import com.otto.paulus.footballmatchscheduletest.util.*
import org.jetbrains.anko.AnkoLogger
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity(), MatchListFragment.OnFragmentInteractionListener, AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navMatch.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            replaceFragment(MatchListFragment.newInstance(item.itemId), framelayout.id)
            return@OnNavigationItemSelectedListener true
        })

        addFragment(MatchListFragment(), framelayout.id)
    }

    override fun onMatchListItemClick(match: Event) {
        startActivity<DetailActivity>(
                getString(R.string.intent_match_id) to match.eventId,
                getString(R.string.intent_home_team_id) to match.homeTeamId,
                getString(R.string.intent_away_team_id) to match.awayTeamId
        )

    }

}
