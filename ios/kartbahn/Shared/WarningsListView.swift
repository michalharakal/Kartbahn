//
//  WarningsListView.swift
//  WarningsListView
//
//  Created by Alexander v. Below on 26.08.21.
//

import SwiftUI
import shared

struct WarningsListView: View {
    var body: some View {
        WarningsElementView(warning: WarningViewModelData(warningId: "XYZ", title: "Krokodil", subtitle: "Ein Krokodil auf der A4", start: Kotlinx_datetimeLocalDateTime(year: 2021, month: .august, dayOfMonth: 24, hour: 23, minute: 23, second: 23, nanosecond: 0)))
    }
}

struct WarningsListView_Previews: PreviewProvider {
    static var previews: some View {
        WarningsListView()
    }
}
