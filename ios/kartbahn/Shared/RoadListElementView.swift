//
//  RoadListElementView.swift
//  kartbahn
//
//  Created by Alexander v. Below on 23.08.21.
//

import SwiftUI
import shared

struct RoadListElementDetail: View {
    let name: String
    let count: Int
    var body: some View {
        HStack (alignment: .center) {
            AutobahnNumberView(number: name)

            if count == 0 {
                Text("Keine Meldungen")
            } else {
                Text("Meldungen: \(count)")
            }
            Spacer()
        }
    }
}

struct RoadListElementView: View {
    let road: RoadViewModelData
    @ObservedObject var warningsProvider: WarningsPublisher

    var body: some View {

        let content = RoadListElementDetail(name: road.name, count: warningsProvider.warnings.count)
        if warningsProvider.warnings.count == 0 {
            content
        } else {
            NavigationLink(destination: WarningsListView(warningsPublisher: warningsProvider)) {
                content
            }
        }
    }
}

struct RoadListElementView_Previews: PreviewProvider {
    static var previews: some View {
        let roadModel = RoadViewModelData(name: "A4", warningsCount: 2)
        RoadListElementView(road: roadModel, warningsProvider: WarningsPreviewPublisher())
    }
}
