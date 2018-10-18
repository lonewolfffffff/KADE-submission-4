package com.otto.paulus.footballmatchscheduletest

import android.util.Log
import java.io.File
import java.nio.file.Files

fun loadJSON(clazz: Class<Any>, path: String): String {
    val jsonFile = clazz.classLoader!!.getResourceAsStream(path)
    return String(jsonFile.readBytes())
}