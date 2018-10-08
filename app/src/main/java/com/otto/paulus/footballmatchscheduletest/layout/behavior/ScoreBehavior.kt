package com.otto.paulus.footballmatchscheduletest.layout.behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.View
import android.widget.TextView

import org.jetbrains.anko.AnkoLogger

class ScoreBehavior(context: Context, attrs: AttributeSet) : CoordinatorLayout.Behavior<TextView>(context, attrs),AnkoLogger {
    override fun layoutDependsOn(parent: CoordinatorLayout, child: TextView, dependency: View): Boolean {
        return dependency is Toolbar
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: TextView, dependency: View): Boolean {
        if(dependency.y in -240..-200) {
            child.animate().alpha((dependency.y + 200)/-40f)
        }
        else {
            child.animate().alpha(1f)
        }
        return false
    }

}