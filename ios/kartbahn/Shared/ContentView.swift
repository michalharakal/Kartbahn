//
//  ContentView.swift
//  Shared
//
//  Created by Michal Harakal on 21.08.21.
//

import SwiftUI
import common


struct ContentView: View {
    
    @ObservedObject private var roadsStateViewModel = RoadsContent()
    
    var body: some View {
        Text("Hello, world!")
            .padding()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
