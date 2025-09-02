import SwiftUI
import GoogleMaps

@main
struct iOSApp: App {
    init() {
        var mapApiKey: String = Configuration.getGoogleMapsApi
        GMSServices.provideAPIKey(mapApiKey)
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
