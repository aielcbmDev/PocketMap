import UIKit
import SwiftUI
import ComposeApp
import GoogleMaps
import Foundation

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        return MainViewControllerKt.MainViewController(
            mapUIViewController: { locationsList, locationSelected in
                let swiftLocations = locationsList as? [ComposeApp.LocationModel] ?? []
                let googleMapView = GoogleMapView(locationsList: swiftLocations, locationSelected: locationSelected)
                return UIHostingController(rootView: googleMapView)
            }
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}

struct ContentView: View {
    var body: some View {
        ComposeView()
            .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}
