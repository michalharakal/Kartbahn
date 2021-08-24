package org.kartbahn.presentation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.kartbahn.common.KartbahnRepository
import org.kartbahn.common.CommonViewModel
import org.kartbahn.core.CFlow
import org.kartbahn.core.asCommonFlow
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
            RoadViewModelData(road.name, road.warnings.size)
        })

    @Suppress("unused")
    fun getCommonFlowFromIos(): CFlow<RoadsViewModelData> {
        refresh()
        val roadsStateCommonFlow = roads.asCommonFlow()
        repository.roadsStateModel.map { roads ->
            mapRoads(roads)
        }
        return roadsStateCommonFlow
    }

    fun refresh() {
        clientScope.launch {
            repository.refresh()
        }
    }

    @ThreadLocal
    companion object {
        fun create() = RoadsViewModel()
    }
}