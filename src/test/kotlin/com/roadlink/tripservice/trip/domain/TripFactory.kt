package com.roadlink.tripservice.trip.domain

object TripFactory {

    const val avCabildoId = "TripFactory_avCabildo"
    const val avCabildo4853_virreyDelPino1800_avCabildo20 = "TripFactory_avCabildo4853_virreyDelPino1800_avCabildo20"

    fun avCabildo() =
        Trip(
            id = avCabildoId,
            driver = "John Smith",
            vehicle = "Ford mustang",
            departure = TripPointFactory.avCabildo_4853(),
            arrival = TripPointFactory.avCabildo_20(),
            meetingPoints = emptyList(),
            availableSeats = 4,
        )

    fun avCabildo4853_virreyDelPino1800_avCabildo20() =
        Trip(
            id = avCabildo4853_virreyDelPino1800_avCabildo20,
            driver = "John Smith",
            vehicle = "Ford mustang",
            departure = TripPointFactory.avCabildo_4853(),
            arrival = TripPointFactory.avCabildo_20(),
            meetingPoints = listOf(TripPointFactory.virreyDelPino_1800()),
            availableSeats = 5,
        )
}