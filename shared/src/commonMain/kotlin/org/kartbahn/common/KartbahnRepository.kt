package org.kartbahn.common

import kotlinx.coroutines.flow.Flow
import org.kartbahn.api.KartbahnApi
import org.kartbahn.core.LogLevel
import org.kartbahn.core.logger
import org.kartbahn.domain.model.Road
import org.kartbahn.domain.model.Roads
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class KartbahnRepository : KoinComponent {

    private val kartbahnApi: KartbahnApi by inject()

    inner class RoadsStateModel : ValueModel<Roads>(Roads(emptyList()))

    private val _roadsStateModel = RoadsStateModel()

    val roadsStateModel: Flow<Roads>
        get() = _roadsStateModel.model

    fun updateRoads(value: org.kartbahn.api.models.Roads) {
        _roadsStateModel.setValue(value.toDomain())
    }

    suspend fun fetch() {
        logger(LogLevel.INFO, "KartbahnRepository", "getCommonFlowFromIos")
        updateRoads(kartbahnApi.getRoads())
    }

    private fun org.kartbahn.api.models.Roads.toDomain(): Roads = Roads(
        this.roads!!.map { road ->
            Road(road, emptyList())
        })
}



