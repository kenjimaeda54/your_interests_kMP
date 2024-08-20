//
//  ResearchScreen.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 01/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

@available(iOS 17.0, *)
struct ResearchScreen: View {
	@EnvironmentObject private var placeEnviroment: PlacesPhotoEnviroment
	@State private var searchPlace: String = ""
	@FocusState private var searchIsFocused: Bool
	
	
	var body: some View {
		VStack(alignment: .leading){
			

			Text("Pesquise por: telefone, categoria ou telefone")
				.font(.custom(FontsApp.light, size: 15))
			
			List  {
				
				ForEach(Array(placeEnviroment.places.enumerated()),id: \.1.fsqId) { (index,item) in
					
					RowItemPlace(place: item)
					
				}
				.listRowInsets(EdgeInsets())
				.listRowBackground(Color.clear)
				.listRowSeparator(.hidden)
				
			}
			.padding([.top],30)
			.listStyle(.plain)
			.scrollContentBackground(.hidden)
			.scrollIndicators(.hidden)
			.listRowSpacing(15)
			
		}
		.padding([.horizontal],13)
		.padding([.vertical],20)
		.frame(minWidth: /*@START_MENU_TOKEN@*/0/*@END_MENU_TOKEN@*/,maxWidth: .infinity, minHeight: 0,maxHeight: .infinity,alignment: .top)
		.background(ColorsApp.gray.opacity(0.7))
		.onAppear {
			print(placeEnviroment.places.count)
		}
	}
}


@available(iOS 17.0, *)
#Preview {
	ResearchScreen()
		.environmentObject(PlacesPhotoEnviroment())
	
}
