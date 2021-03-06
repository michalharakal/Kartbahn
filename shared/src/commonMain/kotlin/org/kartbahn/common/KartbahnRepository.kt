package org.kartbahn.common

import io.ktor.client.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.DeserializationStrategy
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
import kotlinx.serialization.json.Json
import org.kartbahn.api.LocalDateTimeHolder

class KartbahnRepository : KoinComponent {

    init {
        logger(LogLevel.INFO, "KartbahnRepository", "constructor")
    }

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

    suspend fun fetchRoad(roadId: String):Road {
        logger(LogLevel.INFO, "KartbahnRepository", "fetchRoad $roadId")
        val result = getWarnings(roadId)
        val oldRoads = _roadsStateModel.model.value
        _roadsStateModel.setValue(
            Roads(
                oldRoads.roads.map { oldRoad ->
                    if (oldRoad.name == roadId) {
                        Road(
                            oldRoad.name,
                            oldRoad.roadWork,
                            result.map { warning ->
                                Warning(
                                    warningId = warning.warningId,
                                    title = warning.title,
                                    subtitle = warning.subtitle,
                                    start = warning.start
                                )
                            },
                            oldRoad.electricChargingStations
                        )
                    } else {
                        oldRoad
                    }
                }

            )
        )
        return _roadsStateModel.model.value.roads.first { road -> road.name == roadId }
    }

    suspend fun fetchFromNetwork(): DataState<org.kartbahn.api.models.Roads> {
        logger(LogLevel.INFO, "KartbahnRepository", "fetchFromNetwork")

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
            logger(LogLevel.ERROR, "KartbahnRepository", "Error downloading roads list", e)
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
        return kartbahnApi.getWarnings(roadId).warning!!.map { roadEventComposed ->
            val startTimeStamp =
                convertDateTime(roadEventComposed.item1.startTimestamp ?: "")
            with(roadEventComposed.item0) {
                Warning(
                    identifier ?: "",
                    title ?: "",
                    subtitle ?: "",
                    startTimeStamp
                )
            }
        }
    }

    private fun convertDateTime(startTimeStampStr: String): LocalDateTime =
        if (startTimeStampStr.isNotBlank()) {
            // TODO dirty hack ignores timezone offset
            val cutOffOffset = startTimeStampStr.substringBefore("+")
            val localDateTimeHolderJsonStr = "{\"timestamp\":\"$cutOffOffset\"}"
            try {
                // convert and manually add 2 hours
                with(
                    Json.decodeFromString(
                        getDeserializer(),
                        localDateTimeHolderJsonStr
                    ).timestamp
                ) {
                    LocalDateTime(year, monthNumber, dayOfMonth, hour + 2, minute, second)
                }
            } catch (e: Exception) {
                LocalDateTime(1970, 1, 1, 0, 0)
            }
        } else {
            LocalDateTime(1970, 1, 1, 0, 0)
        }


    private fun getDeserializer(): DeserializationStrategy<LocalDateTimeHolder> =
        LocalDateTimeHolder.serializer()

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



