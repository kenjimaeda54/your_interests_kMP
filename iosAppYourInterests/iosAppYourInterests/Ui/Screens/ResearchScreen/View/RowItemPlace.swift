//
//  RowItemPlace.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 20/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct RowItemPlace: View {
		let place: PhotosPlacesWithRelationNearbyModel
    var body: some View {
			HStack(spacing: 15) {
				AsyncImage(url: URL(string: place.photoPlacesModel.icon)) { image in
					
						image
							.resizable()
							.clipShape(RoundedRectangle(cornerRadius: 10))
							.frame(width: 40,height: 40)
				
				}placeholder: {
					 Image("imageNotFound")
						.resizable()
						.clipShape(RoundedRectangle(cornerRadius: 10))
						.frame(width: 40,height: 40)
				}
				Text(place.places.name)
					.font(.custom(FontsApp.regular, size: 15))
					.foregroundStyle(ColorsApp.black.opacity(0.8))
			}
			.frame(minWidth: 0,maxWidth: .infinity, minHeight: 0,maxHeight: .infinity,alignment: .leading)
		}
}

#Preview {
	RowItemPlace(place: PhotosPlacesWithRelationNearbyModel(photoPlacesModel: PhotoPlacesModel(id: "", icon: "https://github.com/kenjimaeda54.png"), places: PlacesNearbyModel(geocode: GeocodeModel(latitude: 3434, longitude: 34343), address: "", name: "Casa do parafuso", distance: Int32(), fsqId: "34343"), fsqId: "34343"))
}
