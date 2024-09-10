//
//  ResearchScreenState.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 21/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

@MainActor
class ResearchState:  ObservableObject {
	@Published var loading: LoadingState = .loading
  private var viewModel = SearchPlacesByQueryViewModel()
	var searchPlaceViewModel: [PhotosPlacesWithRelationNearbyModel]?
	
	
	func getPlaces(searchPlace query: String,latitude: Double, longitude: Double ) async {
		loading = .loading
		viewModel.getPlacesByQuery(query: query, latitude: latitude, longitude: longitude)

		for await placesByQuery in viewModel.placesByQuery {
			 
			if(placesByQuery.exception != nil) {
				print("error search query, \(String(describing: placesByQuery.exception))")
				loading = .failure
				return
			}
			
			if let placesByQuery = placesByQuery.data as? [PhotosPlacesWithRelationNearbyModel] {
				loading = .success
				searchPlaceViewModel = placesByQuery
				
			}
			
			
		}
		
	}
 
	
	
	
}
