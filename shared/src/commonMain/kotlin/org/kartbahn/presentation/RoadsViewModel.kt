package org.kartbahn.presentation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.kartbahn.common.KartbahnRepository
import org.kartbahn.common.CommonViewModel
import org.kartbahn.core.CFlow
import org.kartbahn.core.LogLevel
import org.kartbahn.core.asCommonFlow
import org.kartbahn.core.logger
import org.kartbahn.presentation.model.RoadViewModelData
import org.kartbahn.presentation.model.RoadsViewModelData
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.native.concurrent.ThreadLocal


class RoadsViewModel : CommonViewModel(), KoinComponent {
    private val repository: KartbahnRepository by inject()

    val roads: Flow<RoadsViewModelData> = repository.roadsStateModel.map { roads ->
        mapRoads(roads)
    }

    private fun mapRoads(roads: org.kartbahn.domain.model.Roads) =
        RoadsViewModelData(roads.roads.map { road ->
            RoadViewModelData(road.name)
        })


    //private var _roads: Flow<Roads>
    //private lateinit var roadsStateCommonFlow: CFlow<RoadsViewModelData>

    init {
        logger(LogLevel.INFO, "RoadsViewModel", "init 1")
    }

    @Suppress("unused")
    fun getCommonFlowFromIos(): CFlow<RoadsViewModelData> {
        logger(LogLevel.INFO, "RoadsViewModel", "getCommonFlowFromIos 1")

        clientScope.launch {
            logger(LogLevel.INFO, "RoadsViewModel", "getCommonFlowFromIos 2")
            repository.fetch()
        }
        val roadsStateCommonFlow = roads.asCommonFlow()
        repository.roadsStateModel.map { roads ->
            mapRoads(roads)
        }
        return roadsStateCommonFlow
    }

    @ThreadLocal
    companion object {
        fun create() = RoadsViewModel()
    }
}