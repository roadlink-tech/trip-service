package com.roadlink.tripservice.infrastructure.persistence

import com.roadlink.tripservice.domain.trip.section.SectionRepository
import com.roadlink.tripservice.domain.trip_solicitude.TripLegSolicitudeRepository
import com.roadlink.tripservice.domain.trip_solicitude.TripPlanSolicitude
import com.roadlink.tripservice.domain.trip_solicitude.TripPlanSolicitudeRepository
import com.roadlink.tripservice.usecases.factory.SectionFactory
import com.roadlink.tripservice.usecases.trip_solicitude.TripLegSolicitudeFactory
import com.roadlink.tripservice.usecases.trip_solicitude.plan.TripPlanSolicitudeFactory
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.*

@MicronautTest
class MySQLTripLegSolicitudeRepositoryTest {

    @Inject
    private lateinit var sectionRepository: SectionRepository

    @Inject
    private lateinit var tripPlanSolicitudeRepository: TripPlanSolicitudeRepository

    @Inject
    private lateinit var tripLegSolicitudeRepository: TripLegSolicitudeRepository

    @Test
    fun `given no trip application exists with the given driver id when find all by driver id then should return empty list`() {
        val otherDriverId = UUID.randomUUID()
        givenASavedTripPlanApplicationWithSections(TripLegSolicitudeFactory.any())

        val result = tripLegSolicitudeRepository.find(TripLegSolicitudeRepository.CommandQuery(driverId = otherDriverId))

        assertTrue { result.isEmpty() }
    }

    @Test
    fun `given trip applications exists with the given driver id when find all by driver id then should return them`() {
        val driverId = UUID.randomUUID()
        val tripApplication1 = givenASavedTripPlanApplicationWithSections(TripLegSolicitudeFactory.withDriver(driverId))
        val tripApplication2 = givenASavedTripPlanApplicationWithSections(TripLegSolicitudeFactory.withDriver(driverId))

        val otherDriverId = UUID.randomUUID()
        givenASavedTripPlanApplicationWithSections(TripLegSolicitudeFactory.withDriver(otherDriverId))

        val result = tripLegSolicitudeRepository.find(TripLegSolicitudeRepository.CommandQuery(driverId = driverId))

        assertEquals(2, result.size)
        assertTrue { result.containsAll(setOf(tripApplication1, tripApplication2)) }
    }

    @Test
    fun `given some trip plan application saved, when find by driver id and trip id then it must be retrieved `() {
        val driverId = UUID.randomUUID()
        val tripId = UUID.randomUUID()
        val tripApplication1 =
            givenASavedTripPlanApplicationWithSections(
                TripLegSolicitudeFactory.withDriver(
                    driverId = driverId,
                    tripId = tripId
                )
            )
        val tripApplication2 =
            givenASavedTripPlanApplicationWithSections(
                TripLegSolicitudeFactory.withDriver(
                    driverId = driverId,
                    tripId = tripId
                )
            )

        val otherDriverId = UUID.randomUUID()
        val otherTripId = UUID.randomUUID()
        val tripApplication3 = givenASavedTripPlanApplicationWithSections(
            TripLegSolicitudeFactory.withDriver(
                driverId = otherDriverId,
                tripId = otherTripId
            )
        )


        // THEN
        assertTrue {
            tripLegSolicitudeRepository.find(
                TripLegSolicitudeRepository.CommandQuery(
                    driverId = driverId,
                    tripId = tripId
                )
            ).containsAll(setOf(tripApplication1, tripApplication2))
        }
        assertTrue {
            tripLegSolicitudeRepository.find(
                TripLegSolicitudeRepository.CommandQuery(
                    driverId = otherDriverId,
                    tripId = otherTripId
                )
            ).containsAll(setOf(tripApplication3))
        }
    }

    @Test
    fun `given no trip application exists with the given trip id when find by trip id then should return empty list`() {
        val otherTripId = UUID.randomUUID()
        givenASavedTripPlanApplicationWithSections(TripLegSolicitudeFactory.any())

        val result = tripLegSolicitudeRepository.find(TripLegSolicitudeRepository.CommandQuery(tripId = otherTripId))

        assertTrue { result.isEmpty() }
    }

    @Test
    fun `given trip applications exists with the given trip id when find by trip id then should return them`() {
        val avCabildoSection = SectionFactory.avCabildo()
        val tripApplication1 =
            givenASavedTripPlanApplicationWithSections(TripLegSolicitudeFactory.withSections(listOf(avCabildoSection)))
        val tripApplication2 =
            givenASavedTripPlanApplicationWithSections(TripLegSolicitudeFactory.withSections(listOf(avCabildoSection)))

        val virreyDelPinoSection = SectionFactory.virreyDelPino(tripId = UUID.randomUUID())
        givenASavedTripPlanApplicationWithSections(TripLegSolicitudeFactory.withSections(listOf(virreyDelPinoSection)))

        val result =
            tripLegSolicitudeRepository.find(TripLegSolicitudeRepository.CommandQuery(tripId = avCabildoSection.tripId))

        assertEquals(2, result.size)
        assertTrue { result.containsAll(setOf(tripApplication1, tripApplication2)) }
    }

    @Test
    fun `given trip application has the given section when find by section then should return empty it`() {
        val avCabildoSection = SectionFactory.avCabildo()
        val tripApplication = TripLegSolicitudeFactory.withSections(listOf(avCabildoSection))
        givenASavedTripPlanApplication(TripPlanSolicitudeFactory.withApplications(listOf(tripApplication)))

        val result =
            tripLegSolicitudeRepository.find(TripLegSolicitudeRepository.CommandQuery(sectionId = avCabildoSection.id))

        assertEquals(listOf(tripApplication), result)
    }

    @Test
    fun `given no trip application has the given section when find by section then should return empty set`() {
        val avCabildoSection = SectionFactory.avCabildo()
        val tripApplication = TripLegSolicitudeFactory.withSections(listOf(avCabildoSection))
        givenASavedTripPlanApplication(TripPlanSolicitudeFactory.withApplications(listOf(tripApplication)))

        val result =
            tripLegSolicitudeRepository.find(TripLegSolicitudeRepository.CommandQuery(sectionId = SectionFactory.virreyDelPino_id))

        assertTrue { result.isEmpty() }
    }

    private fun givenASavedTripPlanApplicationWithSections(tripLegSolicitude: TripPlanSolicitude.TripLegSolicitude): TripPlanSolicitude.TripLegSolicitude {
        tripLegSolicitude.sections.forEach {
            if (sectionRepository.findAllById(setOf(it.id)).isEmpty()) {
                sectionRepository.save(it)
            }
        }
        tripPlanSolicitudeRepository.insert(
            TripPlanSolicitudeFactory.withApplications(listOf(tripLegSolicitude))
        )

        return tripLegSolicitude
    }

    private fun givenASavedTripPlanApplication(tripPlanSolicitude: TripPlanSolicitude): TripPlanSolicitude {
        tripPlanSolicitude.tripLegSolicitudes.flatMap { it.sections }.forEach {
            sectionRepository.save(it)
        }

        tripPlanSolicitudeRepository.insert(tripPlanSolicitude)

        return tripPlanSolicitude
    }
}