package com.roadlink.tripservice.usecases.driver_trip

import com.roadlink.tripservice.domain.*
import com.roadlink.tripservice.domain.driver_trip.DriverTripApplication
import com.roadlink.tripservice.domain.driver_trip.Passenger
import com.roadlink.tripservice.domain.driver_trip.PassengerNotExists
import com.roadlink.tripservice.domain.trip_solicitude.TripLegSolicitudeRepository
import com.roadlink.tripservice.domain.user.UserRepository
import java.util.UUID

class ListDriverTripApplications(
    private val tripLegSolicitudeRepository: TripLegSolicitudeRepository,
    private val userRepository: UserRepository,
    private val ratingRepository: RatingRepository,
) {
    operator fun invoke(input: Input): List<DriverTripApplication> {
        return tripLegSolicitudeRepository.find(TripLegSolicitudeRepository.CommandQuery(tripId = input.tripId))
            .filter { tripApplication ->
                tripApplication.isPendingApproval()
            }
            .map { tripApplication ->
                val passengerId = tripApplication.passengerId
                DriverTripApplication(
                    tripApplicationId = tripApplication.id,
                    passenger = userRepository.findFullNameById(passengerId)
                        ?.let { fullName ->
                            Passenger(
                                id = passengerId,
                                fullName = fullName,
                                rating = ratingRepository.findByUserId(passengerId)
                                    ?.let { rating ->
                                        Rated(rating)
                                    }
                                    ?: NotBeenRated,
                            )
                        }
                        ?: PassengerNotExists(id = passengerId),
                    applicationStatus = tripApplication.status,
                    addressJoinStart = tripApplication.departureTripPoint().address,
                    addressJoinEnd = tripApplication.arrivalTripPoint().address,
                )
            }
    }

    data class Input(val tripId: UUID)
}