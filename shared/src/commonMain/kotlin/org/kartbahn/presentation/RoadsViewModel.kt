package org.kartbahn.presentation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.karbahn.api.models.Roads
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
    val roads: Flow<Roads> = repository.roadsStateModel
    private lateinit var _roads: Flow<Roads>
    lateinit var roadsStateCommonFlow: CFlow<RoadsViewModelData>

    init {
        clientScope.launch {
            _roads = repository.roadsStateModel
            roadsStateCommonFlow = _roads.map {
                mapRemoteRoadsData(it)
            }.asCommonFlow()
        }
    }

    private fun mapRemoteRoadsData(roads: Roads): RoadsViewModelData = RoadsViewModelData(
        roads.roads?.map { dtoRoad ->
            RoadViewModelData(dtoRoad)
        } ?: emptyList()
    )

    @Suppress("unused")
    fun getCommonFlowFromIos(): CFlow<RoadsViewModelData> {
        _roads = repository.roadsStateModel
        roadsStateCommonFlow = _roads.map {
            mapRemoteRoadsData(it)
        }.asCommonFlow()
        clientScope.launch {
            repository.fetch()
        }
        return roadsStateCommonFlow
    }

    @ThreadLocal
    companion object {
        fun create() = RoadsViewModel()
    }
}