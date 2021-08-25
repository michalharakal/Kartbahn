//
//  WarningsElementView.swift
//  WarningsElementView
//
//  Created by Alexander v. Below on 26.08.21.
//

import SwiftUI
import shared

struct WarningsElementView: View {
    @State var warning: WarningViewModelData
    var body: some View {
        VStack {
            Text(self.warning.title).font(.title)
            Text(self.warning.subtitle).font(.subheadline)
        }
    }
}

struct WarningsElementView_Previews: PreviewProvider {
    static var previews: some View {
        WarningsElementView(warning: WarningViewModelData(warningId: "XYZ", title: "Krokodil", subtitle: "Ein Krokodil auf der A4", start: Kotlinx_datetimeLocalDateTime(year: 2021, month: .august, dayOfMonth: 24, hour: 23, minute: 23, second: 23, nanosecond: 0)))
    }
}
