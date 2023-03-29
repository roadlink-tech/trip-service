package com.roadlink.tripservice.usecases

import com.roadlink.tripservice.domain.*
import com.roadlink.tripservice.domain.event.*
import com.roadlink.tripservice.domain.time.exception.InvalidTripTimeRangeException
import com.roadlink.tripservice.domain.time.TimeProvider
import com.roadlink.tripservice.domain.time.TimeRange
import com.roadlink.tripservice.domain.trip.Trip
import com.roadlink.tripservice.domain.trip.TripPoint
import com.roadlink.tripservice.domain.trip.TripRepository
import java.util.UUID

object DefaultIdGenerator : IdGenerator {
    override fun id(): String {
        return UUID.randomUUID().toString()
    }
}

class CreateTrip(
    private val tripRepository: TripRepository,
    private val idGenerator: IdGenerator,
    private val eventPublisher: EventPublisher,
    private val commandBus: CommandBus,
    private val timeProvider: TimeProvider,
) {
    operator fun invoke(input: Input): Trip {
        validateTripTimeRange(input)
        validateExistsTripByDriverAndInTimeRange(input)

        val trip = input.toTrip()
        return trip.also {
            tripRepository.save(it)
            //eventPublisher.publish(TripCreatedEvent(trip = trip, at = timeProvider.now()))
            commandBus.publish<TripCreatedCommandV2, TripCreatedCommandResponseV2>(
                TripCreatedCommandV2(
                    trip = trip,
                    at = timeProvider.now()
                )
            )
        }
    }

    private fun validateTripTimeRange(input: Input) {
        val tripPoints = listOf(input.departure) + input.meetingPoints + listOf(input.arrival)
        for ((actual, next) in tripPoints.windowed(2, 1) { Pair(it[0], it[1]) }) {
            if (next.at.isBefore(actual.at))
                throw InvalidTripTimeRangeException()
        }
    }

    private fun validateExistsTripByDriverAndInTimeRange(input: Input) {
        val driver = input.driver
        val timeRange = TimeRange(from = input.departure.at, to = input.arrival.at)
        if (tripRepository.existsByDriverAndInTimeRange(driver, timeRange))
            throw AlreadyExistsTripByDriverInTimeRange(driver, timeRange)
    }

    private fun Input.toTrip(): Trip =
        Trip(
            id = idGenerator.id(),
            driver = driver,
            vehicle = vehicle,
            departure = departure,
            arrival = arrival,
            meetingPoints = meetingPoints,
            availableSeats = availableSeats,
        )

    data class Input(
        val driver: String,
        val vehicle: String,
        val departure: TripPoint,
        val arrival: TripPoint,
        val meetingPoints: List<TripPoint>,
        val availableSeats: Int,
    )
}