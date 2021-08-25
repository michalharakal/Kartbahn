//
//  RoadsPublisher.swift
//  kartbahn
//
//  Created by Michal Harakal on 21.08.21.
//

import SwiftUI
import Combine
import shared

class RoadsPublisher: ObservableObject {
    @Published var roads = [RoadViewModelData]()
}

class RoadsPreviewPublisher: RoadsPublisher {
    override init() {
        super.init()
        
        roads = [RoadViewModelData(name: "A1",
                                   warningsCount: 4),
             RoadViewModelData(name: "A4", warningsCount: 2),
             RoadViewModelData(name: "A61", warningsCount: 0),
             RoadViewModelData(name: "A555", warningsCount: 1)]
    }
}

class RoadsLivePublisher: RoadsPublisher {
    override init() {
        super.init()
        let viewModel = RoadsViewModel.Companion.init().create()
        let roadsStateCommonFlow = viewModel.getCommonFlowFromIos()

        roadsStateCommonFlow.watch { roadState in
            if let roadState = roadState {
                self.objectWillChange.send()
                self.roads = roadState.roads
            }
        }
    }
}
