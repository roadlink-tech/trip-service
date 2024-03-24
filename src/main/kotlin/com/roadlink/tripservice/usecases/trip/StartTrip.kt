package com.roadlink.tripservice.usecases.trip

import com.roadlink.tripservice.domain.trip_solicitude.TripPlanSolicitude
import com.roadlink.tripservice.domain.trip_solicitude.TripPlanSolicitude.Status.PENDING_APPROVAL
import com.roadlink.tripservice.domain.trip_solicitude.TripPlanSolicitudeRepository
import com.roadlink.tripservice.usecases.UseCase
import java.util.*

class StartTrip(
    private val tripPlanSolicitudeRepository: TripPlanSolicitudeRepository,
) : UseCase<StartTrip.Input, StartTrip.Output> {

    override fun invoke(input: Input): Output {
        val tripPlanSolicitudes = tripPlanSolicitudeRepository.find(
            commandQuery = TripPlanSolicitudeRepository.CommandQuery(
                tripId = input.tripId
            )
        ).filter { it.status() == PENDING_APPROVAL }

        tripPlanSolicitudes.forEach { tripPlanSolicitude ->
            tripPlanSolicitude.reject()
            tripPlanSolicitudeRepository.update(tripPlanSolicitude)
        }.also { return Output(tripPlanSolicitudes) }
    }

    class Input(
        var tripId: UUID
    )

    class Output(var rejectedTripPlanSolicitudes: List<TripPlanSolicitude>)
}