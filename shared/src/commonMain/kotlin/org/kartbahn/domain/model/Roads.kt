package org.kartbahn.domain.model

data class RoadWork (val name:String)

data class Road (val name:String, val roadWorks:List<RoadWork>)

data class Roads (val roads: List<Road>)