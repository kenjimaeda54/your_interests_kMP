//
//  SingUpScreen.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 27/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Combine
import ToastUI

//https://www.quanshousio.com/ToastUI/documentation/toastui/about
@available(iOS 17.0, *)
struct SingUpScreen: View {
	@Binding var phone:  String
	@EnvironmentObject private var graphNavigation: NavigationGraph
	@StateObject private var authState = AuthState()
	@State private var isShowToastError = false
	
	
	var body: some View {
		
		GeometryReader { geometry in
			ZStack {
				VStack(alignment: .leading,spacing: 10 ) {
					Color.clear
					
					Text("Bem vindo")
						.font(.custom(FontsApp.bold, size: 35))
						.foregroundStyle(ColorsApp.black)
					
					Text("Seus interesses na palma da mão")
						.font(.custom(FontsApp.regular, size: 20))
						.foregroundStyle(ColorsApp.black)
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
							.disabled(authState.loading == .loading)
							.keyboardType(.numbersAndPunctuation)
							.submitLabel(.done)
							.autocorrectionDisabled()
							.textInputAutocapitalization(.none)
							.onSubmit {
								if(phone.count >= 10 ) {
									sendCode(with: phone)
								}
							}
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
						ButtonDefault(
							action: {
								if(phone.count >= 10) {
									sendCode(with: phone)
								}
							}, 
							title: "Entrar", 
							isDisable: phone.count <= 10 || authState.loading == .loading
						)
						
					}
					.padding([.horizontal], 13)
					.padding([.vertical],15)
					.background(ColorsApp.white.clipShape(RoundedRectangle(cornerRadius: 13)))
					
					Spacer(minLength: geometry.size.height * 0.3)
				}
				
				if(authState.loading == .loading) {
					ProgressView()
						.tint(ColorsApp.black)
						.frame(width: 100,height: 100)
						.controlSize(.large)
				}
				
			}
			.padding([.horizontal],13)
			.frame(minWidth: 0,maxWidth: .infinity, minHeight: 0,maxHeight: .infinity)
			.background(ColorsApp.gray.opacity(0.7))
			.onChange(of: authState.loading) { _, newValue in
				if(newValue == .success) {
					graphNavigation.switchView = .confirmCode
					isShowToastError = false
				}
				
				if(newValue == .failure) {
					isShowToastError = true
					
				}
			}
			.toast(isPresented: $isShowToastError,dismissAfter: 3.0) {
				ToastView() {
					Text("Verifique se o número existe, não foi possível enviar o código")
						.font(.custom(FontsApp.regular, size: 17))
						.foregroundStyle(ColorsApp.black)
				}
				.frame(width: 250)
				.onDisappear {
					isShowToastError = false
				}
			}
			.environmentObject(graphNavigation)
			
		}
	}
}

@available(iOS 17.0, *)
#Preview {
	SingUpScreen(phone: .constant(""))
		.environmentObject(NavigationGraph())
}

@available(iOS 17.0, *)
extension SingUpScreen {
	
	func sendCode(with phone: String) {
		Task {
			await authState.sendCode(with: "+55\(phone)")
		}
	}
	
	
	func limmitText() {
		if  phone.count > 11 {
			phone = String(phone.prefix(10))
		}
	}
	
}
