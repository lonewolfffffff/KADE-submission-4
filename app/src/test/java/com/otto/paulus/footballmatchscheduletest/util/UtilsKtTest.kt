package com.otto.paulus.footballmatchscheduletest.util

import org.junit.Test

import org.junit.Assert.*

class UtilsKtTest {

    @Test
    fun testFormatDate() {
        val dateString = "27/10/18"
        assertEquals("Sat, 27 Oct 2018",dateString.formatDate())
    }

    @Test
    fun testParse() {
        val strTeam = "Wilfredo Caballero; Michy Batshuayi; Antonio Ruediger; Pedro Rodriguez; Davide Zappacosta; Danny Drinkwater; Willian;"
        val strTeamParsed = "Wilfredo Caballerox Michy Batshuayix Antonio Ruedigerx Pedro Rodriguezx Davide Zappacostax Danny Drinkwaterx Willianx"
        assertEquals(strTeamParsed,strTeam.parse(replacement = "x"))
    }
}