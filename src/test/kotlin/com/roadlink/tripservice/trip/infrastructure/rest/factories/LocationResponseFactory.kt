package com.roadlink.tripservice.trip.infrastructure.rest.factories

import com.roadlink.tripservice.trip.infrastructure.rest.responses.LocationExpectedResponse

object LocationResponseFactory {
    fun avCabildo_4853() =
        LocationExpectedResponse(
            latitude = -34.540412,
            longitude = -58.474732,
        )

    fun avCabildo_20() =
        LocationExpectedResponse(
            latitude = -34.574810,
            longitude = -58.435990,
        )

    fun virreyDelPino1800() =
        LocationExpectedResponse(
            latitude = -34.562389,
            longitude = -58.445302,
        )
}