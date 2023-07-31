package com.roadlink.tripservice.infrastructure.rest.trip_application

import com.roadlink.tripservice.infrastructure.rest.mappers.DriverTripApplicationResponseMapper
import com.roadlink.tripservice.infrastructure.rest.trip_application.response.DriverTripApplicationResponse
import com.roadlink.tripservice.usecases.trip_plan.GetDriverTripApplications
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import java.util.*
import kotlin.collections.List

@Controller("/trip-service")
class GetDriverTripApplicationsController(
    private val getDriverTripApplications: GetDriverTripApplications,
) {
    @Get("/driver-trip-applications")
    fun handle(@QueryValue tripId: String): HttpResponse<List<DriverTripApplicationResponse>> {
        val driverTripApplications = getDriverTripApplications(GetDriverTripApplications.Input(
            tripId = UUID.fromString(tripId)
        ))

        return HttpResponse.ok(driverTripApplications.map {
            DriverTripApplicationResponseMapper.map(it)
        })
    }
}