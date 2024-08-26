//
//  DetailsPlaceScreen.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 25/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared
import MapKit

struct DetailsPlaceScreen: View {
	let place: PhotosPlacesWithRelationNearbyModel
	var span: CLLocationDegrees = 0.01
	var location: CLLocationCoordinate2D {
		return CLLocationCoordinate2D(latitude: place.places.geocode.latitude, longitude: place.places.geocode.longitude)
	}
	@Binding  var selectedResult:  MKMapItem?
	@Binding  var route: MKRoute?
	@Binding var isShowMap: Bool
	@EnvironmentObject private var tabEnviroment: ManagerTabEnvironment
	@EnvironmentObject private var locationEnviroment: LocationEnvironment
	
	
	var body: some View {
		GeometryReader { geometry in
			
			VStack {
				AsyncImage(url: URL(string: place.photoPlacesModel.icon)) { phase in
					
					phase
						.resizable()
						.frame(height: geometry.size.height * 0.3)
					
					
				} placeholder: {
					Image("imageNotFound")
						.resizable()
						.frame(height: geometry.size.height * 0.3)
					
				}
				VStack(alignment: .leading,spacing: 25){
					Text(place.places.name)
						.padding([.bottom],30)
						.font(.custom(FontsApp.semiBold, size: 20))
						.foregroundStyle(ColorsApp.black)
						.multilineTextAlignment(.leading)
					Divider()
						.background(ColorsApp.black.opacity(0.7))
						.frame(minHeight: 1)
					InformationItem(title: "Endereço", subTitle: place.places.address){
						Image("pin")
							.resizable()
							.renderingMode(.template)
							.foregroundStyle(ColorsApp.blue)
							.frame(width: 30,height: 30)
						
					}
					InformationItem(title: "Distancia", subTitle: formatDistance(distance: Int(place.places.distance))){
						Image("distance")
							.resizable()
							.renderingMode(.template)
							.foregroundStyle(ColorsApp.blue)
							.frame(width: 30,height: 30)
					}
					
					AsyncImage(url: URL(string: "https://api.mapbox.com/styles/v1/mapbox/streets-v10/static/pin-s-l+3C6E71(\(place.places.geocode.longitude),\(place.places.geocode.latitude)/\(place.places.geocode.longitude),\(place.places.geocode.latitude),14/600x600?access_token=sk.eyJ1Ijoia2VuamltYWVkYSIsImEiOiJjbTA2bWJ0c3QwNXpmMmxvcWtvaHRnZ2pwIn0.aMeRjYcDf3sJuaPYRNGqZw")) { image in
						
						image
							.resizable()
							.frame(height: geometry.size.height * 0.3)
							.clipShape(RoundedRectangle(cornerRadius: 10))
							.onTapGesture {
								//verificar se tem mais alguma coisa
								selectedResult = MKMapItem(placemark: MKPlacemark(coordinate: CLLocationCoordinate2D(latitude: place.places.geocode.latitude, longitude: place.places.geocode.longitude)))
								getDirections()
								withAnimation(.easeInOut) {
									isShowMap.toggle()
								}
							}
						
						
					}placeholder: {
						Image("imageNotFound")
							.resizable()
							.frame(height: geometry.size.height * 0.3)
							.clipShape(RoundedRectangle(cornerRadius: 10))
							.redactShimmer(condition: true)
					}
					
				}
				.padding([.horizontal],13)
				.padding([.top],33)
				.frame(minWidth: 0,maxWidth: .infinity, minHeight: 0, maxHeight: .infinity,alignment: .topTrailing)
				
				
			}
			.ignoresSafeArea(edges: .top)
			.background(ColorsApp.gray.opacity(0.4))
			.safeAreaInset(edge: .top) {
				ButtonBackTopAppBar()
			}
			.onAppear {
				tabEnviroment.isShowTab = false
			}
			.environmentObject(ManagerTabEnvironment())
			.environmentObject(LocationEnvironment())
			
		}
		
	}
}






extension DetailsPlaceScreen  {
	
	
	func getDirections() {
		
		self.route = nil
		guard selectedResult != nil else { return }
		
		
		let request = MKDirections.Request()
		request.source = MKMapItem(placemark:MKPlacemark(coordinate: locationEnviroment.location))
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
	
	
	func formatDistance(distance: Int) -> String {
		if(distance >= 1000) {
			let newDistance = Double(Double(distance) / 1000)
			return "\(String(format: "%.2f", newDistance)) km"
		}
		return "\(distance) m"
	}
	
	
}
