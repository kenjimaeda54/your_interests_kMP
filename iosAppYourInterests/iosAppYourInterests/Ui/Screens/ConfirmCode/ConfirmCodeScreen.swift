//
//  ConfirmCode.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 28/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI


enum FocusedField {
	case one,two,three,four,five,six
}


@available(iOS 17.0, *)
struct ConfirmCodeScreen: View {
	@StateObject private var singUpState = SingUpState()
	@EnvironmentObject private var environmentGraph: NavigationGraph
	@FocusState private var focus: FocusedField?
	@Binding var phone: String
	@State private var timeRemaining = 60
	@State var oneFieldInput = ""
	@State var twoFieldInput = ""
	@State var threeFieldInput = ""
	@State var fourFieldInput = ""
	@State var fiveFieldInput = ""
	@State var sixFieldInput = ""
	var filedsTextField = Array(repeating: 0,count: 6)
	let timer = Timer.publish(every: 1, on: .main, in: .common).autoconnect()
	
	
	var body: some View {
		GeometryReader { geometry in
			VStack(spacing: 3) {
				Text("Insira o codigo enviado pelo SMS")
					.font(.custom(FontsApp.bold, size: 25))
					.foregroundStyle(ColorsApp.black)
					.padding([.bottom],60)
					.frame(minWidth: 0, maxWidth: .infinity,alignment: .leading)
				HStack(alignment: .center, spacing: geometry.size.width * 0.03) {
					InputCode(text: $oneFieldInput, width: geometry.size.width * 0.13, height: geometry.size.width * 0.13)
						.focused($focus, equals: .one)
						.onChange(of: oneFieldInput) { _, __ in
							focus = .two
						}
					InputCode(text: $twoFieldInput, width: geometry.size.width * 0.13, height: geometry.size.width * 0.13)
						.focused($focus, equals: .two)
						.onChange(of: twoFieldInput) { _, __ in
							focus = .three
						}
					InputCode(text: $threeFieldInput, width: geometry.size.width * 0.13, height: geometry.size.width * 0.13)
						.focused($focus, equals: .three)
						.onChange(of: threeFieldInput) { _, __ in
							focus = .four
						}
					InputCode(text: $fourFieldInput, width: geometry.size.width * 0.13, height: geometry.size.width * 0.13)
						.focused($focus, equals: .four)
						.onChange(of: fourFieldInput) { _, __ in
							focus = .five
						}
					InputCode(text: $fiveFieldInput, width: geometry.size.width * 0.13, height: geometry.size.width * 0.13)
						.focused($focus, equals: .five)
						.onChange(of: fiveFieldInput) { _, __ in
							focus = .six
						}
					InputCode(text: $sixFieldInput, width: geometry.size.width * 0.13, height: geometry.size.width * 0.13)
						.focused($focus, equals: .six)
						.onChange(of: sixFieldInput) { _, __ in
							focus =  nil
						}
				}
				
				
				Text("\(timeRemaining)")
					.font(.custom(FontsApp.regular, size: 18))
					.foregroundStyle(ColorsApp.black)
					.padding([.top],50)
					.onReceive(timer) { _ in
						
						if(timeRemaining > 0){
							timeRemaining -= 1
						}
						
					}
				
				
				
				if(timeRemaining == 0) {
					Button(action: sendCode, label: {
						Text("Enviar novo codigo")
							.font(.custom(FontsApp.bold, size: 18))
							.foregroundStyle(ColorsApp.black)
					})
				}
				
			}
			.ignoresSafeArea(edges: [.horizontal])
			.padding([.horizontal],13)
			.frame(minHeight: 0, maxHeight: .infinity,alignment: .center)
			.background(ColorsApp.gray.opacity(0.7))
			.onChange(of: singUpState.isSuccessMessage) { oldValue, newValue in
				if(newValue) {
					timeRemaining = 60
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
			await singUpState.sendCode(with: "+55\(phone)")
		}
	}
	
}

@available(iOS 17.0, *)
#Preview {
	ConfirmCodeScreen(phone: .constant(""))
		.environmentObject(NavigationGraph())
}


