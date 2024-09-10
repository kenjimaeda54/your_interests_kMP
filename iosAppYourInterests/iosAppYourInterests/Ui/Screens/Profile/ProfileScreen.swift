//
//  ProfileScreen.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 01/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

@available(iOS 17.0, *)
struct ProfileScreen: View {
	@EnvironmentObject private var userEnvironment: UserEnvironment
	
	
	var body: some View {
		VStack(alignment: .center, spacing: 10) {
			if let user = userEnvironment.user {
				AsyncImage(url: URL(string: user.photoUrl)) { image in
					
					image
						.resizable()
						.frame(width: 200,height: 200)
						.clipShape(/*@START_MENU_TOKEN@*/Circle()/*@END_MENU_TOKEN@*/)
						.scaledToFit()
					
				} placeholder: {
					Image("imageNotFound")
						.resizable()
						.frame(width: 200,height: 200)
						.clipShape(/*@START_MENU_TOKEN@*/Circle()/*@END_MENU_TOKEN@*/)
						.scaledToFit()
					
				}
				VStack(alignment: .leading,spacing: 5) {
					Text("Nome:")
						.font(.custom(FontsApp.light, size: 17))
						.foregroundStyle(ColorsApp.black) +
					Text(" \(user.name)")
						.font(.custom(FontsApp.bold, size: 17))
						.foregroundStyle(ColorsApp.black)
					
					Text("Telefone:")
						.font(.custom(FontsApp.light, size: 17))
						.foregroundStyle(ColorsApp.black) +
					Text(" \(user.phone)")
						.font(.custom(FontsApp.bold, size: 17))
						.foregroundStyle(ColorsApp.black)
					
				}
				.padding(.top, 40)
				.frame(minWidth: 0, maxWidth: .infinity,alignment: .leading)
				
			}
			
		}
		.padding(.horizontal,13)
		.frame(minWidth: /*@START_MENU_TOKEN@*/0/*@END_MENU_TOKEN@*/,maxWidth: .infinity, minHeight: 0,maxHeight: .infinity,alignment: .center)
		.background(ColorsApp.gray.opacity(0.7))
		
	}
}

