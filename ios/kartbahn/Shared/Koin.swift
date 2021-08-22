//
//  Koin.swift
//  kartbahn
//
//  Created by Michal Harakal on 22.08.21.
//

import Foundation

import Foundation
import shared

func startKoin() {
    // You could just as easily define all these dependencies in Kotlin, but this helps demonstrate how you might pass platform-specific dependencies in a larger scale project where declaring them in Kotlin is more difficult, or where they're also used in iOS-specific code.
    
    let doOnStartup = { NSLog("Hello from iOS/Swift!") }
    
    let koinApplication = KoinIOSKt.doInitKoinIos()
    _koin = koinApplication.koin
}

private var _koin: Koin_coreKoin? = nil
var koin: Koin_coreKoin {
    return _koin!
}
