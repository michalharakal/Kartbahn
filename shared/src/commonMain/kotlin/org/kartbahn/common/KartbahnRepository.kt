package org.kartbahn.common

import kotlinx.coroutines.flow.Flow
import org.karbahn.api.models.Roads
import org.kartbahn.api.KartbahnApi
import org.kartbahn.core.LogLevel
import org.kartbahn.core.logger
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class KartbahnRepository:KoinComponent {

    private val kartbahnApi: KartbahnApi by inject()

    inner class RoadsStateModel : ValueModel<Roads>(Roads(emptyList()))

    private val _roadsStateModel = RoadsStateModel()

    val roadsStateModel: Flow<Roads>
        get() = _roadsStateModel.model

    fun updateRoads(value: Roads) {
        _roadsStateModel.setValue(value.copy())
    }

    suspend fun fetch() {
        logger(LogLevel.INFO, "KartbahnRepository", "getCommonFlowFromIos")
        updateRoads(kartbahnApi.getRoads())
    }
}