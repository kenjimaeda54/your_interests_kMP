//
//  DetailsPlaceShared.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 25/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared
import MapKit

//https://www.hackingwithswift.com/quick-start/swiftui/how-to-synchronize-animations-from-one-view-to-another-with-matchedgeometryeffect@available(iOS 17.0, *)

@available(iOS 17.0, *)
struct DetailsPlaceShared: View {
	@Namespace private var animation
	@State var isShowMap = false
	let place: PhotosPlacesWithRelationNearbyModel
	@State var selectedResult: MKMapItem?
	@State var route: MKRoute?
	

	var body: some View {
			VStack {
				if(isShowMap) {
					
					if let route = route, let selectedResult = selectedResult {
						NavigationRouteScreen(destination: CLLocationCoordinate2D(latitude: place.places.geocode.latitude, longitude: place.places.geocode.longitude), selectedResult: selectedResult, route: route, placeAnnotaion: place)
							.safeAreaInset(edge: .top, content: {
							  
						    ButtonBackTopAppBar()
									.onTapGesture {
										withAnimation(.easeInOut) {
											isShowMap.toggle()
										}
									}
								
							})
							.matchedGeometryEffect(id: "map-animation", in: animation)
					}
					
			
				}else {
					DetailsPlaceScreen(place: place, selectedResult: $selectedResult, route: $route, isShowMap: $isShowMap)
						.matchedGeometryEffect(id: "map-animation", in: animation)
				}
				
			}
			.frame(minWidth: /*@START_MENU_TOKEN@*/0/*@END_MENU_TOKEN@*/,maxWidth: .infinity,minHeight: 0, maxHeight: .infinity)
    }
}

 
