package com.otto.paulus.footballmatchscheduletest.layout

import android.support.design.widget.AppBarLayout
import android.view.View
import android.widget.FrameLayout
import com.otto.paulus.footballmatchscheduletest.activity.MainActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout


class MainActivityUI : AnkoComponent<MainActivity> {

    lateinit var frameLayout: FrameLayout

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        coordinatorLayout {

            val appBarLayout = appBarLayout {
                id = View.generateViewId()
                lparams(width = matchParent, height = wrapContent)
                toolbar {
                    lparams(width = matchParent, height = wrapContent) {

                    }

                }
            }
            linearLayout {

            }.lparams {
                behavior = AppBarLayout.ScrollingViewBehavior()
            }

            frameLayout = frameLayout{
                id = View.generateViewId()
            }.lparams {
                width = matchParent

            }

        }
    }
}

