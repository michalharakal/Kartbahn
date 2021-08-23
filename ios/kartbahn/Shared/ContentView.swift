//
//  ContentView.swift
//  Shared
//
//  Created by Michal Harakal on 21.08.21.
//

import SwiftUI
import shared

struct ContentView: View {

    var body: some View {
        NavigationView {
            RoadListView(roads: RoadsLiveViewModel())
                .navigationTitle("Autobahnen")
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
