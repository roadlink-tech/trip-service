package com.roadlink.tripservice.infrastructure.rest.error

enum class ErrorResponseCode {
    ALREADY_EXISTS_TRIP_BY_DRIVER_IN_TIME_RANGE,
    INVALID_TRIP_TIME_RANGE,
    INSUFFICIENT_AMOUNT_OF_SEATS,
    TRIP_PLAN_SOLICITUDE_HAS_BEEN_REJECTED,
    EMPTY_DRIVER_TRIP_SUMMARY
}