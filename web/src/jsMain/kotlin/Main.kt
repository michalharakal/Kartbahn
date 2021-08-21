import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kartbahn.common.KartbahnRepository
import org.jetbrains.compose.common.foundation.layout.Column
import org.jetbrains.compose.common.foundation.layout.Row
import org.jetbrains.compose.common.material.Text
import org.jetbrains.compose.web.renderComposable
import org.kartbahn.api.KartbahnApi

fun main() {
    val conferenceEventsState = mutableStateOf(listOf<String>())

    val repository = KartbahnRepository()
    GlobalScope.launch {
        repository.updateRoads(KartbahnApi().getRoads())
        repository.roadsStateModel.collect { events ->
            conferenceEventsState.value = events.roads!!
        }
    }

    renderComposable(rootElementId = "root") {
        Text("Kartbahn")
        Column {
            conferenceEventsState.value.forEach { session ->
                Row {
                    Text(session)
                }
            }
        }
    }
}
