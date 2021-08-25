//
//  ContentView.swift
//  Shared
//
//  Created by Michal Harakal on 21.08.21.
//

import SwiftUI
import shared

struct ContentView: View {
    @State var roadsPublisher = RoadsLivePublisher()

    var body: some View {
        NavigationView {
            RoadListView(roads: roadsPublisher)
                .navigationTitle("Autobahnen")
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
