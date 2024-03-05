package com.roadlink.tripservice.infrastructure.persistence

import com.roadlink.tripservice.domain.trip.section.SectionRepository
import com.roadlink.tripservice.domain.trip_application.TripPlanApplication
import com.roadlink.tripservice.domain.trip_application.TripPlanApplication.TripApplication.Status.*
import com.roadlink.tripservice.domain.trip_application.TripPlanApplicationRepository
import com.roadlink.tripservice.usecases.factory.SectionFactory
import com.roadlink.tripservice.usecases.trip_application.TripApplicationFactory
import com.roadlink.tripservice.usecases.trip_application.plan.TripPlanApplicationFactory
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

@MicronautTest
class MySQLTripPlanApplicationRepositoryTest {

    @Inject
    private lateinit var sectionRepository: SectionRepository

    @Inject
    lateinit var repository: TripPlanApplicationRepository

    @Test
    fun `given a trip plan application stored, when find it by passenger id then it must be retrieved`() {
        val tripApplicationId = UUID.randomUUID()
        val passengerId = UUID.randomUUID()
        val tripPlanApplication = TripPlanApplicationFactory.withASingleTripApplication(
            tripApplicationId = tripApplicationId,
            passengerId = passengerId.toString()
        )
        tripPlanApplication.tripApplications.flatMap { it.sections }.forEach {
            sectionRepository.save(it)
        }

        repository.insert(tripPlanApplication)

        assertEquals(
            listOf(tripPlanApplication),
            repository.find(TripPlanApplicationRepository.CommandQuery(passengerId = passengerId))
        )
    }

    @Test
    fun `given a trip plan application stored, when find it by passenger id and trip application status then it must be retrieved`() {
        val tripApplicationId = UUID.randomUUID()
        val passengerId = UUID.randomUUID()
        val tripPlanApplication = TripPlanApplicationFactory.withASingleTripApplication(
            tripApplicationId = tripApplicationId,
            passengerId = passengerId.toString(),
            tripApplicationStatus = REJECTED
        )
        tripPlanApplication.tripApplications.flatMap { it.sections }.forEach {
            sectionRepository.save(it)
        }

        repository.insert(tripPlanApplication)

        assertEquals(
            listOf(tripPlanApplication),
            repository.find(TripPlanApplicationRepository.CommandQuery(passengerId = passengerId))
        )
    }

    @Test
    fun `when find trip plan application by passenger id, but there no is anything, then an empty list must be retrieved`() {
        val passengerId = UUID.randomUUID()

        assertEquals(
            listOf<TripPlanApplication>(),
            repository.find(TripPlanApplicationRepository.CommandQuery(passengerId = passengerId))
        )
    }

    @Test
    fun `given no trip plan application when save one then should be able to find it`() {
        val tripApplicationId = UUID.randomUUID()
        val tripPlanApplication =
            TripPlanApplicationFactory.withASingleTripApplication(tripApplicationId = tripApplicationId)
        tripPlanApplication.tripApplications.flatMap { it.sections }.forEach {
            sectionRepository.save(it)
        }

        repository.insert(tripPlanApplication)

        assertEquals(
            tripPlanApplication,
            repository.findByTripApplicationId(tripApplicationId)
        )
    }

    @Test
    fun `given trip plan application exists and it is modified when update it then should retrieve it`() {
        val passengerId = UUID.randomUUID()
        val tripApplicationId = UUID.randomUUID()
        val tripPlanApplication = givenExists(
            TripPlanApplicationFactory.withASingleTripApplication(
                tripApplicationId = tripApplicationId,
                passengerId = passengerId.toString(),
            )
        )
        tripPlanApplication.confirmApplicationById(
            tripPlanApplication.tripApplications.first().id,
            passengerId
        )

        repository.update(tripPlanApplication)

        assertEquals(
            tripPlanApplication,
            repository.findByTripApplicationId(tripApplicationId)
        )
    }

    @Test
    fun `given no trip plan application exists with the given id when find by trip application id then should return null`() {
        val otherTripApplicationId = UUID.randomUUID()
        givenExists(TripPlanApplicationFactory.withASingleTripApplication())

        val result = repository.findByTripApplicationId(otherTripApplicationId)

        assertNull(result)
    }


    @Test
    fun `given no trip plan application when save one then should be able to find it by id`() {
        val tripPlanApplication = TripPlanApplicationFactory.withASingleTripApplication()
        tripPlanApplication.tripApplications.flatMap { it.sections }.forEach {
            sectionRepository.save(it)
        }

        repository.insert(tripPlanApplication)

        assertEquals(
            tripPlanApplication,
            repository.find(TripPlanApplicationRepository.CommandQuery(ids = listOf(tripPlanApplication.id))).first()
        )
    }

    @Test
    fun `given trip plan application exists and it is modified when update it then should retrieve it by id`() {
        val passengerId = UUID.randomUUID()
        val tripPlanApplication = givenExists(
            TripPlanApplicationFactory.withASingleTripApplication(
                passengerId = passengerId.toString(),
            )
        )
        tripPlanApplication.confirmApplicationById(
            tripPlanApplication.tripApplications.first().id,
            passengerId
        )

        repository.update(tripPlanApplication)

        assertEquals(
            tripPlanApplication,
            repository.find(TripPlanApplicationRepository.CommandQuery(ids = listOf(tripPlanApplication.id))).first()
        )
    }

    @Test
    fun `given no trip plan application exists with the given id when find by id then should return an empty list`() {
        val otherTripPlanApplicationId = UUID.randomUUID()
        givenExists(TripPlanApplicationFactory.withASingleTripApplication())

        val result = repository.find(
            TripPlanApplicationRepository.CommandQuery(ids = listOf(otherTripPlanApplicationId))
        )

        assertTrue(result.isEmpty())
    }

    private fun givenExists(tripPlanApplication: TripPlanApplication): TripPlanApplication {
        tripPlanApplication.tripApplications.flatMap { it.sections }.forEach {
            sectionRepository.save(it)
        }

        repository.insert(tripPlanApplication)

        return tripPlanApplication
    }
}