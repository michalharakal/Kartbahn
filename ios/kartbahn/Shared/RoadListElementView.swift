//
//  RoadListElementView.swift
//  kartbahn
//
//  Created by Alexander v. Below on 23.08.21.
//

import SwiftUI

struct RoadListElementView: View {
    @State var name: String
    var body: some View {
        HStack (alignment: .center) {
            AutobahnNumberView(number: name)
            Spacer()
            Text("Verkehrsmeldungen: 1")
        }
    }
}

struct RoadListElementView_Previews: PreviewProvider {
    static var previews: some View {
        RoadListElementView(name: "A4")
    }
}
