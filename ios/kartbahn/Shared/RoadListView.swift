//
//  RoadListView.swift
//  kartbahn
//
//  Created by Alexander v. Below on 23.08.21.
//

import SwiftUI
import shared

struct RoadListView: View {
    @ObservedObject var roads: RoadsPublisher
    var body: some View {
        VStack {
            List(roads.roads, id:\.name) { road in
                RoadListElementView(name: road.name, warningsProvider: WarningsPreviewProvider())
            }
            Spacer()
        }
    }
}

struct RoadListView_Previews: PreviewProvider {
    static var previews: some View {
        RoadListView(roads: RoadsPreviewPublisher())
    }
}
