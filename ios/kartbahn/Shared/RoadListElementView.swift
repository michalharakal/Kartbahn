//
//  RoadListElementView.swift
//  kartbahn
//
//  Created by Alexander v. Below on 23.08.21.
//

import SwiftUI
import shared

struct RoadListElementDetail: View {
    @State var name: String
    @State var warningsCount: Int
    var body: some View {
        HStack (alignment: .center) {
            AutobahnNumberView(number: name)
            Spacer()

            Text("Verkehrsmeldungen:")
            if warningsCount == 0 {
                Text("Keine")
            } else {
                Text("\(warningsCount)")
            }
        }
    }
}

struct RoadListElementView: View {
    @State var road: RoadViewModelData
    var body: some View {
        let warnings = WarningsLivePublisher(name: road.name)

        NavigationLink(destination: WarningsListView(warningsPublisher: warnings)) {
            RoadListElementDetail(name: road.name, warningsCount: warnings.warnings.count)
        }
    }
}

struct RoadListElementView_Previews: PreviewProvider {
    static var previews: some View {
        RoadListElementView(road: RoadViewModelData(name: "A4", warningsCount: 2))
    }
}
