//
//  WrapperPhotosPlaces.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 14/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

//infelizmente MapBox utiliza impelemntacao do ForEvery para criar uma lista de Annotations
//por isso criei esse wrapper, pois model que espera e um identifiable
struct WrapperIdentifiblePhotosPlaces: Identifiable {
	let id: String = UUID().uuidString
	let photoPlacesModel: PhotoPlacesModel
	let places: PlacesNearbyModel
}
