//
//  ContentView.swift
//  Shared
//
//  Created by Michal Harakal on 21.08.21.
//

import SwiftUI
import shared

struct ContentView: View {
         
    @ObservedObject private var roadsStateViewModel = RoadsContent()
    
    var body: some View {
        VStack {
            List(roadsStateViewModel.roadsState.roads, id:\.name) { road in
                        VStack (alignment: .leading) {
                            Text(road.name)
                        }
                  } // List
                  Spacer()
                
    }.onAppear {
    self.roadsStateViewModel.fetch()
    }
}
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
