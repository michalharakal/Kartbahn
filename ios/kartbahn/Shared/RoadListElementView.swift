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
        VStack (alignment: .leading) {
            Text(name)
        }
    }
}

struct RoadListElementView_Previews: PreviewProvider {
    static var previews: some View {
        RoadListElementView(name: "A4")
    }
}
