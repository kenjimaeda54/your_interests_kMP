//
//  FavoriteScreen.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 01/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import MapboxMaps



@available(iOS 17.0, *)
struct NearbyInterestScreen: View {
	@Namespace var mapScope
	@StateObject private var locationManager = LocationManager()
	@StateObject private var placesNearbyState = NearbyInterestsState()
	@Environment(\.scenePhase) var scenePhase
	
	
	
	var body: some View {
		GeometryReader { geometry in
			
			VStack {
				
				if(locationManager.anotationMap.lastKnownLocation == nil && locationManager.authorizationStatus != .denied)  {
					Loading()
				}
				Group  {
					if(locationManager.authorizationStatus == .authorizedAlways || locationManager.authorizationStatus == .authorizedWhenInUse && locationManager.anotationMap.lastKnownLocation != nil) {
				
						//https://docs.mapbox.com/ios/maps/api/11.6.0/documentation/mapboxmaps/swiftui-user-guide/
						//https://stackoverflow.com/questions/38905767/how-to-remove-info-button-on-ios-mapbox
						Map(
						initialViewport: .camera(center: locationManager.anotationMap.lastKnownLocation,zoom: 17)
					 
					 ) {
						 
 
						 MapViewAnnotation(coordinate: locationManager.anotationMap.lastKnownLocation!) {
							 PinAnnotation(anotationMap: locationManager.anotationMap, geometryProxy: geometry)

						 }
						 .allowOverlap(true)
						 
						 if(placesNearbyState.loading == .success) {
							 ForEvery(placesNearbyState.placesRelationsWithPhoto!) { place in
								 MapViewAnnotation(coordinate: CLLocationCoordinate2D(latitude: place.places.geocode.latitude, longitude: place.places.geocode.longitude)) {
									 PlaceAnnotation(place: place, geometryProxy: geometry)
								 }
								 .allowOverlap(false)
							 }
						 }
 					 }
					 .mapStyle(MapStyle(uri: StyleURI(url: URL(string: "mapbox://styles/kenjimaeda/clzu6cgv300qe01pd35jr9y81")!)!))
						.task {
							await placesNearbyState.getPlaces(latitude: locationManager.anotationMap.lastKnownLocation!.latitude,
																								longitude: locationManager.anotationMap.lastKnownLocation!.longitude)
						}				
					}
					
					if(locationManager.authorizationStatus == .denied) {
						Button(action:{
							
							UIApplication.shared.open(URL(string: UIApplication.openSettingsURLString)!)
							
						}) {
							VStack {
								Text("Voce recusou acesso a Localização �.\nPara visualizar o mapa acesse no celular:")
									.font(.custom(FontsApp.regular , size: 15))
									.foregroundStyle(ColorsApp.black) +
								Text(" Ajustes > Privacidade  e Segurança >  Serviços de Localização  e iosAppYourInterests.\n")
									.font(.custom(FontsApp.bold , size: 15))
									.foregroundStyle(ColorsApp.black) +
								Text("Se desejar ir direto para Ajustes e só clicar")
									.font(.custom(FontsApp.regular , size: 15))
									.foregroundStyle(ColorsApp.black)
							}
							.padding([.horizontal],40)
						}
					}
					
				}
			}
			.frame(minWidth: 0 , maxWidth: .infinity, minHeight: 0, maxHeight: .infinity)
			.background(
				ColorsApp.gray.opacity(0.4)
			)
			.onChange(of: scenePhase) {_, newPhase in
				
				//para lidar com ciclo de viida sempre que retornar a tetla esse codigo e chamado
				//https://www.hackingwithswift.com/books/ios-swiftui/how-to-be-notified-when-your-swiftui-app-moves-to-the-background
				if(newPhase == .active) {
					locationManager.requestLocationPermission()
				}
				
			}
			
		}
		.ignoresSafeArea()
		
	}
}
