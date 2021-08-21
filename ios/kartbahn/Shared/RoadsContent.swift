//
//  RoadsContent.swift
//  kartbahn
//
//  Created by Michal Harakal on 21.08.21.
//

import Foundation
import common

class RoadsContent: ObservableObject {
    
    let viewModel = RoadsViewModel(repository: KartbahnRepository())
        
    @Published var roadsState = RoadsViewModelDataKt.createDefaultRoadsViewModelData()
    
    func fetch() {
        let roadsStateCommonFlow = viewModel.getCommonFlowFromIos()
        roadsStateCommonFlow.watch { _roadsState in
            if _roadsState != nil {
                self.roadsState = _roadsState!
            }
        }
    }
}
