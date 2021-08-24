//
//  RoadListElementView.swift
//  kartbahn
//
//  Created by Alexander v. Below on 23.08.21.
//

import SwiftUI

struct RoadListElementView: View {
    @State var name: String
    @ObservedObject var warningsProvider: WarningsProvider
    var body: some View {
        HStack (alignment: .center) {
            AutobahnNumberView(number: name)
            Spacer()
            
            Text("Verkehrsmeldungen:")
            if warningsProvider.count == 0 {
                Text("Keine")
            } else {
                Text("\(warningsProvider.count)")
            }
        }
    }
}

struct RoadListElementView_Previews: PreviewProvider {
    static var previews: some View {
        let previewData = WarningsPreviewProvider()
        RoadListElementView(name: "A4", warningsProvider: previewData)
    }
}
