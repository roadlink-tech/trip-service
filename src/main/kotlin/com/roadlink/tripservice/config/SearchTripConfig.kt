package com.roadlink.tripservice.config

import com.roadlink.tripservice.domain.BruteForceSearchEngine
import com.roadlink.tripservice.usecases.SearchTrip
import io.micronaut.context.annotation.Factory
import jakarta.inject.Singleton

@Factory
class SearchTripConfig {
    @Singleton
    fun searchTrip(bruteForceSearchEngine: BruteForceSearchEngine): SearchTrip {
        return SearchTrip(
            searchEngine = bruteForceSearchEngine,
        )
    }
}