package com.roadlink.tripservice.domain

import java.time.Instant

data class TripPoint(
    val location: Location,
    val at: Instant,
    val formatted: String,
    val street: String,
    val city: String,
    val country: String,
    val housenumber: String,
)
