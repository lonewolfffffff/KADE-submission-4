package com.otto.paulus.footballmatchscheduletest.layout

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import org.jetbrains.anko.*

class TeamUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            padding = dip(16)
            orientation = LinearLayout.HORIZONTAL

            imageView {
                id = Id.badge
            }.lparams{
                height = dip(50)
                width = dip(50)
            }

            textView {
                id = Id.name
                textSize = 16f
            }.lparams{
                margin = dip(15)
            }

        }
    }

    companion object Id {
        val badge = 1
        val name = 2
    }
}