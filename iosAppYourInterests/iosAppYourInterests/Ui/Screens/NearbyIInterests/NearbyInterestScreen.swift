//
//  FavoriteScreen.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 01/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import _MapKit_SwiftUI
import shared


@available(iOS 17.0, *)
struct NearbyInterestScreen: View {
	@Namespace var mapScope
	@StateObject private var locationManager = LocationManager()
	@StateObject private var placesNearbyState = NearbyInterestsState()
	@Environment(\.scenePhase) var scenePhase
	@EnvironmentObject private var locationEnvironment: LocationEnvironment
	@EnvironmentObject private var managerTabEnrionment: ManagerTabEnvironment
	@State var originLocation = CLLocationCoordinate2D(latitude: 0.0, longitude: 0.0)
	@State var destinationLocation = CLLocationCoordinate2D(latitude: 0.0, longitude: 0.0)
	@State private var isPresentedNewScreen = false
	@State private var selectedResult: MKMapItem?
	@State private var route: MKRoute?
	@State private var placeSelected: PhotosPlacesWithRelationNearbyModel?
	
	var body: some View {
		
		if(locationManager.anotationMap.lastKnownLocation == nil && locationManager.authorizationStatus != .denied)  {
			ZStack {
				Loading()
			}
			.frame(minWidth: 0 , maxWidth: .infinity, minHeight: 0, maxHeight: .infinity)
			.background(
				ColorsApp.gray.opacity(0.4)
			)
		}else {
			NavigationStack {
				GeometryReader { geometry in
					VStack {
						Group  {
							if(locationManager.authorizationStatus == .authorizedAlways || locationManager.authorizationStatus == .authorizedWhenInUse && locationManager.anotationMap.lastKnownLocation != nil) {
								Map(
									initialPosition: {
										let center = locationManager.anotationMap.lastKnownLocation ?? CLLocationCoordinate2D(latitude: 00.00, longitude: 00.00)
										let span = MKCoordinateSpan(latitudeDelta: 0.003, longitudeDelta: 0.003)
										let region = MKCoordinateRegion(center: center, span: span)
										return .region(region)
									}()
									
								) {
									
									
									if(placesNearbyState.loading == .success) {
										ForEach(placesNearbyState.placesRelationsWithPhoto!,id: \.self) { place in
											
											Annotation("",coordinate: CLLocationCoordinate2D(latitude: place.places.geocode.latitude, longitude: place.places.geocode.longitude)) {
												PlaceAnnotation(place: place,geometryProxy: geometry)
													.onTapGesture {
														
														destinationLocation =  CLLocationCoordinate2D(latitude: place.places.geocode.latitude, longitude: place.places.geocode.longitude)
														selectedResult = MKMapItem(placemark: MKPlacemark(coordinate: destinationLocation))
														getDirections()
														isPresentedNewScreen = true
														placeSelected = place
													}
													
											}
											
										}
									}
									
									
									if(locationManager.anotationMap.lastKnownLocation != nil) {
										Annotation("", coordinate: locationManager.anotationMap.lastKnownLocation!) {
											PinAnnotation(anotationMap: locationManager.anotationMap, geometryProxy: geometry)
												.onAppear {
													originLocation = locationManager.anotationMap.lastKnownLocation!
													locationEnvironment.location = locationManager.anotationMap.lastKnownLocation!
													managerTabEnrionment.isShowTab = true
												}
										}
										
									}
									
									
								}
								.navigationDestination(isPresented: $isPresentedNewScreen) {
									if let route = route, let selectedResult = selectedResult, let placeSelected = placeSelected {
										NavigationRouteScreen(destination: destinationLocation, selectedResult: selectedResult, route: route, placeAnnotaion: placeSelected)
									}
								}
								.mapControlVisibility(.hidden)
								//.mapStyle(.hybrid(elevation: .flat,showsTraffic: true))//customizar para mostrar pontos de interesse
								.mapStyle(.imagery)
								.mapScope(mapScope)
								.task {
									await placesNearbyState.getPlaces(latitude: locationManager.anotationMap.lastKnownLocation!.latitude,
																										longitude: locationManager.anotationMap.lastKnownLocation!.longitude)
								}
								
								
							}//Implementacao com Mapbox , dificuldade em usar navegacoes com ele
							//						Group  {
							//
							//
							//							if(locationManager.authorizationStatus == .authorizedAlways || locationManager.authorizationStatus == .authorizedWhenInUse && locationManager.anotationMap.lastKnownLocation != nil) {
							//
							//								//https://docs.mapbox.com/ios/maps/api/11.6.0/documentation/mapboxmaps/swiftui-user-guide/
							//								//examples mapbox https://github.com/mapbox/mapbox-maps-ios/tree/main/Apps/Examples/Examples/SwiftUI%20Examples
							//								Map(
							//									viewport: $viewport
							//								) {
							//
							//
							//								 MapViewAnnotation(coordinate: locationManager.anotationMap.lastKnownLocation!) {
							//									 PinAnnotation(anotationMap: locationManager.anotationMap, geometryProxy: geometry)
							//
							//								 }
							//								 .allowOverlap(true)
							//
							//								 if(placesNearbyState.loading == .success) {
							//									 ForEvery(placesNearbyState.placesRelationsWithPhoto!) { place in
							//										 MapViewAnnotation(coordinate: CLLocationCoordinate2D(latitude: place.places.geocode.latitude, longitude: place.places.geocode.longitude)) {
							//											 PlaceAnnotation(place: place, geometryProxy: geometry)
							//												 .onTapGesture {
							//													 destinationLocation =  CLLocationCoordinate2D(latitude: place.places.geocode.latitude, longitude: place.places.geocode.longitude)
							//													 isPresentedNewScreen = true
							//												 }
							//										 }
							//										 .allowOverlap(false)
							//									 }
							//								 }
							//
							//							 }
							//							 .mapStyle(MapStyle(uri: StyleURI(url: URL(string: "mapbox://styles/kenjimaeda/clzu6cgv300qe01pd35jr9y81")!)!))
							//							 .ornamentOptions(
							//								OrnamentOptions(
							//									logo: .init(margins: .init(x: -10000, y: 0)) , attributionButton: .init(margins: .init(x: -10000, y: 0)))
							//								)
							//								.task {
							//									await placesNearbyState.getPlaces(latitude: locationManager.anotationMap.lastKnownLocation!.latitude,
							//																										longitude: locationManager.anotationMap.lastKnownLocation!.longitude)
							//
							//								}
							//								.onAppear {
							//									viewport = .camera(center: locationManager.anotationMap.lastKnownLocation,zoom: 16,pitch: 0)
							//									originLocation =  locationManager.anotationMap.lastKnownLocation!
							//								}
							//								.navigationDestination(isPresented: $isPresentedNewScreen) {
							//										 NavigationRouteScreen(destination:  $destinationLocation, origin: $originLocation)
							//								}
							//							}
							//
							//							if(locationManager.authorizationStatus == .denied) {
							//								Button(action:{
							//
							//									UIApplication.shared.open(URL(string: UIApplication.openSettingsURLString)!)
							//
							//								}) {
							//									VStack {
							//										Text("Voce recusou acesso a Localização.\nPara visualizar o mapa acesse no celular:")
							//											.font(.custom(FontsApp.regular , size: 15))
							//											.foregroundStyle(ColorsApp.black) +
							//										Text(" Ajustes > Privacidade  e Segurança >  Serviços de Localização  e iosAppYourInterests.\n")
							//											.font(.custom(FontsApp.bold , size: 15))
							//											.foregroundStyle(ColorsApp.black) +
							//										Text("Se desejar ir direto para Ajustes e só clicar")
							//											.font(.custom(FontsApp.regular , size: 15))
							//											.foregroundStyle(ColorsApp.black)
							//									}
							//									.padding([.horizontal],40)
							//								}
							//							}
							//
							//						}
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
					.environmentObject(locationEnvironment)
					.environmentObject(managerTabEnrionment)
					 
					
				}
				
			}
		}
		
	}
}


@available(iOS 17.0, *)
extension NearbyInterestScreen {
	func getDirections() {
		
		self.route = nil
		guard selectedResult != nil else { return }
		
		
		let request = MKDirections.Request()
		request.source = MKMapItem(placemark: MKPlacemark(coordinate: originLocation))
		request.destination = self.selectedResult
		
		Task {
			do {
				let directions = MKDirections(request: request)
				let response = try await directions.calculate()
				route = response.routes.first
			}catch {
				print("error when fetchin route travel \(error)")
			}
		}
	}
	
}
