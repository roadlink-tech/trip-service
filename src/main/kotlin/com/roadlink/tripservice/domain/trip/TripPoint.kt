package com.roadlink.tripservice.domain.trip

import com.roadlink.tripservice.domain.Location
import java.time.Instant

data class TripPoint(
    val location: Location,
    val at: Instant,
    // TODO address formatted or full address?
    val formatted: String,
    val street: String,
    val city: String,
    val country: String,
    val housenumber: String,
)
