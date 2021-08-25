//
//  RoadsContent.swift
//  kartbahn
//
//  Created by Michal Harakal on 21.08.21.
//

import SwiftUI
import Combine
import shared

class Provider<T>: ObservableObject, RandomAccessCollection {
    typealias ArrayType = Array<T>
    typealias Index = ArrayType.Index
    typealias Element = ArrayType.Element

    internal var storage = ArrayType()

    var startIndex: Index { return storage.startIndex }
    var endIndex: Index { return storage.endIndex }
    func index(after i: Index) -> Index {
        return storage.index(after: i)
    }

    subscript(index: Index) -> ArrayType.Element {
        get { return storage[index] }
    }
}

class RoadsPublisher: ObservableObject {
    @Published var roads = [RoadViewModelData]()
}

class RoadsPreviewPublisher: RoadsPublisher {
    override init() {
        super.init()
        
        roads = [RoadViewModelData(name: "A1",
                               warnings: [WarningViewModelData(name: "This is a warning")]),
             RoadViewModelData(name: "A4", warnings: []),
             RoadViewModelData(name: "A61", warnings: []),
             RoadViewModelData(name: "A555", warnings: [])]
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
