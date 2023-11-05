package com.roadlink.tripservice.trip.domain

import com.roadlink.tripservice.domain.trip.TripPoint
import com.roadlink.tripservice.domain.trip.section.Section
import java.util.*

object SectionFactory {

    const val avCabildo_id = "SectionFactory-avCabildo"
    const val virreyDelPino_id = "SectionFactory-virreyDelPino"
    const val avCabildo4853_virreyDelPino1800_id = "SectionFactory-avCabildo4853_virreyDelPino1800"
    const val virreyDelPino1800_avCabildo20_id = "SectionFactory-virreyDelPino1800_avCabildo20"
    const val avCabildo4853_virreyDelPino2880_id = "SectionFactory-avCabildo4853_virreyDelPino2880"
    const val virreyDelPino2880_avCabildo20_id = "SectionFactory-virreyDelPino2880_avCabildo20"
    const val virreyDelPino2880_avCabildo1621_id = "SectionFactory-virreyDelPino2880_avCabildo1621"
    const val avCabildo1621_virreyDelPino1800_id = "SectionFactory-avCabildo1621_virreyDelPino1800"
    const val virreyDelPino1800_avDelLibertador5000_id = "SectionFactory-virreyDelPino1800_avDelLibertador5000"
    const val avDelLibertador5000_avCabildo20_id = "SectionFactory-avDelLibertador5000_avCabildo20"

    fun avCabildo(
        departure: TripPoint = TripPointFactory.avCabildo_4853(),
        arrival: TripPoint = TripPointFactory.avCabildo_20(),
        initialAmountOfSeats: Int = 4,
        bookedSeats: Int = 0,
        tripId: UUID = UUID.fromString("ad2e7d11-0fbb-4711-8f0c-a0f31529241f"),
        driverId: String = "John Smith"
    ) =
        Section(
            id = avCabildo_id,
            //tripId = TripFactory.avCabildo_id,
            departure = departure,
            arrival = arrival,
            distanceInMeters = 6070.0,
            driver = driverId,
            vehicle = "Ford mustang",
            initialAmountOfSeats = initialAmountOfSeats,
            bookedSeats = bookedSeats,
            tripId = tripId
        )

    fun virreyDelPino(tripId: UUID = UUID.fromString("ad2e7d11-0fbb-4711-8f0c-a0f31529241f")) =
        Section(
            id = virreyDelPino_id,
            departure = TripPointFactory.virreyDelPino_2880(),
            arrival = TripPointFactory.virreyDelPino_1800(),
            distanceInMeters = 1300.0,
            driver = "John Smith",
            vehicle = "Ford mustang",
            initialAmountOfSeats = 4,
            bookedSeats = 0,
            tripId = tripId
        )

    fun avCabildo4853_virreyDelPino1800(
        availableSeats: Int = 4,
        tripId: UUID = UUID.fromString("bd7ee293-f5d3-4832-a74c-17e9a8fa465f")
    ) =
        Section(
            id = avCabildo4853_virreyDelPino1800_id,
            //tripId = TripFactory.avCabildo4853_virreyDelPino1800_avCabildo20_id,
            departure = TripPointFactory.avCabildo_4853(),
            arrival = TripPointFactory.virreyDelPino_1800(),
            distanceInMeters = 4000.0,
            driver = "John Smith",
            vehicle = "Ford mustang",
            initialAmountOfSeats = availableSeats,
            bookedSeats = 0,
            tripId = tripId
        )

    fun virreyDelPino1800_avCabildo20(
        tripId: UUID = UUID.fromString("bd7ee293-f5d3-4832-a74c-17e9a8fa465f")
    ) =
        Section(
            id = virreyDelPino1800_avCabildo20_id,
            //tripId = TripFactory.avCabildo4853_virreyDelPino1800_avCabildo20_id,
            departure = TripPointFactory.virreyDelPino_1800(),
            arrival = TripPointFactory.avCabildo_20(),
            distanceInMeters = 3000.0,
            driver = "John Smith",
            vehicle = "Ford mustang",
            initialAmountOfSeats = 4,
            bookedSeats = 0,
            tripId = tripId
        )

    fun avCabildo4853_virreyDelPino2880(
        tripId: UUID = UUID.fromString("bd7ee293-f5d3-4832-a74c-17e9a8fa465f")
    ) =
        Section(
            id = avCabildo4853_virreyDelPino2880_id,
            departure = TripPointFactory.avCabildo_4853(),
            arrival = TripPointFactory.virreyDelPino_2880(),
            distanceInMeters = 5000.0,
            driver = "John Smith",
            vehicle = "Ford mustang",
            initialAmountOfSeats = 4,
            bookedSeats = 0,
            tripId = tripId
        )

    fun virreyDelPino2880_avCabildo20(
        tripId: UUID = UUID.fromString("bd7ee293-f5d3-4832-a74c-17e9a8fa465f")
    ) =
        Section(
            id = virreyDelPino2880_avCabildo20_id,
            departure = TripPointFactory.virreyDelPino_2880(),
            arrival = TripPointFactory.avCabildo_20(),
            distanceInMeters = 3000.0,
            driver = "John Smith",
            vehicle = "Ford mustang",
            initialAmountOfSeats = 4,
            bookedSeats = 0,
            tripId = tripId
        )

    fun virreyDelPino2880_avCabildo1621() =
        Section(
            id = virreyDelPino2880_avCabildo1621_id,
            departure = TripPointFactory.virreyDelPino_2880(),
            arrival = TripPointFactory.avCabildo_1621(),
            distanceInMeters = 900.0,
            driver = "John Smith",
            vehicle = "Ford mustang",
            initialAmountOfSeats = 4,
            bookedSeats = 0,
            tripId = UUID.randomUUID()
        )

    fun avCabildo1621_virreyDelPino1800() =
        Section(
            id = avCabildo1621_virreyDelPino1800_id,
            departure = TripPointFactory.avCabildo_1621(),
            arrival = TripPointFactory.virreyDelPino_1800(),
            distanceInMeters = 400.0,
            driver = "John Smith",
            vehicle = "Ford mustang",
            initialAmountOfSeats = 4,
            bookedSeats = 0,
            tripId = UUID.randomUUID()
        )

    fun avCabildo1621_virreyDelPino1800_completed() =
        Section(
            id = avCabildo1621_virreyDelPino1800_id,
            departure = TripPointFactory.avCabildo_1621(),
            arrival = TripPointFactory.virreyDelPino_1800(),
            distanceInMeters = 400.0,
            driver = "John Smith",
            vehicle = "Ford mustang",
            initialAmountOfSeats = 0,
            bookedSeats = 0,
            tripId = UUID.randomUUID()
        )

    fun virreyDelPino1800_avDelLibertador5000(
        tripId: UUID = UUID.fromString("bd7ee293-f5d3-4832-a74c-17e9a8fa465f")
    ) =
        Section(
            id = virreyDelPino1800_avDelLibertador5000_id,
            departure = TripPointFactory.virreyDelPino_1800(),
            arrival = TripPointFactory.avDelLibertador_5000(),
            distanceInMeters = 3000.0,
            driver = "John Smith",
            vehicle = "Ford mustang",
            initialAmountOfSeats = 4,
            bookedSeats = 0,
            tripId = tripId
        )

    fun avDelLibertador5000_avCabildo20(
        tripId: UUID = UUID.fromString("bd7ee293-f5d3-4832-a74c-17e9a8fa465f")
    ) =
        Section(
            id = avDelLibertador5000_avCabildo20_id,
            departure = TripPointFactory.avDelLibertador_5000(),
            arrival = TripPointFactory.avCabildo_20(),
            distanceInMeters = 3000.0,
            driver = "John Smith",
            vehicle = "Ford mustang",
            initialAmountOfSeats = 4,
            bookedSeats = 0,
            tripId = tripId
        )

    fun withDriver(driverId: UUID) =
        Section(
            id = UUID.randomUUID().toString(),
            departure = TripPointFactory.virreyDelPino_2880(),
            arrival = TripPointFactory.virreyDelPino_1800(),
            distanceInMeters = 1300.0,
            driver = driverId.toString(),
            vehicle = "Ford mustang",
            initialAmountOfSeats = 4,
            bookedSeats = 0,
            tripId = UUID.randomUUID()
        )

    class Builder(private var section: Section) {

        fun allSeatsAvailable() = apply {
            this.section = this.section.copy(bookedSeats = 0)
        }

        fun noSeatsAvailable() = apply {
            this.section = this.section.copy(
                initialAmountOfSeats = 1,
                bookedSeats = 1,
            )
        }

        fun someSeatsAvailable() = apply {
            this.section = this.section.copy(
                initialAmountOfSeats = 3,
                bookedSeats = 1,
            )
        }

        fun build() = section
    }
}

fun Section.builder() = SectionFactory.Builder(section = this)
