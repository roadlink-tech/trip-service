package com.roadlink.tripservice.infrastructure.persistence

import com.roadlink.tripservice.domain.Location
import com.roadlink.tripservice.domain.trip.section.Section
import com.roadlink.tripservice.domain.trip.section.SectionRepository
import java.time.Instant

class InMemorySectionRepository(
    private val sections: MutableList<Section> = mutableListOf(),
) : SectionRepository {
    override fun save(section: Section) {
        sections.add(section)
    }

    override fun save(sections: Set<Section>) {
        sections.forEach { section ->
            this.save(section)
        }
    }

    override fun findNextSections(from: Location, at: Instant): Set<Section> {
        return sections
            .filter { it.departuresFrom(from) }
            .filter { it.departuresAfterOrEqual(at) }
            .toSet()
    }

    override fun findAllById(sectionsIds: Set<String>): Set<Section> {
        return sections
            .filter { it.id in sectionsIds }
            .toSet()
    }

    fun deleteAll() {
        sections.clear()
    }
}