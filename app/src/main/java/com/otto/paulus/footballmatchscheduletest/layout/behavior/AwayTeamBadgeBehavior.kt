package com.otto.paulus.footballmatchscheduletest.layout.behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class AwayTeamBadgeBehavior(context: Context, attrs: AttributeSet) : CoordinatorLayout.Behavior<ImageView>(context, attrs),AnkoLogger {
    override fun layoutDependsOn(parent: CoordinatorLayout, child: ImageView, dependency: View): Boolean {
        return dependency is Toolbar
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: ImageView, dependency: View): Boolean {
        //TODO : sama seperti HomeTeam, lebih bagus jika menggunakan exponential formula
        child.alpha = dependency.y/250
        info(dependency.x)
        return false
    }

}