import UIKit
import SwiftUI
import ComposeApp
import GoogleMaps
import Foundation

struct GoogleMapView: UIViewRepresentable {
    var locationsList: [ComposeApp.LocationModel]
    var locationSelected: ComposeApp.LocationModel?

    func makeUIView(context: Context) -> GMSMapView {
        let options = GMSMapViewOptions()
        let cameraPosition: GMSCameraPosition

        if let location = locationSelected {
            cameraPosition = GMSCameraPosition.camera(withLatitude: location.latitude, longitude: location.longitude, zoom: 15.0)
        } else if let firstLocation = locationsList.first {
            cameraPosition = GMSCameraPosition.camera(withLatitude: firstLocation.latitude, longitude: firstLocation.longitude, zoom: 10.0)
        } else {
            cameraPosition = GMSCameraPosition.camera(withLatitude: 51.50512, longitude: -0.08633, zoom: 10.0)
        }
        options.camera = cameraPosition
        let mapView = GMSMapView(options: options)
        updateMarkers(for: mapView, locationSelected: locationSelected)
        return mapView
    }

    func updateUIView(_ uiView: GMSMapView, context: Context) {
        updateMarkers(for: uiView, locationSelected: locationSelected)

        if let location = locationSelected {
            let cameraPosition = GMSCameraPosition.camera(withLatitude: location.latitude, longitude: location.longitude, zoom: 15.0)
            uiView.animate(to: cameraPosition)
        }
    }

    private func updateMarkers(for mapView: GMSMapView, locationSelected: ComposeApp.LocationModel?) {
        mapView.clear()
        var markerToSelect: GMSMarker?
        for location in locationsList {
            let marker = GMSMarker()
            marker.position = CLLocationCoordinate2D(latitude: location.latitude, longitude: location.longitude)
            marker.title = location.title
            marker.snippet = location.description_
            marker.map = mapView
            if location.id == locationSelected?.id {
                markerToSelect = marker
            }
        }
        mapView.selectedMarker = markerToSelect
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
