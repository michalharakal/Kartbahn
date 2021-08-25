//
//  WarningsListView.swift
//  WarningsListView
//
//  Created by Alexander v. Below on 26.08.21.
//

import SwiftUI
import shared

struct WarningsListView: View {
    @ObservedObject var warningsPublisher: WarningsPublisher
    var body: some View {
        List(warningsPublisher.warnings, id:\.warningId) { warning in
            WarningsElementView(warning: warning)
        }
    }
}

struct WarningsListView_Previews: PreviewProvider {
    static var previews: some View {
        WarningsListView(warningsPublisher: WarningsPreviewPublisher())
    }
}
