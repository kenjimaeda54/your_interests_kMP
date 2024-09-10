//
//  ConfirmCode.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 28/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import ToastUI


enum FocusedField {
	case one,two,three,four,five,six
}


@available(iOS 17.0, *)
struct ConfirmCodeScreen: View {
	@StateObject private var authState = AuthState()
	@EnvironmentObject private var environmentGraph: NavigationGraph
	@FocusState private var focusTextField: Bool
	@Binding var phone: String
	@State private var timeRemaining = 60
	@State private var codeOtp = ""
	@State private var isShowToastError = false
	var numbersOfFIelds = 6
	let timer = Timer.publish(every: 1, on: .main, in: .common).autoconnect()
	
	
	var body: some View {
		GeometryReader { geometry in
			VStack(spacing: 3) {
				Text("Insira o codigo enviado pelo SMS")
					.font(.custom(FontsApp.bold, size: 25))
					.foregroundStyle(ColorsApp.black)
					.padding([.bottom],60)
					.frame(minWidth: 0, maxWidth: .infinity,alignment: .leading)
				ZStack  {
					
					if(authState.loadingVerifiCode == .loading) {
						ProgressView()
							.tint(ColorsApp.black)
							.frame(width: 100,height: 100)
							.controlSize(.large)
					}
					HStack(alignment: .center, spacing: geometry.size.width * 0.03) {
						OTPFieldView(numberOfFields: numbersOfFIelds, otp: $codeOtp, size: geometry.size.width * 0.12,loading: authState.loadingVerifiCode  == .loading)
							.onChange(of: codeOtp) { newOtp in
								if newOtp.count == numbersOfFIelds {
									Task {
										await	authState.verifyCode(with: "+55\(phone)", code: codeOtp)
										
									}
								}
								
							}
							.focused($focusTextField)
					}
				}
				
				if(timeRemaining > 0) {
					Text("\(timeRemaining)")
						.font(.custom(FontsApp.regular, size: 18))
						.foregroundStyle(ColorsApp.black)
						.padding([.top],50)
						.onReceive(timer) { _ in
							
							if(timeRemaining > 0){
								timeRemaining -= 1
							}
							
						}
				}
				
				if(timeRemaining == 0) {
					Button(action: sendCode, label: {
						Text("Enviar novo codigo")
							.padding([.top],50)
							.font(.custom(FontsApp.bold, size: 18))
							.foregroundStyle(ColorsApp.black)
						
					})
				}
				
			}
			.ignoresSafeArea(edges: [.horizontal])
			.onAppear {
				DispatchQueue.main.asyncAfter(deadline: .now() + 5) {
					focusTextField = true
				}
			}
			.padding([.horizontal],13)
			.frame(minHeight: 0, maxHeight: .infinity,alignment: .center)
			.background(ColorsApp.gray.opacity(0.7))
			.onChange(of: authState.loadingVerifiCode) { _,newValue in
				if(newValue == .success) {
					environmentGraph.switchView = .finishedRegister
				}
				
				if(newValue == .failure) {
					isShowToastError = true
					codeOtp = ""
				}
				
			}
			.onChange(of: authState.isSuccessMessage) { oldValue, newValue in
				if(newValue) {
					timeRemaining = 60
				}
			}
			.toast(isPresented: $isShowToastError,dismissAfter: 3.0) {
				ToastView() {
					Text("Codigo digitado esta errado")
						.font(.custom(FontsApp.regular, size: 17))
						.foregroundStyle(ColorsApp.black)
				}
				.frame(width: 250)
				.onDisappear {
					isShowToastError = false
				}
			}
			
			.environmentObject(environmentGraph)
			
		}
	}
	
	
}

@available(iOS 17.0, *)
extension ConfirmCodeScreen {
	
	func sendCode() {
		Task {
			await authState.sendCode(with: "+55\(phone)")
		}
	}
	
}

@available(iOS 17.0, *)
#Preview {
	ConfirmCodeScreen(phone: .constant(""))
		.environmentObject(NavigationGraph())
}


