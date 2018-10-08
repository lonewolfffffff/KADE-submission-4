package com.otto.paulus.footballmatchscheduletest.layout

import android.support.design.widget.BottomNavigationView
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import com.otto.paulus.footballmatchscheduletest.R
import com.otto.paulus.footballmatchscheduletest.activity.fragment.MatchListFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.design.bottomNavigationView
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class MatchListUI:AnkoComponent<MatchListFragment> {
    lateinit var progressBar: ProgressBar
    lateinit var swipeRefresh: SwipeRefreshLayout
    lateinit var eventsList: RecyclerView
    lateinit var bottomNavigation: BottomNavigationView

    override fun createView(ui: AnkoContext<MatchListFragment>): View = with(ui) {
        relativeLayout {
            lparams (width = matchParent, height = matchParent)
            topPadding = dip(16)
            leftPadding = dip(3)
            rightPadding = dip(3)
            hasNestedScrollingParent()
            //spinner = spinner ()

            bottomNavigation = bottomNavigationView {
                id = View.generateViewId()
                inflateMenu(R.menu.navigation)
            }.lparams{
                width = matchParent
                height = wrapContent
                alignParentBottom()
            }

            progressBar = progressBar {
                id = View.generateViewId()
            }.lparams {
                centerInParent()
            }

            swipeRefresh = swipeRefreshLayout {
                id = View.generateViewId()
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                eventsList = recyclerView {
                    id = View.generateViewId()
                    lparams (width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                    addItemDecoration(DividerItemDecoration(ctx, DividerItemDecoration.VERTICAL))

                }

            }.lparams {
                above(bottomNavigation)
            }

        }
    }
}