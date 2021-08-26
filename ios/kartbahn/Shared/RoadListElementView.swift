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
    @ObservedObject var warningsData: WarningsPublisher
    var body: some View {
        HStack (alignment: .center) {
            AutobahnNumberView(number: name)
            Spacer()

            Text("Verkehrsmeldungen:")
            if warningsData.warnings.count == 0 {
                Text("Keine")
            } else {
                Text("\(warningsData.warnings.count)")
            }
        }
    }
}

struct RoadListElementView: View {
    let road: RoadViewModelData
    var body: some View {
        let warnings = WarningsLivePublisher(name: road.name)

        NavigationLink(destination: WarningsListView(warningsPublisher: warnings)) {
            RoadListElementDetail(name: road.name, warningsData: warnings)
        }
    }
}

struct RoadListElementView_Previews: PreviewProvider {
    static var previews: some View {
        RoadListElementView(road: RoadViewModelData(name: "A4", warningsCount: 2))
    }
}
