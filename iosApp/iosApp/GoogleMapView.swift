import UIKit
import SwiftUI
import ComposeApp
import GoogleMaps
import Foundation

struct GoogleMapView: UIViewRepresentable {
    var locationsList: [ComposeApp.LocationModel]
    var locationSelected: ComposeApp.LocationModel?
    var onMarkerClick: (ComposeApp.LocationModel) -> Void

    class Coordinator: NSObject, GMSMapViewDelegate {
        var parent: GoogleMapView

        init(_ parent: GoogleMapView) {
            self.parent = parent
        }

        func mapView(_ mapView: GMSMapView, idleAt position: GMSCameraPosition) {
            UserDefaults.standard.set(position.target.latitude, forKey: "lastLatitude")
            UserDefaults.standard.set(position.target.longitude, forKey: "lastLongitude")
            UserDefaults.standard.set(position.zoom, forKey: "lastZoom")
        }

        func mapView(_ mapView: GMSMapView, didTap marker: GMSMarker) -> Bool {
            if let location = marker.userData as? ComposeApp.LocationModel {
                parent.onMarkerClick(location)
            }
            return false
        }
    }

    func makeCoordinator() -> Coordinator {
        Coordinator(self)
    }

    func makeUIView(context: Context) -> GMSMapView {
        let options = GMSMapViewOptions()
        options.camera = createCameraPosition()

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

    private func createCameraPosition() -> GMSCameraPosition {
        if let location = locationSelected {
            return GMSCameraPosition.camera(withLatitude: location.latitude, longitude: location.longitude, zoom: 15.0)
        }

        let lastLatitude = UserDefaults.standard.double(forKey: "lastLatitude")
        let lastLongitude = UserDefaults.standard.double(forKey: "lastLongitude")
        let lastZoom = UserDefaults.standard.float(forKey: "lastZoom")

        if lastLatitude != 0 && lastLongitude != 0 {
            let zoom: Float = lastZoom > 0 ? lastZoom : 10.0
            return GMSCameraPosition.camera(withLatitude: lastLatitude, longitude: lastLongitude, zoom: zoom)
        }

        if let firstLocation = locationsList.first {
            return GMSCameraPosition.camera(withLatitude: firstLocation.latitude, longitude: firstLocation.longitude, zoom: 10.0)
        }

        return GMSCameraPosition.camera(withLatitude: 51.50512, longitude: -0.08633, zoom: 10.0)
    }

    private func displayMarkers(for mapView: GMSMapView, locationSelected: ComposeApp.LocationModel?) {
        mapView.clear()
        for location in locationsList {
            let marker = GMSMarker()
            marker.position = CLLocationCoordinate2D(latitude: location.latitude, longitude: location.longitude)
            marker.title = location.title
            marker.snippet = location.description_
            marker.userData = location
            marker.map = mapView
            if location.id == locationSelected?.id {
                mapView.selectedMarker = marker
            }
        }
    }
}
