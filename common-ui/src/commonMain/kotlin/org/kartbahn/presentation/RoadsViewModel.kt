package org.kartbahn.presentation

import kotlinx.coroutines.flow.Flow
import org.karbahn.api.models.Roads
import org.kartbahn.common.KartbahnRepository
import org.kartbahn.common.CommonViewModel

class RoadsViewModel(repository: KartbahnRepository) : CommonViewModel() {
    val roads: Flow<Roads> = repository.roadsStateModel
}