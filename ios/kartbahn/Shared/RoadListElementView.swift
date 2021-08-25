//
//  RoadListElementView.swift
//  kartbahn
//
//  Created by Alexander v. Below on 23.08.21.
//

import SwiftUI
import shared

struct RoadListElementView: View {
    @State var road: RoadViewModelData
    var body: some View {
        HStack (alignment: .center) {
            AutobahnNumberView(number: road.name)
            Spacer()
            
            Text("Verkehrsmeldungen:")
            if road.warningsCount == 0 {
                Text("Keine")
            } else {
                Text("\(road.warningsCount)")
            }
        }
    }
}

struct RoadListElementView_Previews: PreviewProvider {
    static var previews: some View {
        RoadListElementView(road: RoadViewModelData(name: "A4", warningsCount: 2))
    }
}
