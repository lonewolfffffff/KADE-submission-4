package com.otto.paulus.footballmatchscheduletest.activity

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.otto.paulus.footballmatchscheduletest.R
import com.otto.paulus.footballmatchscheduletest.R.string.label_added_to_favorite
import com.otto.paulus.footballmatchscheduletest.R.string.label_removed_from_favorite
import com.otto.paulus.footballmatchscheduletest.api.ApiRepository
import com.otto.paulus.footballmatchscheduletest.db.FavoriteMatch
import com.otto.paulus.footballmatchscheduletest.db.database
import com.otto.paulus.footballmatchscheduletest.model.EventDetail
import com.otto.paulus.footballmatchscheduletest.model.Team
import com.otto.paulus.footballmatchscheduletest.presenter.MatchDetailPresenter
import com.otto.paulus.footballmatchscheduletest.util.formatDate
import com.otto.paulus.footballmatchscheduletest.util.invisible
import com.otto.paulus.footballmatchscheduletest.util.parse
import com.otto.paulus.footballmatchscheduletest.util.visible
import com.otto.paulus.footballmatchscheduletest.view.MatchDetailView
import com.squareup.picasso.Picasso
import org.jetbrains.anko.AnkoLogger
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.select
import org.jetbrains.anko.info

class DetailActivity: AppCompatActivity(), MatchDetailView, AppBarLayout.OnOffsetChangedListener, AnkoLogger {
    private val presenter: MatchDetailPresenter = MatchDetailPresenter(this, ApiRepository(), Gson())
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    private lateinit var match: EventDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

        val matchId = intent.getStringExtra(getString(R.string.intent_match_id))
        val homeTeamId = intent.getStringExtra(getString(R.string.intent_home_team_id))
        val awayTeamId = intent.getStringExtra(getString(R.string.intent_away_team_id))

        favoriteState(matchId)

        presenter.getEventDetail(matchId)

        presenter.getTeamDetail(homeTeamId)
        presenter.getTeamDetail(awayTeamId,false)

        appBar.addOnOffsetChangedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.add_to_favorite -> {
                if(this::match.isInitialized) {
                    if (isFavorite) removeFromFavorite() else addToFavorite()

                    isFavorite = !isFavorite
                    setFavorite()
                }

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        info("Offset: "+verticalOffset+" -> "+(verticalOffset+200)/-40f)
        animateToolbar(verticalOffset)
    }

    private fun animateToolbar(verticalOffset: Int) {
        when(verticalOffset) {
            // fade ins
            in -240..-200 -> {
                tvMatchDateToolbar.visible()
                tvMatchDateToolbar.animate().alpha((200 - verticalOffset)/40f)
                tvMatchDateToolbar.animate().scaleX((verticalOffset+200)/-40f)
                tvMatchDateToolbar.animate().scaleY((verticalOffset+200)/-40f)
                ivHomeBadgeToolbar.visible()
                ivHomeBadgeToolbar.animate().alpha((200 - verticalOffset)/40f)
                ivHomeBadgeToolbar.animate().scaleX((verticalOffset+200)/-40f)
                ivHomeBadgeToolbar.animate().scaleY((verticalOffset+200)/-40f)
                tvScoreToolbar.visible()
                tvScoreToolbar.animate().alpha((200 - verticalOffset)/40f)
                tvScoreToolbar.animate().scaleX((verticalOffset+200)/-40f)
                tvScoreToolbar.animate().scaleY((verticalOffset+200)/-40f)
                ivAwayBadgeToolbar.visible()
                ivAwayBadgeToolbar.animate().alpha((200 - verticalOffset)/40f)
                ivAwayBadgeToolbar.animate().scaleX((verticalOffset+200)/-40f)
                ivAwayBadgeToolbar.animate().scaleY((verticalOffset+200)/-40f)
            }
            // disappear
            in -119..0 -> {
                tvMatchDateToolbar.alpha = 0f
                tvMatchDateToolbar.invisible()
                ivHomeBadgeToolbar.alpha = 0f
                ivHomeBadgeToolbar.invisible()
                tvScoreToolbar.alpha = 0f
                tvScoreToolbar.invisible()
                ivAwayBadgeToolbar.alpha = 0f
                ivAwayBadgeToolbar.invisible()
            }
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showDetailEvent(match: EventDetail) {
        this.match = match

        tvMatchDate.text = match.eventDate?.formatDate()
        tvMatchDateToolbar.text = match.eventDate?.formatDate()

        tvHomeTeamName.text = match.homeTeam

        if (match.homeScore === null) {
            val score_separator = getString(R.string.upcoming_match_score_separator)
            tvScore.text = score_separator
            tvScoreToolbar.text = " $score_separator "
        }
        else {
            val score_separator = getString(R.string.match_score_separator)
            tvScore.text = match.homeScore + " $score_separator " + match.awayScore
            tvScoreToolbar.text = match.homeScore + " $score_separator " + match.awayScore
        }

        tvAwayTeamName.text = match.awayTeam

        tvHomeFormation.text = match.homeFormation
        tvAwayFormation.text = match.awayFormation

        tvHomeGoals.text = match.homeGoalDetails?.parse()
        tvAwayGoals.text = match.awayGoalDetails?.parse()

        tvHomeShots.text = match.homeShots
        tvAwayShots.text = match.awayShots

        tvHomeGoalKeeper.text = match.homeGoalKeeper
        tvAwayGoalKeeper.text = match.awayGoalKeeper

        tvHomeDefenders.text = match.homeDefense?.parse()
        tvAwayDefenders.text = match.awayDefense?.parse()

        tvHomeMidfielders.text = match.homeMidfield?.parse()
        tvAwayMidfielders.text = match.awayMidfield?.parse()

        tvHomeForwards.text = match.homeForward?.parse()
        tvAwayForwards.text = match.awayForward?.parse()

        tvHomeSubstitutes.text = match.homeSubstitutes?.parse()
        tvAwaySubstitutes.text = match.awaySubstitutes?.parse()
    }

    override fun showDetailTeam(data: Team, isHomeTeam: Boolean) {
        Picasso.get().load(data.teamBadge).into(if(isHomeTeam) ivHomeBadge else ivAwayBadge)
        Picasso.get().load(data.teamBadge).into(if(isHomeTeam) ivHomeBadgeToolbar else ivAwayBadgeToolbar)
    }

    private fun favoriteState(matchId:String){
        database.use {
            val result = select(FavoriteMatch.TABLE_FAVORITE_MATCH)
                    .whereArgs("(MATCH_ID = {id})",
                            "id" to matchId)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            if (!favorite.isEmpty()) isFavorite = true

            setFavorite()
        }
    }

    private fun addToFavorite(){

        try {
            database.use {
                insert(FavoriteMatch.TABLE_FAVORITE_MATCH,
                        FavoriteMatch.MATCH_ID to match.eventId,
                        FavoriteMatch.MATCH_DATE to match.eventDate,
                        FavoriteMatch.HOME_TEAM_ID to match.homeTeamId,
                        FavoriteMatch.HOME_TEAM_NAME to match.homeTeam,
                        FavoriteMatch.HOME_SCORE to match.homeScore,
                        FavoriteMatch.AWAY_TEAM_ID to match.awayTeamId,
                        FavoriteMatch.AWAY_TEAM_NAME to match.awayTeam,
                        FavoriteMatch.AWAY_SCORE to match.awayScore
                )
            }

            Snackbar.make(relativeLayout,label_added_to_favorite, Snackbar.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException){
            Snackbar.make(relativeLayout,e.localizedMessage, Snackbar.LENGTH_SHORT).show()
        }

    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(FavoriteMatch.TABLE_FAVORITE_MATCH, "(MATCH_ID = {id})","id" to match.eventId.toString())
            }
            Snackbar.make(relativeLayout, label_removed_from_favorite,Snackbar.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException){
            Snackbar.make(relativeLayout,e.localizedMessage, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border)
    }
}