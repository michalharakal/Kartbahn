package org.kartbahn.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
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
import org.kartbahn.presentation.features.roads.model.RoadViewModelData

@Composable
fun RoadListCell(roadId: String) {
    val roadDetailViewModel = WarningsViewModel(roadId)
    roadDetailViewModel.refresh()
    val roadsState: State<RoadViewModelData> =
        roadDetailViewModel.getLiveData(roadId).collectAsState(
            roadDetailViewModel.getRoadState(roadId),
            roadDetailViewModel.clientScope.coroutineContext
        )
    Row {
        Box(
            modifier = Modifier.size(width = 100.dp, height = 60.dp).padding(10.dp)
                .clip(HighwaySignShape())
                .background(Color.Blue),
            contentAlignment = Alignment.Center
        ) {
            Text(
                roadsState.value.name,
                fontWeight = FontWeight.W900,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
        Text(
            if (roadsState.value.warningsCount == 0) {
                "Keine Meldungen"
            } else {
                roadsState.value.warningsCount.toString()
            },
            textAlign = TextAlign.Center
        )
    }
}

class HighwaySignShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val signPath = Path().apply {
            moveTo(0f, size.height * 0.1f)
            lineTo(x = size.width / 2, y = 0f)
            lineTo(x = size.width, y = size.height * 0.1f)
            lineTo(x = size.width, y = size.height - size.height * 0.1f)
            lineTo(x = size.width / 2, y = size.height)
            lineTo(x = 0f, y = size.height - size.height * 0.1f)
            lineTo(x = 0f, size.height * 0.1f)
        }
        return Outline.Generic(path = signPath)
    }
}
