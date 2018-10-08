package com.otto.paulus.footballmatchscheduletest.layout

import android.view.View
import com.otto.paulus.footballmatchscheduletest.activity.fragment.MatchDetailFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout

class MatchDetailUI:AnkoComponent<MatchDetailFragment> {
    override fun createView(ui: AnkoContext<MatchDetailFragment>): View = with(ui) {
        coordinatorLayout {
            appBarLayout {
                lparams(width = matchParent, height = wrapContent)
                toolbar {
                    lparams(width = matchParent, height = wrapContent)

                }
            }
        }
    }

}