package org.kartbahn.common

import kotlinx.coroutines.flow.Flow
import org.karbahn.api.models.Roads

class KartbahnRepository {

    inner class RoadsStateModel : ValueModel<Roads>(Roads(emptyList()))

    private val _roadsStateModel = RoadsStateModel()

    val roadsStateModel: Flow<Roads>
        get() = _roadsStateModel.model

    fun updateRoads(value: Roads) {
        _roadsStateModel.setValue(value)
    }
}