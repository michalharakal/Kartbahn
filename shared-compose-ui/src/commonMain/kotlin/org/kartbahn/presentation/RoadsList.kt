package org.kartbahn.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import org.kartbahn.presentation.model.createDefaultRoadsViewModelData
import androidx.compose.material.Card
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Composable
fun RoadsList(roadsViewModel: RoadsViewModel) {

    val roadsState = roadsViewModel.roads.collectAsState(
        createDefaultRoadsViewModelData(),
        roadsViewModel.clientScope.coroutineContext
    )

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Kartbahn") })
            }) {
            LazyColumn {
                items(roadsState.value.roads.size) { roadIndex ->
                    RoadListCell(roadsState.value.roads[roadIndex].name)
                }
            }
        }
    }
}
