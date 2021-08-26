import androidx.compose.desktop.Window
import org.kartbahn.di.appModule
import org.kartbahn.di.initKoin
import org.kartbahn.presentation.MainScreen
import org.kartbahn.presentation.RoadsViewModel
import org.koin.java.KoinJavaComponent.inject

fun main() {
    initKoin(appModule)
    val roadsViewModel: RoadsViewModel by inject(RoadsViewModel::class.java)
    roadsViewModel.refresh()

    return Window {
        MainScreen(roadsViewModel)
    }


}