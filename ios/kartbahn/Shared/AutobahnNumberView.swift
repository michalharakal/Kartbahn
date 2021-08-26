//
//  AutobahnNumberView.swift
//  kartbahn
//
//  Created by Alexander v. Below on 24.08.21.
//

import SwiftUI

struct AutobahnNumberView: View {
    var number: String
    var body: some View {
        Image("Zeichen_405")
            .resizable()
            .scaledToFit()
            .frame(width: 50)
            .overlay(
                Text(number)
                    .foregroundColor(.white)
            )
    }
}

struct AutobahnNumberView_Previews: PreviewProvider {
    static var previews: some View {
        AutobahnNumberView(number: "4")
    }
}
