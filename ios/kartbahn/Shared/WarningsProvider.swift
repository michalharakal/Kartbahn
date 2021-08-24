//
//  WarningsProvider.swift
//  kartbahn
//
//  Created by Alexander v. Below on 24.08.21.
//

import SwiftUI

class WarningsProvider: ObservableObject, RandomAccessCollection {
    typealias ArrayType = Array<String>
    typealias Index = ArrayType.Index
    typealias Element = ArrayType.Element

    internal var storage = ArrayType()

    var startIndex: Index { return storage.startIndex }
    var endIndex: Index { return storage.endIndex }

    subscript(index: Index) -> ArrayType.Element {
        get { return storage[index] }
    }

    func index(after i: Index) -> Index {
        return storage.index(after: i)
    }
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
