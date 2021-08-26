package org.kartbahn.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import org.kartbahn.presentation.features.warnings.model.WarningViewModelData
import org.kartbahn.presentation.features.warnings.model.WarningsViewModelData

@Composable
fun WarningsView(roadId: String) {
    val warningsViewModel = WarningsViewModel(roadId)


    val warningsState = warningsViewModel.warnings.collectAsState(
        warningsViewModel.getRoadState(roadId),
        warningsViewModel.clientScope.coroutineContext
    )

    Column {
        Text(roadId)
        Card {
            LazyColumn {

                items(warningsState.value.warnings.size) { roadIndex ->
                    WarningListCell(warningsState.value.warnings[roadIndex])
                }
            }
        }
    }
}

@Composable
fun WarningListCell(warning: WarningViewModelData) {
    Column {
        Text(warning.title)
        Text(warning.subtitle)
    }
}
