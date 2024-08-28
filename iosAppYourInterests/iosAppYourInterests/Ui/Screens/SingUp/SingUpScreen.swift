//
//  SingUpScreen.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 27/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Combine

@available(iOS 17.0, *)
struct SingUpScreen: View {
	@State private var phone: String = ""
	
	var body: some View {
		GeometryReader { geometry in
			
			VStack(alignment: .leading,spacing: 10 ) {
				Color.clear
				
				Text("Bem vindo")
					.font(.custom(FontsApp.bold, size: 35))
					.foregroundStyle(ColorsApp.white)
				
				
				
				Text("Seus interesses na palma da mão")
					.font(.custom(FontsApp.regular, size: 20))
					.foregroundStyle(ColorsApp.white)
					.padding([.bottom], geometry.size.height * 0.09)
				
				VStack(alignment: .leading, spacing: 15){
					Text("Numero de telefone")
						.font(.custom(FontsApp.regular, size: 17))
						.foregroundStyle(ColorsApp.black)
					
					HStack {
						
						Text("+55")
							.font(.custom(FontsApp.regular, size: 17))
							.foregroundStyle(ColorsApp.black)
							.padding([.horizontal],10)
							.padding([.vertical],8)
							.background(
								ColorsApp.white.clipShape(RoundedRectangle(cornerRadius: 7)).shadow(radius: 2)
							)
						
						TextField("", text: $phone, prompt:
												Text("ex: 119444444")
							.font(.custom(FontsApp.regular, size: 17))
							.foregroundStyle(ColorsApp.black.opacity(0.5))
											
						)
						.font(.custom(FontsApp.regular, size: 17))
						.foregroundStyle(ColorsApp.black)
						.onReceive(Just(phone), perform: { _ in
							limmitText()
						})
						.padding([.horizontal],10)
						.padding([.vertical],8)
						.background(
							ColorsApp.white.clipShape(RoundedRectangle(cornerRadius: 7)).shadow(radius: 2)
						)
						
					}
					Button {} label: {
						Text("Entrar")
							.font(.custom(FontsApp.light, size: 18))
							.foregroundStyle(ColorsApp.white)
					}
					.padding([.vertical],7)
					.frame(minWidth: /*@START_MENU_TOKEN@*/0/*@END_MENU_TOKEN@*/,maxWidth: .infinity)
					.background(ColorsApp.blue.clipShape(RoundedRectangle(cornerRadius: 10)))
				}
				.padding([.horizontal], 13)
				.padding([.vertical],15)
				.background(ColorsApp.white.clipShape(RoundedRectangle(cornerRadius: 13)))
				
				Spacer(minLength: geometry.size.height * 0.3)
			}
			.padding([.horizontal],13)
			.frame(minWidth: 0,maxWidth: .infinity, minHeight: 0,maxHeight: .infinity)
			.background(ColorsApp.blue)
		}
	}
}

@available(iOS 17.0, *)
#Preview {
	SingUpScreen()
}

@available(iOS 17.0, *)
extension SingUpScreen {
	
	
	func limmitText() {
		if  phone.count > 11 {
			phone = String(phone.prefix(10))
		}
	}
	
}
