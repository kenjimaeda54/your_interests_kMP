//
//  NearbyInterestsState.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 11/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

@MainActor
class NearbyInterestsState: ObservableObject {
	@Published  var loading: LoadingState = .loading
	private var viewModel = PlacesNearbyViewModel()
	var placesRelationsWithPhoto: [PhotosPlacesWithRelationNearbyModel]?
	
	func getPlaces(latitude: Double, longitude: Double) async {
		loading = .loading
		viewModel.getPlacesNearby(latitude: latitude, longitude: longitude)
		
		
		for await places in viewModel.placesNearby  {
			if(places.exception != nil) {
				print("exception get places: \(String(describing: places.exception))")
				loading = .failure
			}
			
			if let places = places.data as? [PhotosPlacesWithRelationNearbyModel] {
				loading = .success
				self.placesRelationsWithPhoto = places
 			}
			
		}
		
	}
	
	
	
	
	
}
