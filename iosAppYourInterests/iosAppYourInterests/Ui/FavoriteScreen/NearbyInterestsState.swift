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
  @Published	var location: CLLocationCoordinate2D? = nil
  @Published var viewModel = RecoveryLocation()
  var gelocationError: GeolocationError? = nil
	
	

	

	func fetchLocation() async {
		self.loadingState = .loading
		viewModel.getLocation()
		for await viewModel in viewModel.location {
			
			if(viewModel.exception != nil) {
 
				if let exception = viewModel.exception as? GeolocationException {
					self.loadingState = .failure
					self.gelocationError = exception.error
					print(exception.messageError ?? "")
				}
			
			}
			
			if(viewModel.data != nil) {
				self.loadingState = .success
				self.location = CLLocationCoordinate2D(latitude: viewModel.data!.latitude, longitude: viewModel.data!.longitude)
				
			}
			
		}
		
		
	}
	
	
	
}
