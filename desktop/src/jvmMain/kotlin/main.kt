import androidx.compose.desktop.Window
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kartbahn.api.KartbahnApi
import org.kartbahn.common.KartbahnRepository
import org.kartbahn.presentation.RoadsList
import org.kartbahn.presentation.RoadsViewModel


fun main() {
    val repository = KartbahnRepository()
    val statusScreenViewModel = RoadsViewModel(repository)
    GlobalScope.launch {
        val events = KartbahnApi().getRoads()
        repository.updateRoads(events)
    }

    return Window {
        RoadsList(statusScreenViewModel)
    }
}