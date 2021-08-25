import androidx.compose.desktop.Window
import org.kartbahn.di.appModule
import org.kartbahn.di.initKoin
import org.kartbahn.presentation.RoadsList
import org.kartbahn.presentation.RoadsViewModel
import org.koin.java.KoinJavaComponent.inject

fun main() {
    initKoin(appModule)
    val statusScreenViewModel: RoadsViewModel by inject(RoadsViewModel::class.java)
    statusScreenViewModel.refresh()

    return Window {
        RoadsList(statusScreenViewModel)
    }


}