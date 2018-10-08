package com.otto.paulus.footballmatchscheduletest.model

import com.google.gson.annotations.SerializedName

data class Event (
    @SerializedName("idEvent")
    var eventId: String? = null,

    @SerializedName("strDate")
    var eventDate: String? = null,

    // Home
    @SerializedName("strHomeTeam")
    var homeTeam: String? = null,

    @SerializedName("idHomeTeam")
    var homeTeamId: String? = null,

    @SerializedName("intHomeScore")
    var homeScore: String? = null,

    // Away
    @SerializedName("strAwayTeam")
    var awayTeam: String? = null,

    @SerializedName("idAwayTeam")
    var awayTeamId: String? = null,

    @SerializedName("intAwayScore")
    var awayScore: String? = null
)
