//
//  LocationManager.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 01/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

import Foundation
import CoreLocation
import SwiftUI
import MapKit

//usando koltin nao funcionava
struct AnotationMap {
	var street: String
	var streetNumber: String
	var lastKnownLocation: CLLocationCoordinate2D?
	
}

final class LocationManager: NSObject, CLLocationManagerDelegate, ObservableObject {
	
	private let locationManager = CLLocationManager()
	@Published var authorizationStatus: CLAuthorizationStatus = .notDetermined
	@Published var  anotationMap: AnotationMap = .init(street: "", streetNumber: "", lastKnownLocation: nil )
	
	override init() {
		super.init()
		locationManager.delegate = self
		locationManager.desiredAccuracy = kCLLocationAccuracyBest
		locationManager.requestLocation()
		//para requesitar sempre pode usar o startUpdatingLocation()
	}
	
	
	
	func requestLocationPermission() {
		//precisa no info plist as permissoes
		//Privacy - Location When In Use Usage Description
		locationManager.requestWhenInUseAuthorization()
	}
	
	func locationManagerDidChangeAuthorization(_ manager: CLLocationManager) {
		authorizationStatus = manager.authorizationStatus
		if(manager.authorizationStatus == .authorizedWhenInUse || manager.authorizationStatus == .authorizedAlways) {
			locationManager.requestLocation()
		}
	}
	
	func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
		print("error:: \(error.localizedDescription)")
	}
	
	func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
		self.anotationMap.lastKnownLocation = CLLocationCoordinate2D(
			latitude: locations.first?.coordinate.latitude ?? 00.00,
			longitude: locations.first?.coordinate.longitude ?? 00.00
		)
		let location = CLLocation(latitude: locations.first?.coordinate.latitude ?? 00.00, longitude: locations.first?.coordinate.longitude ?? 00.00)
		let geocoder = CLGeocoder()
		geocoder.reverseGeocodeLocation(location) { placemarks, error in
			
			guard error == nil else {
				print("Problem with the data received from geocoder,\(error.debugDescription)")
				return
			}
			
			
			
			if(placemarks!.isEmpty) {
				print("Problem with the data receivved from geocoder,\(error.debugDescription)")
				return
			}
			
			let placemark = placemarks![0]
			
			self.anotationMap.streetNumber = placemark.subThoroughfare ?? ""
			self.anotationMap.street = placemark.thoroughfare ?? ""
			
		}
		
	}
}
