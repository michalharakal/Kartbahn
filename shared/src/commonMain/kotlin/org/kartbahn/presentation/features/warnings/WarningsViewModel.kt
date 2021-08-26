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

    val warnings: Flow<WarningsViewModelData> = repository.roadsStateModel.map { roads: Roads ->
        val road = roads.roads.firstOrNull { road -> road.name == roadId }
        if (road != null) {
            WarningsViewModelData(road.name, road.warnings.map { warning ->
                WarningViewModelData(
                    warningId = warning.warningId,
                    title = warning.title,
                    subtitle = warning.subtitle,
                    start = warning.start
                )
            })
        } else {
            WarningsViewModelData("", emptyList())
        }
    }

    @Suppress("unused")
    fun getCommonFlowFromIos(roadId: String): CFlow<WarningsViewModelData> {
        clientScope.launch {
            logger(LogLevel.INFO, "WarningsViewModel", "getCommonFlowFromIos $roadId")
            repository.fetchRoad(roadId)
        }
        return warnings.asCommonFlow()
    }

    fun getLiveData(): CFlow<WarningsViewModelData> = warnings.asCommonFlow()

    fun getRoadState(roadId: String): WarningsViewModelData {
        val roadState = repository.getRoadsState(roadId)
        return WarningsViewModelData(roadState.name, roadState.warnings.map { warning ->
            WarningViewModelData(
                warningId = warning.warningId,
                title = warning.title,
                subtitle = warning.subtitle,
                start = warning.start
            )
        })
    }

    fun refresh() {
        clientScope.launch {
            logger(LogLevel.INFO, "WarningsViewModel", "refresh $roadId")
            repository.fetchRoad(roadId)
        }
    }


    @ThreadLocal
    companion object {
        fun create(roadId: String) = WarningsViewModel(roadId)
    }
}