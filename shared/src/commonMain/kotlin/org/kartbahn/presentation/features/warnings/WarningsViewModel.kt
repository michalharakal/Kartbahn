package org.kartbahn.presentation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.kartbahn.common.CommonViewModel
import org.kartbahn.common.KartbahnRepository
import org.kartbahn.core.CFlow
import org.kartbahn.core.LogLevel
import org.kartbahn.core.asCommonFlow
import org.kartbahn.core.logger
import org.kartbahn.domain.model.Roads
import org.kartbahn.presentation.features.warnings.model.WarningsViewModelData
import org.kartbahn.presentation.features.roads.model.RoadViewModelData
import org.kartbahn.presentation.features.warnings.model.WarningViewModelData
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.native.concurrent.ThreadLocal

class WarningsViewModel(private val roadId: String) : CommonViewModel(), KoinComponent {
    private val repository: KartbahnRepository by inject()

    val road: Flow<WarningsViewModelData> = repository.roadsStateModel.map { roads: Roads ->
        val road = roads.roads.first { road -> road.name == roadId }
        WarningsViewModelData(road.name, road.warnings.map { warning ->
            WarningViewModelData(
                warningId = warning.warningId,
                title = warning.title,
                subtitle = warning.subtitle,
                start = warning.start
            )
        })
    }

    @Suppress("unused")
    fun getCommonFlowFromIos(roadId: String): CFlow<WarningsViewModelData> {
        clientScope.launch {
            logger(LogLevel.INFO, "WarningsViewModel", "getCommonFlowFromIos $roadId")
            repository.fetchRoad(roadId)
        }
        return road.asCommonFlow()
    }

    fun getLiveData(roadId: String): CFlow<RoadViewModelData> =
        repository.getRoadObservable(roadId).map { road ->
            RoadViewModelData(road.name, road.warnings.size)
        }.asCommonFlow()

    fun getRoadState(roadId: String): RoadViewModelData {
        val roadState = repository.getRoadsState(roadId)
        return RoadViewModelData(roadState.name, roadState.warnings.size)
    }

    fun refresh() {
        clientScope.launch {
            logger(LogLevel.INFO, "WarningsViewModel", "getCommonFlowFromIos $roadId")
            repository.fetchRoad(roadId)
        }
    }


    @ThreadLocal
    companion object {
        fun create(roadId: String) = WarningsViewModel(roadId)
    }
}