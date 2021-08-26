package org.kartbahn.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.kartbahn.domain.model.Road
import org.kartbahn.presentation.features.roads.model.RoadViewModelData

@Composable
actual fun MainScreen(roadsViewModel: RoadsViewModel) {
    var selectedRoad by remember { mutableStateOf<RoadViewModelData?>(roadsViewModel.getRoadState("A4")) }
    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Kartbahn") })
            }
        ) {
            Box(Modifier.fillMaxSize().background(color = Color.LightGray)) {
                RoadsList(roadsViewModel, selectedRoad) {
                    selectedRoad = it
                    // switch screen
                }
            }
        }
    }
}
