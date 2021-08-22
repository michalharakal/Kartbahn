package org.kartbahn.presentation.model

data class RoadViewModelData(val name: String)
data class RoadsViewModelData(val roads: List<RoadViewModelData>)

fun createDefaultRoadsViewModelData() = RoadsViewModelData(emptyList())
