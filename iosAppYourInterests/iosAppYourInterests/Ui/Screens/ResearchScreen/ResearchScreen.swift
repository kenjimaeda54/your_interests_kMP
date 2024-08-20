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
	
	var body: some View {
		VStack(alignment: .leading){
			HStack {
				Image(systemName: "magnifyingglass")
					.resizable()
					.frame(width: 22, height: 22)
					.foregroundColor(ColorsApp.white)
					.offset(x: 10,y: 0)
				
				TextField("", text: $searchPlace, prompt:Text("Pesquisar")
					.font(.custom(FontsApp.light, size: 16))
					.foregroundStyle(ColorsApp.black.opacity(0.5))
									
				)
				.padding([.horizontal],15)
				.autocorrectionDisabled()
				.font(.custom(FontsApp.regular, size: 16))
				.foregroundStyle(ColorsApp.black)
				
			}
			.padding([.horizontal],10)
			.padding([.vertical],11)
			.background(
				ColorsApp.gray
					.clipShape(RoundedRectangle(cornerRadius: 10))
			)
			
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
