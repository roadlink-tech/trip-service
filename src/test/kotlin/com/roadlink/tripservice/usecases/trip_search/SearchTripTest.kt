package com.roadlink.tripservice.usecases.trip_search

import com.roadlink.tripservice.domain.trip_search.BruteForceSearchEngine
import com.roadlink.tripservice.infrastructure.persistence.section.InMemorySectionRepository
import com.roadlink.tripservice.usecases.common.InstantFactory
import com.roadlink.tripservice.usecases.common.address.LocationFactory
import com.roadlink.tripservice.usecases.trip.SectionFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class SearchTripTest {

    private lateinit var inMemorySectionRepository: InMemorySectionRepository

    private lateinit var searchTrip: SearchTrip

    @BeforeEach
    fun setUp() {
        inMemorySectionRepository = InMemorySectionRepository()
        val bruteForceSearchEngine = BruteForceSearchEngine(
            sectionRepository = inMemorySectionRepository,
        )

        searchTrip = SearchTrip(
            searchEngine = bruteForceSearchEngine,
        )
    }

    @Test
    fun `given no section exists then should return empty list`() {
        val result = searchTrip(
            SearchTrip.Input(
            departure = LocationFactory.avCabildo_4853(),
            arrival = LocationFactory.avCabildo_20(),
            at = InstantFactory.october15_12hs(),
        ))

        assertTrue(result.isEmpty())
    }

    @Test
    fun `given exists a trip plan between the given departure and arrival then should return it`() {
        inMemorySectionRepository.save(SectionFactory.avCabildo())

        val result = searchTrip(
            SearchTrip.Input(
            departure = LocationFactory.avCabildo_4853(),
            arrival = LocationFactory.avCabildo_20(),
            at = InstantFactory.october15_12hs(),
        ))

        assertEquals(listOf(TripSearchPlanFactory.avCabildo()), result)
    }

    @Test
    fun `given exists a trip plan but not between the given departure and arrival then should return empty list`() {
        inMemorySectionRepository.save(SectionFactory.virreyDelPino())

        val result = searchTrip(
            SearchTrip.Input(
            departure = LocationFactory.avCabildo_4853(),
            arrival = LocationFactory.avCabildo_20(),
            at = InstantFactory.october15_12hs(),
        ))

        assertTrue(result.isEmpty())
    }

    @Test
    fun `given exists a trip plan with one meeting point but not between the given departure and arrival then should return empty list`() {
        inMemorySectionRepository.save(SectionFactory.virreyDelPino2880_avCabildo1621())
        inMemorySectionRepository.save(SectionFactory.avCabildo1621_virreyDelPino1800())

        val result = searchTrip(
            SearchTrip.Input(
            departure = LocationFactory.avCabildo_4853(),
            arrival = LocationFactory.avCabildo_20(),
            at = InstantFactory.october15_12hs(),
        ))

        assertTrue(result.isEmpty())
    }

    @Test
    fun `given exists two trip plan then should only return the one between the given departure and arrival`() {
        inMemorySectionRepository.save(SectionFactory.avCabildo())
        inMemorySectionRepository.save(SectionFactory.virreyDelPino())

        val result = searchTrip(
            SearchTrip.Input(
            departure = LocationFactory.avCabildo_4853(),
            arrival = LocationFactory.avCabildo_20(),
            at = InstantFactory.october15_12hs(),
        ))

        assertEquals(listOf(TripSearchPlanFactory.avCabildo()), result)
    }

    @Test
    fun `given exists a trip plan with one meeting point between the given departure and arrival then should return it`() {
        inMemorySectionRepository.save(SectionFactory.avCabildo4853_virreyDelPino1800())
        inMemorySectionRepository.save(SectionFactory.virreyDelPino1800_avCabildo20())

        val result = searchTrip(
            SearchTrip.Input(
            departure = LocationFactory.avCabildo_4853(),
            arrival = LocationFactory.avCabildo_20(),
            at = InstantFactory.october15_12hs(),
        ))

        assertEquals(listOf(TripSearchPlanFactory.avCabildo4853_virreyDelPino1800_avCabildo20()), result)
    }

    @Test
    fun `given exists two trip plans with one meeting point between the given departure and arrival then the shorter must be first`() {
        inMemorySectionRepository.save(SectionFactory.avCabildo4853_virreyDelPino1800())
        inMemorySectionRepository.save(SectionFactory.virreyDelPino1800_avCabildo20())

        inMemorySectionRepository.save(SectionFactory.avCabildo4853_virreyDelPino2880())
        inMemorySectionRepository.save(SectionFactory.virreyDelPino2880_avCabildo20())

        val result = searchTrip(
            SearchTrip.Input(
            departure = LocationFactory.avCabildo_4853(),
            arrival = LocationFactory.avCabildo_20(),
            at = InstantFactory.october15_12hs(),
        ))

        assertEquals(
            listOf(
                TripSearchPlanFactory.avCabildo4853_virreyDelPino1800_avCabildo20(),
                TripSearchPlanFactory.avCabildo4853_virreyDelPino2880_avCabildo20(),
            ),
            result
        )
    }

    @Test
    fun `given exists two trip plans with the same meeting point between the given departure and arrival then should return `() {
        inMemorySectionRepository.save(SectionFactory.avCabildo4853_virreyDelPino1800())
        inMemorySectionRepository.save(SectionFactory.virreyDelPino1800_avCabildo20())
        inMemorySectionRepository.save(SectionFactory.virreyDelPino1800_avDelLibertador5000())
        inMemorySectionRepository.save(SectionFactory.avDelLibertador5000_avCabildo20())

        inMemorySectionRepository.save(SectionFactory.avCabildo4853_virreyDelPino2880())
        inMemorySectionRepository.save(SectionFactory.virreyDelPino2880_avCabildo20())

        val result = searchTrip(
            SearchTrip.Input(
            departure = LocationFactory.avCabildo_4853(),
            arrival = LocationFactory.avCabildo_20(),
            at = InstantFactory.october15_12hs(),
        ))

        assertEquals(
            listOf(
                TripSearchPlanFactory.avCabildo4853_virreyDelPino1800_avCabildo20(),
                TripSearchPlanFactory.avCabildo4853_virreyDelPino2880_avCabildo20(),
                TripSearchPlanFactory.avCabildo4853_virreyDelPino1800_avDelLibertador5000_avCabildo20(),
            ),
            result
        )
    }

}