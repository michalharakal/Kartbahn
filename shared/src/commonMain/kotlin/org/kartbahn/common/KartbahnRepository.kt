package org.kartbahn.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.kartbahn.api.KartbahnApi
import org.kartbahn.core.CFlow
import org.kartbahn.core.LogLevel
import org.kartbahn.core.asCommonFlow
import org.kartbahn.core.logger
import org.kartbahn.domain.model.Road
import org.kartbahn.domain.model.Roads
import org.kartbahn.domain.model.Warning
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class KartbahnRepository : KoinComponent {

    private val kartbahnApi: KartbahnApi by inject()

    inner class RoadsStateModel : ValueModel<Roads>(Roads(emptyList()))

    private val _roadsStateModel = RoadsStateModel()

    val roadsStateModel: Flow<Roads>
        get() = _roadsStateModel.model

    private fun updateRoads(value: org.kartbahn.api.models.Roads) {
        _roadsStateModel.setValue(value.toDomain())
    }

    suspend fun refresh() {
        val result = fetchFromNetwork()
        if (result.data != null) {
            updateRoads(result.data)
        }
    }

    suspend fun fetchFromNetwork(): DataState<org.kartbahn.api.models.Roads> {
        logger(LogLevel.INFO, "KartbahnRepository", "getCommonFlowFromIos")

        return try {
            val roads = kartbahnApi.getRoads()
            logger(LogLevel.DEBUG, "KartbahnRepository", "getRoads()")
            if (roads.roads != null) {
                logger(
                    LogLevel.DEBUG,
                    "KartbahnRepository",
                    "Fetched ${roads.roads.size} roads from network"
                )
                if (roads.roads.isEmpty()) {
                    DataState(empty = true)
                } else {
                    DataState(roads)
                }
            } else {
                DataState(empty = false)
            }
        } catch (e: Exception) {
            logger(LogLevel.ERROR, "KartbahnRepository", "Error downloading breed list", e)
            DataState(exception = "Unable to download roads list")
        }
    }

    private fun org.kartbahn.api.models.Roads.toDomain(): Roads = Roads(
        this.roads!!.map { road ->
            Road(
                name = road,
                roadWork = emptyList(),
                warnings = emptyList(),
                electricChargingStations = emptyList()
            )
        })

    private suspend fun getWarnings(roadId: String): List<Warning> {
        // ignore "/" in road name
        if (roadId.contains("/")) {
            return emptyList()
        }
        return kartbahnApi.getWarnings(roadId).warning!!.map {
            it.item0.title?.let { it1 -> Warning(it1) } ?: Warning("")
        }
    }

    fun getRoadsState(roadId: String): Road {
        return _roadsStateModel.model.value.roads.firstOrNull { road ->
            road.name == roadId
        } ?: return createDefaultRoad()
    }

    fun getRoadObservable(roadId: String): CFlow<Road> =
        _roadsStateModel.model.map { roads ->
            roads.roads.firstOrNull { road -> road.name == roadId } ?: createDefaultRoad()
        }.asCommonFlow()

    private fun createDefaultRoad(): Road = Road("", emptyList(), emptyList(), emptyList())

}



