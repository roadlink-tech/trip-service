package com.roadlink.tripservice.infrastructure.rest.driver_trip.handler

import com.roadlink.tripservice.infrastructure.rest.driver_trip.response.DriverTripSummaryResponseFactory
import com.roadlink.tripservice.usecases.UseCase
import com.roadlink.tripservice.usecases.driver_trip.RetrieveDriverTripSummaryOutput
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable


@Controller("/trip-service")
class DriverTripSummaryHandler(
    private val retrieveDriverTripSummary: UseCase<String, RetrieveDriverTripSummaryOutput>,
    private val responseFactory: DriverTripSummaryResponseFactory
) {

    @Get("/driver/{driverId}/trip-summary")
    fun retrieve(@PathVariable driverId: String): HttpResponse<Any> {
        val output = retrieveDriverTripSummary(driverId)
        return responseFactory.from(output)
    }

}