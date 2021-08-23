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
        RoadListView(roads: RoadsLiveViewModel())
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
