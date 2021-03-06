package org.kartbahn.presentation

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import org.kartbahn.domain.model.Road
import org.kartbahn.presentation.features.roads.model.RoadViewModelData
import org.kartbahn.presentation.features.roads.model.createDefaultRoadsViewModelData

@Composable
fun RoadsList(
    roadsViewModel: RoadsViewModel,
    selectedRoad: RoadViewModelData?,
    roadSelected: (person: RoadViewModelData) -> Unit
) {

    val roadsState = roadsViewModel.roads.collectAsState(
        createDefaultRoadsViewModelData(),
        roadsViewModel.clientScope.coroutineContext
    )

    LazyColumn {
        items(roadsState.value.roads.size) { roadIndex ->
            RoadListCell(roadsViewModel, roadsState.value.roads[roadIndex], selectedRoad, roadSelected)
        }
    }
}
