package com.otto.paulus.footballmatchscheduletest

import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.MockResponse
import org.junit.After
import org.junit.Before
import org.junit.Test

@RunWith(JUnit4::class)
class MatchListActivityTest {
    lateinit var mockServer : MockWebServer

    @Before
    @Throws fun setUp() {
        mockServer = MockWebServer()
        mockServer.start()
    }

    @Test
    fun shouldShowMatchList() {

        val mockResponse = MockResponse()
                .setResponseCode(200)
                .setBody(loadJSON(this.javaClass , "json/eventresponse_last15events.json"))

        mockServer.enqueue(mockResponse)



    }

    @After
    @Throws fun tearDown() {
        mockServer.shutdown()
    }
}