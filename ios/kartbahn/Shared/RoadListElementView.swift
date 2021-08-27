//
//  RoadListElementView.swift
//  kartbahn
//
//  Created by Alexander v. Below on 23.08.21.
//

import SwiftUI
import shared

struct RoadListElementDetail: View {
    var name: String
    var count: Int
    var body: some View {
        HStack (alignment: .center) {
            AutobahnNumberView(number: name)
            Spacer()

            Text("Verkehrsmeldungen:")
            if count == 0 {
                Text("Keine")
            } else {
                Text("\(count)")
            }
        }
    }
}

struct RoadListElementView: View {
    let road: RoadViewModelData
    @StateObject private var warningsProvider: WarningsPublisher

    init(road: RoadViewModelData) {
        self.road = road
        _warningsProvider = StateObject(wrappedValue: WarningsLivePublisher(name: road.name))
    }
    var body: some View {

        NavigationLink(destination: WarningsListView(warningsPublisher: warningsProvider)) {
            RoadListElementDetail(name: road.name, count: warningsProvider.warnings.count)
        }
    }
}

struct RoadListElementView_Previews: PreviewProvider {
    static var previews: some View {
        RoadListElementView(road: RoadViewModelData(name: "A4", warningsCount: 2))
    }
}
