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
	var placesRelationsWithPhoto: [WrapperIdentifiblePhotosPlaces]?
	
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
				let wrapperModel = places.map {
					WrapperIdentifiblePhotosPlaces(photoPlacesModel: $0.photoPlacesModel, places: $0.places)
				}
				self.placesRelationsWithPhoto = wrapperModel
 			}
			
		}
		
	}
	

	
	deinit {
		viewModel.clear()
	}
	
	
}
