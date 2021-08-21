package org.kartbahn.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.ktor.util.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kartbahn.api.KartbahnApi
import org.kartbahn.common.KartbahnRepository
import org.kartbahn.presentation.RoadsList
import org.kartbahn.presentation.RoadsViewModel

@InternalAPI
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initClient()

        setContent {
            RoadsList(roadsViewModel)
        }
    }

    val repository = KartbahnRepository()

    class BaseViewModelFactory<T>(val creator: () -> T) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return creator() as T
        }
    }


    
    private val roadsViewModel: RoadsViewModel by lazy {
        ViewModelProvider(
            this,
            BaseViewModelFactory { RoadsViewModel(repository) }
        ).get(RoadsViewModel::class.java)
    }


    private fun initClient() {
        val kartbahnApi = KartbahnApi(engine = createUnsecureOkHttpClient(application))

        GlobalScope.launch {
            val roads = kartbahnApi.getRoads()
            repository.updateRoads(roads)
        }
    }
}