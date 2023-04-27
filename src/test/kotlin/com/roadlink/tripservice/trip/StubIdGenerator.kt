package com.roadlink.tripservice.trip

import com.roadlink.tripservice.domain.IdGenerator

class StubIdGenerator(
    private val idsToGenerate: MutableList<String> = mutableListOf(),
) : IdGenerator {
    override fun id(): String  =
        idsToGenerate.removeFirst()

    fun nextIdToGenerate(id: String) {
        idsToGenerate.add(id)
    }

    fun nextIdToGenerate(vararg id: String) {
        idsToGenerate.addAll(id)
    }

}