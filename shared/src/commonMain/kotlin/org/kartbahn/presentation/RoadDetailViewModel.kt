package org.kartbahn.presentation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.kartbahn.common.CommonViewModel
import org.kartbahn.common.KartbahnRepository
import org.kartbahn.core.CFlow
import org.kartbahn.core.asCommonFlow
import org.kartbahn.domain.model.Road
import org.kartbahn.domain.model.Roads
import org.kartbahn.presentation.model.RoadDetailViewModelData
import org.kartbahn.presentation.model.RoadViewModelData
import org.kartbahn.presentation.model.WarningViewModelData
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.native.concurrent.ThreadLocal

class RoadDetailViewModel(private val roadId: String) : CommonViewModel(), KoinComponent {
    private val repository: KartbahnRepository by inject()

    val road: Flow<RoadDetailViewModelData> = repository.roadsStateModel.map { roads: Roads ->
        val road = roads.roads.first { road -> road.name == roadId }
        RoadDetailViewModelData(road.name, road.warnings.map { warning ->
            WarningViewModelData(warning.name)
        })
    }

    /*
    private fun mapRoads(roads: org.kartbahn.domain.model.Roads) =
        RoadsViewModelData(roads.roads.map { road ->
            RoadViewModelData(road.name)
        })

     */

    @Suppress("unused")
    fun getCommonFlowFromIos(): CFlow<RoadDetailViewModelData> {
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


    @ThreadLocal
    companion object {
        fun create(roadId: String) = RoadDetailViewModel(roadId)
    }
}