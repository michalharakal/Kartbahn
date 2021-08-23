//
//  RoadListView.swift
//  kartbahn
//
//  Created by Alexander v. Below on 23.08.21.
//

import SwiftUI

struct RoadListView: View {
    @ObservedObject var roads: SwiftRoadsViewModel
    var body: some View {
        VStack {
            List(roads, id:\.name) { road in
                RoadListElementView(name: road.name)
            }
            Spacer()
        }
    }
}

struct RoadListView_Previews: PreviewProvider {
    static var previews: some View {
        let sampleRoads = RoadsPreviewModel()
        RoadListView(roads: sampleRoads)
    }
}
