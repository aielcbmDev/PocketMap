import SwiftUI
import GoogleMaps

@main
struct iOSApp: App {
    init() {
        GMSServices.provideAPIKey("${IOS_MAPS_API_KEY}")
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
