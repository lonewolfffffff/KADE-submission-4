package com.otto.paulus.footballmatchscheduletest.util

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import java.text.SimpleDateFormat

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun TextView.normal() {
    typeface = Typeface.DEFAULT
}

fun TextView.bold() {
    typeface = Typeface.DEFAULT_BOLD
}

fun String.formatDate(fromDateFormat:String="dd/MM/yy", toDateFormat:String = "E, dd MMM yyyy") : String {
    val date = SimpleDateFormat(fromDateFormat).parse(this)
    val dateFormatter = SimpleDateFormat(toDateFormat)
    return dateFormatter.format(date)
}

fun String.parse(delimiter: String = ";", replacement: String = System.getProperty("line.separator") ) : String {
    return this.replace(delimiter, replacement)
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { add(frameId, fragment) }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int, func: (FragmentTransaction.() -> FragmentTransaction)? = null) {
    if(func==null) {
        supportFragmentManager.inTransaction { replace(frameId, fragment) }
    }
    else {
        supportFragmentManager.inTransaction { replace(frameId, fragment).func() }
    }
}

