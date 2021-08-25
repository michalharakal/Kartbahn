package org.kartbahn.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import org.kartbahn.presentation.RoadsList
import org.kartbahn.presentation.RoadsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

class MainActivity : AppCompatActivity(), KoinComponent {

    private val roadsViewModel: RoadsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initClient()

        setContent {
            RoadsList(roadsViewModel)
        }
    }

    private fun initClient() {
        roadsViewModel.refresh()
    }
}