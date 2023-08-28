package com.roadlink.tripservice.domain

import java.util.*

data class DriverTripDetail(
    val tripId: UUID,
    val tripStatus: TripStatus,
    val seatStatus: SeatsAvailabilityStatus,
    val sectionDetails: List<DriverSectionDetail>
)