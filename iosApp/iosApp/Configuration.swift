//
//  Configuration.swift
//  iosApp
//
//  Created by Charly Baquero on 02/09/2025.
//

import Foundation

enum Configuration {
    
    // MARK: - Public API
    
    static var getGoogleMapsApi: String {
        string(for: "IOS_MAPS_API_KEY")
    }

    // MARK: - Helper Methods

    static private func string(for key: String) -> String {
        Bundle.main.infoDictionary?[key] as! String
    }
}
