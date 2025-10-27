import UIKit
import SwiftUI
import ComposeApp
import GoogleMaps
import Foundation

struct GoogleMapView: UIViewRepresentable {
    var locationsList: [ComposeApp.LocationModel]
    var locationSelected: ComposeApp.LocationModel?

    class Coordinator: NSObject, GMSMapViewDelegate {
        func mapView(_ mapView: GMSMapView, idleAt position: GMSCameraPosition) {
            UserDefaults.standard.set(position.target.latitude, forKey: "lastLatitude")
            UserDefaults.standard.set(position.target.longitude, forKey: "lastLongitude")
            UserDefaults.standard.set(position.zoom, forKey: "lastZoom")
        }
    }

    func makeCoordinator() -> Coordinator {
        Coordinator()
    }

    func makeUIView(context: Context) -> GMSMapView {
        let options = GMSMapViewOptions()
        let cameraPosition: GMSCameraPosition

        if let location = locationSelected {
            cameraPosition = GMSCameraPosition.camera(withLatitude: location.latitude, longitude: location.longitude, zoom: 15.0)
        } else {
            let lastLatitude = UserDefaults.standard.double(forKey: "lastLatitude")
            let lastLongitude = UserDefaults.standard.double(forKey: "lastLongitude")
            let lastZoom = UserDefaults.standard.float(forKey: "lastZoom")

            if lastLatitude != 0 && lastLongitude != 0 {
                let zoom: Float = lastZoom > 0 ? lastZoom : 10.0
                cameraPosition = GMSCameraPosition.camera(withLatitude: lastLatitude, longitude: lastLongitude, zoom: zoom)
            } else {
                cameraPosition = GMSCameraPosition.camera(withLatitude: 51.50512, longitude: -0.08633, zoom: 10.0)
            }
        }
        options.camera = cameraPosition

        let mapView = GMSMapView(options: options)
        mapView.delegate = context.coordinator

        displayMarkers(for: mapView, locationSelected: locationSelected)
        return mapView
    }

    func updateUIView(_ uiView: GMSMapView, context: Context) {
        displayMarkers(for: uiView, locationSelected: locationSelected)

        if let location = locationSelected {
            let cameraPosition = GMSCameraPosition.camera(withLatitude: location.latitude, longitude: location.longitude, zoom: 15.0)
            uiView.animate(to: cameraPosition)
        }
    }

    private func displayMarkers(for mapView: GMSMapView, locationSelected: ComposeApp.LocationModel?) {
        for location in locationsList {
            let marker = GMSMarker()
            marker.position = CLLocationCoordinate2D(latitude: location.latitude, longitude: location.longitude)
            marker.title = location.title
            marker.snippet = location.description_
            marker.map = mapView
            if location.id == locationSelected?.id {
                mapView.selectedMarker = marker
            }
        }
    }
}

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        return MainViewControllerKt.MainViewController(
            mapUIViewController: { locationsList, locationSelected in
                let swiftLocations = locationsList as? [ComposeApp.LocationModel] ?? []
                return UIHostingController(rootView: GoogleMapView(locationsList: swiftLocations, locationSelected: locationSelected))
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
