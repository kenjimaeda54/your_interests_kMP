//
//  NearbyInterestsState.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 07/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import MapKit
import shared


@MainActor
class NearbyInterestsState: ObservableObject {
	
	@Published var loadingState: LoadingState = .loading
	var location: CLLocationCoordinate2D? = nil
	var viewModel = RecoveryLocation()
	var gelocationError: GeolocationError? = nil
	
	
	func fetchLocation() async {
		self.loadingState = .loading
		viewModel.getLocation()
    
		for await viewModel in viewModel.location {
			
			if(viewModel.exception != nil) {
 
				if let error = viewModel.exception as? GeolocationError {
					self.loadingState = .failure
					self.gelocationError = error
				}
			
			}
			
			if(viewModel.data != nil) {
				self.loadingState = .success
				self.location = CLLocationCoordinate2D(latitude: viewModel.data!.coordinates.latitude, longitude: viewModel.data!.coordinates.longitude)
				
			}
			
		}
		
		
	}
	
	
	
}
