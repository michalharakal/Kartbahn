//
//  WarningsProvider.swift
//  kartbahn
//
//  Created by Alexander v. Below on 24.08.21.
//

import SwiftUI

class WarningsProvider: Provider<String> {
}

class WarningsPreviewProvider: WarningsProvider {
    override init() {
        super.init()
        self.storage = ["Stau", "Krokodil", "Mann weiss es nicht"]
    }
}

class WarningsLiveProvider: WarningsProvider {
    init(name: String) {
        super.init()
        
    }
}
