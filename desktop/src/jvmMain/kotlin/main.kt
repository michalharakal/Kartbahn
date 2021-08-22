import androidx.compose.desktop.Window
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kartbahn.api.KartbahnApi
import org.kartbahn.common.KartbahnRepository
import org.kartbahn.di.appModule
import org.kartbahn.di.initKoin
import org.kartbahn.presentation.RoadsList
import org.kartbahn.presentation.RoadsViewModel
import org.koin.java.KoinJavaComponent.inject

fun main() {
    initKoin(appModule)
    val statusScreenViewModel: RoadsViewModel by inject(RoadsViewModel::class.java)
    val repository: KartbahnRepository by inject(KartbahnRepository::class.java)
    GlobalScope.launch {
        val events = KartbahnApi().getRoads()
        repository.updateRoads(events)
    }

    return Window {
        RoadsList(statusScreenViewModel)
    }


}