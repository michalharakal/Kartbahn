package org.kartbahn.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kartbahn.api.KartbahnApi
import org.kartbahn.common.KartbahnRepository
import org.kartbahn.presentation.RoadsList
import org.kartbahn.presentation.RoadsViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

class MainActivity : AppCompatActivity(), KoinComponent {

    private val roadsViewModel: RoadsViewModel by viewModel()
    private val kartbahnApi: KartbahnApi by inject()
    val repository by inject<KartbahnRepository>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initClient()

        setContent {
            RoadsList(roadsViewModel)
        }
    }

    private fun initClient() {
        GlobalScope.launch {
            val roads = kartbahnApi.getRoads()
            repository.updateRoads(roads)
        }
    }
}