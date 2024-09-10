//
//  LocationEnviroment.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 21/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import CoreLocation

class LocationEnvironment: ObservableObject {
	@Published var location: CLLocationCoordinate2D = CLLocationCoordinate2D(latitude: 0.0, longitude: 0.0)
}

