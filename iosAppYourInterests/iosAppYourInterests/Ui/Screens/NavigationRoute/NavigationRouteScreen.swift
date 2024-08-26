//
//  NavigationRouteScreen.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 17/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import _MapKit_SwiftUI
import shared

@available(iOS 17.0, *)
struct NavigationRouteScreen: View {
	@StateObject private var locationManager = LocationManager()
	@EnvironmentObject private var tabEnviroment: ManagerTabEnvironment
	@State private var position: MapCameraPosition = .automatic //autommatico vai fazer com qque o zom fica o sufficente conforme a linah tracada
	var destination: CLLocationCoordinate2D
	var selectedResult: MKMapItem
	var route: MKRoute
	var placeAnnotaion: PhotosPlacesWithRelationNearbyModel
	
    var body: some View {
			GeometryReader { geometry in
				if((locationManager.anotationMap.lastKnownLocation) != nil) {
					Map(
						position: $position
					) {
						UserAnnotation()
							
						
						MapPolyline(route)
							.stroke(ColorsApp.green, lineWidth: 5)
						
						Annotation("", coordinate: CLLocationCoordinate2D(latitude: placeAnnotaion.places.geocode.latitude, longitude: placeAnnotaion.places.geocode.longitude)) {
							 PlaceAnnotation(place: placeAnnotaion, geometryProxy: geometry)
						}
					}
					.mapStyle(.hybrid(elevation: .realistic,pointsOfInterest: .excludingAll,showsTraffic: true))
					.onAppear {
						locationManager.requestUpdateLocation()
						withAnimation {
							position = .automatic
						}
					}
				}  else {
					ZStack {
						Loading()
					}
					.frame(minWidth: 0 , maxWidth: .infinity, minHeight: 0, maxHeight: .infinity)
					.background(
						ColorsApp.gray.opacity(0.4)
					)
				}
			}
			.ignoresSafeArea(.all)
			.navigationBarBackButtonHidden()
			.onAppear {
				tabEnviroment.isShowTab = false
			}
		
		 .frame(minWidth: 0, maxWidth: .infinity,minHeight: 0, maxHeight: .infinity)
		}
		
		
}


 
