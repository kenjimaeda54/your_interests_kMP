//
//  PlacesPhotoEnviroment.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 19/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared
import SwiftUI

class PlacesPhotoEnviroment: ObservableObject {
	@Published var places:  [PhotosPlacesWithRelationNearbyModel] = []
}
