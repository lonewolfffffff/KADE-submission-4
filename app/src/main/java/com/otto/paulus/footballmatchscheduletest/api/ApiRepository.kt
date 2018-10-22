package com.otto.paulus.footballmatchscheduletest.api

import java.net.URL

class ApiRepository {
    fun doRequest(url: String): String {
        return URL(if(IS_MOCKED) MOCKED_SERVER_URL else url).readText()
    }

    companion object {
        var IS_MOCKED = false
        var MOCKED_SERVER_URL = ""
    }
}