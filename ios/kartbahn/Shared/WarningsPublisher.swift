//
//  WarningsPublisher.swift
//  kartbahn
//
//  Created by Alexander v. Below on 24.08.21.
//

import SwiftUI
import shared

class WarningsPublisher: ObservableObject {
    @Published var warnings = [WarningViewModelData]()
}

class WarningsPreviewPublisher: WarningsPublisher {
    override init() {
        super.init()
        self.warnings = [WarningViewModelData(warningId: "1", title: "Störung", subtitle: "Eine Störung", start: Kotlinx_datetimeLocalDateTime(year: 2021, month: Kotlinx_datetimeMonth.august, dayOfMonth: 25, hour: 23, minute: 52, second: 23, nanosecond: 0))]
    }
}

class WarningsLivePublisher: WarningsPublisher {
    init(name: String) {
        super.init()
        let viewModel = WarningsViewModel.Companion.init().create(roadId: name)
        let warningsStateCommonFlow = viewModel.getCommonFlowFromIos(roadId: name)

        warningsStateCommonFlow.watch { warningState in
            if let warningState = warningState {
                self.warnings = warningState.warnings
            }
        }
    }
}
