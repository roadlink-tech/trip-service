package com.roadlink.tripservice.infrastructure.persistence.trip_application

import com.roadlink.tripservice.domain.trip_application.TripPlanApplication
import com.roadlink.tripservice.domain.trip_application.TripPlanApplicationRepository
import jakarta.persistence.EntityManager
import jakarta.persistence.NoResultException
import java.util.*


class MySQLTripPlanApplicationRepository(
    private val entityManager: EntityManager,
): TripPlanApplicationRepository {

    override fun insert(application: TripPlanApplication) {
        entityManager.persist(TripPlanApplicationJPAEntity.from(application))
    }

    override fun update(application: TripPlanApplication) {
        entityManager.merge(TripPlanApplicationJPAEntity.from(application))
    }

    override fun findByTripApplicationId(tripApplicationId: UUID): TripPlanApplication? {
        return try {
            entityManager.createQuery(
                "SELECT tpa FROM TripPlanApplicationJPAEntity tpa WHERE tpa.id = :id",
                TripPlanApplicationJPAEntity::class.java
            )
                .setParameter("id", tripApplicationId)
                .singleResult
                .toDomain()
        } catch (e: NoResultException) {
            null
        }
    }

    override fun findTripApplicationBySectionId(sectionId: String): Set<TripPlanApplication.TripApplication> {
        return entityManager.createQuery(
            """
                |SELECT ta FROM TripApplicationJPAEntity ta
                |JOIN ta.sections s
                |WHERE s.id = :sectionId
                |""".trimMargin(),
            TripApplicationJPAEntity::class.java
        )
            .setParameter("sectionId", sectionId)
            .resultList
            .map { it.toDomain() }
            .toSet()
    }

}
