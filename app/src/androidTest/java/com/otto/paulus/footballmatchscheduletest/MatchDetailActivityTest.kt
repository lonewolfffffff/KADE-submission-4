package com.otto.paulus.footballmatchscheduletest

import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.isChecked
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.RecyclerView
import com.otto.paulus.footballmatchscheduletest.activity.MainActivity
import com.otto.paulus.footballmatchscheduletest.api.ApiRepository
import com.otto.paulus.footballmatchscheduletest.db.FavoriteMatch
import com.otto.paulus.footballmatchscheduletest.db.database
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Matchers.not
import org.jetbrains.anko.db.delete
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MatchDetailActivityTest {
    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Rule
    @JvmField
    val intentsTestRule = IntentsTestRule(MainActivity::class.java)

    lateinit var mockServer: MockWebServer
    val db = InstrumentationRegistry.getTargetContext().database

    @Before
    @Throws
    fun setUp() {
        mockServer = MockWebServer()
        mockServer.start()

        try {
            db.use {
                delete(FavoriteMatch.TABLE_FAVORITE_MATCH, "(MATCH_ID = {id})", "id" to "576537")
            }
        } catch (e:SQLiteConstraintException) {

        }

        ApiRepository.IS_MOCKED = true
        ApiRepository.MOCKED_SERVER_URL = mockServer.url("/").toString()

    }

    @Test
    fun setAsFavorite() {

        val mockMatchListResponse = MockResponse()
                .setResponseCode(200)
                .setBody(loadJSON(this.javaClass, "json/eventresponse_last15events.json"))

        mockServer.enqueue(mockMatchListResponse)

        val mockMatchDetailResponse = MockResponse()
                .setResponseCode(200)
                .setBody(loadJSON(this.javaClass, "json/eventdetailresponse_past_576537.json"))

        mockServer.enqueue(mockMatchDetailResponse)

        val intent = Intent()
        activityTestRule.launchActivity(intent)

        onView(withId(R.id.rvMatchList)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(13, click()))
        onView(withId(R.id.add_to_favorite)).perform(click()).check(matches(not(isChecked())))


    }

    @Test
    fun removeFromFavorites() {

        val mockMatchListResponse = MockResponse()
                .setResponseCode(200)
                .setBody(loadJSON(this.javaClass, "json/eventresponse_last15events.json"))

        mockServer.enqueue(mockMatchListResponse)

        val mockMatchDetailResponse = MockResponse()
                .setResponseCode(200)
                .setBody(loadJSON(this.javaClass, "json/eventdetailresponse_past_576537.json"))

        mockServer.enqueue(mockMatchDetailResponse)

        val intent = Intent()
        activityTestRule.launchActivity(intent)

        onView(withId(R.id.rvMatchList)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(13, click()))

        onView(withId(R.id.add_to_favorite)).perform(click()).check(matches(not(isChecked())))
        onView(withId(R.id.add_to_favorite)).perform(click()).check(matches(not(isChecked())))


    }

    @After
    @Throws
    fun tearDown() {
        mockServer.shutdown()
    }
}