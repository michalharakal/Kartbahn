//
//  RoadsContent.swift
//  kartbahn
//
//  Created by Michal Harakal on 21.08.21.
//

import Foundation
import SwiftUI
import shared

class RoadsContent: ObservableObject {
    
     let viewModel = RoadsViewModel.Companion.init().create()
        
    @Published var roadsState = RoadsViewModelDataKt.createDefaultRoadsViewModelData()
    
    func fetch() {
        let roadsStateCommonFlow = viewModel.getCommonFlowFromIos()
        roadsStateCommonFlow.watch { _roadState in
            if _roadState != nil {
                self.roadsState = _roadState!
                
            }            
        }
    }
}
