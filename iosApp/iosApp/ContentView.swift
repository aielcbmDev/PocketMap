import UIKit
import SwiftUI
import ComposeApp
import GoogleMaps
import Foundation

struct LocationModel: Codable {
    let id: Int64
    let title: String
    let description: String?
    let latitude: Double
    let longitude: Double
}

struct GoogleMapView: UIViewRepresentable {
    var locationsJson: String

    func makeUIView(context: Context) -> GMSMapView {
        let locations = decodeLocations()
        let options = GMSMapViewOptions()
        options.camera = GMSCameraPosition.camera(withLatitude: 51.50512, longitude: -0.08633, zoom: 10.0)

        let mapView = GMSMapView(options: options)

        mapView.clear()
        // Creates a marker for each location.
        for location in locations {
            let marker = GMSMarker()
            marker.position = CLLocationCoordinate2D(latitude: location.latitude, longitude: location.longitude)
            marker.title = location.title
            marker.snippet = location.description
            marker.map = mapView
        }

        return mapView
    }

    func updateUIView(_ uiView: GMSMapView, context: Context) {
        // In a real app you might want to update the markers
        // if the locationsJson changes.
    }

    private func decodeLocations() -> [LocationModel] {
        guard let data = locationsJson.data(using: .utf8) else { return [] }
        do {
            let decoder = JSONDecoder()
            return try decoder.decode([LocationModel].self, from: data)
        } catch {
            print("Error decoding locations: \(error)")
            return []
        }
    }
}

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController(
            mapUIViewController: { (locationsJson) -> UIViewController in
                return UIHostingController(rootView: GoogleMapView(locationsJson: locationsJson))
            }
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}
