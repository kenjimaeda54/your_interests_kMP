//
//  FavoriteScreen.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 01/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import _MapKit_SwiftUI



@available(iOS 17.0, *)
struct FavoriteScreen: View {
	@Namespace var mapScope
	@StateObject private var locationManager = LocationManager()
	@State private var initialPosition: MapCameraPosition?
	@Environment(\.scenePhase) var scenePhase



	var body: some View {
		GeometryReader { geometry in
			
			VStack {
				
				if(locationManager.anotationMap.lastKnownLocation == nil && locationManager.authorizationStatus != .denied)  {
  					Loading()
  				}
				Group  {
					if(locationManager.authorizationStatus == .authorizedAlways || locationManager.authorizationStatus == .authorizedWhenInUse && locationManager.anotationMap.lastKnownLocation != nil) {
						Map(
							initialPosition: {
								let center = locationManager.anotationMap.lastKnownLocation ?? CLLocationCoordinate2D(latitude: 00.00, longitude: 00.00)
								let span = MKCoordinateSpan(latitudeDelta: 0.020, longitudeDelta: 0.020)
								let region = MKCoordinateRegion(center: center, span: span)
								return .region(region)
							}()
							
						) {
							if(locationManager.anotationMap.lastKnownLocation != nil) {
								Annotation("", coordinate: locationManager.anotationMap.lastKnownLocation!) {
									MapAnotation(anotationMap: locationManager.anotationMap, geometryProxy: geometry)
								}
							}
							
							
						}
						.mapControlVisibility(.hidden)
						//.mapStyle(.hybrid(elevation: .flat,showsTraffic: true))//customizar para mostrar pontos de interesse
						.mapStyle(.imagery)
						.mapScope(mapScope)
					}
					
					if(locationManager.authorizationStatus == .denied) {
						Button(action:{
							
							UIApplication.shared.open(URL(string: UIApplication.openSettingsURLString)!)
							
						}) {
							VStack {
								Text("Voce recusou acesso a Localização 😢.\nPara visualizar o mapa acesse no celular:")
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

