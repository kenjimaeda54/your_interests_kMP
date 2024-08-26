//
//  ResearchScreen.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 01/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

@available(iOS 17.0, *)
struct ResearchScreen: View {
	@State private var searchPlace: String = ""
	@FocusState private var searchIsFocused: Bool
	@EnvironmentObject private var locationEnvironment: LocationEnvironment
	@StateObject private var researchState = ResearchState()
	@State private var isNewDestination = false
	@State private var placeSelected: PhotosPlacesWithRelationNearbyModel? =  nil
	
	var body: some View {
		NavigationStack {
			GeometryReader { geometry in
				VStack(alignment: .leading){
					HStack {
						Image(systemName: "magnifyingglass")
							.resizable()
							.frame(width: 22, height: 22)
							.foregroundColor(ColorsApp.white)
							.offset(x: 10,y: 0)
						
						TextField("", text: $searchPlace, prompt:Text("Pesquisar")
							.font(.custom(FontsApp.light, size: 16))
							.foregroundStyle(ColorsApp.black.opacity(0.5)),
											axis: .vertical
											
						)
						.padding([.horizontal],15)
						.autocorrectionDisabled()
						.font(.custom(FontsApp.regular, size: 16))
						.foregroundStyle(ColorsApp.black)
						.onChange(of: searchPlace) {
							if searchPlace.last?.isNewline == .some(true) {
								searchPlace.removeLast()
								searchIsFocused = false
								
							}
							Task {
								await researchState.getPlaces(searchPlace: searchPlace, latitude: locationEnvironment.location.latitude, longitude: locationEnvironment.location.longitude)
							}
						}
						.focused($searchIsFocused )
						.submitLabel(.done)
					}
					.padding([.horizontal],10)
					.padding([.vertical],11)
					.background(
						ColorsApp.gray
							.clipShape(RoundedRectangle(cornerRadius: 10))
					)
					Text("Pesquise por: telefone, categoria ou telefone")
						.font(.custom(FontsApp.light, size: 15))
					
					if(researchState.loading == .success) {
						
						if let places = researchState.searchPlaceViewModel {
							if(places.isEmpty) {
								Spacer(minLength: geometry.size.height * 0.5)
								VStack(alignment: .center) {
									Text("Sem resultado")
									
								}
								.frame(minWidth: 0, maxWidth: .infinity, alignment: .center)
								
							}
							CustomListContent {
								ForEach(places,id: \.fsqId) { item in
									
									LazyVStack  {
										
										RowItemPlace(place: item)
											.onTapGesture {
												isNewDestination = true
												placeSelected = item
											}
										
									}
									
								}
								
							}
							
						}
					}
					if(researchState.loading == .loading) {
						CustomListContent {
							ForEach(0...10,id: \.self) {_ in
								
								LazyVStack {
									RowItemShimmer()
									
								}
								
							}
						}
					}
				}
				.padding([.horizontal],13)
				.padding([.vertical],20)
				.frame(minWidth: /*@START_MENU_TOKEN@*/0/*@END_MENU_TOKEN@*/,maxWidth: .infinity, minHeight: 0,maxHeight: .infinity,alignment: .top)
				.background(ColorsApp.gray.opacity(0.7))
				.navigationDestination(isPresented: $isNewDestination) {
					if let place = placeSelected {
						DetailsPlaceShared(place: place)
							.navigationBarBackButtonHidden()
					}
				}
				.task {
					await researchState.getPlaces(searchPlace: "", latitude: locationEnvironment.location.latitude, longitude: locationEnvironment.location.longitude)
				}
				.environmentObject(locationEnvironment)
			}
			
		}
		
	}
	
}

