//
//  GraphManager.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 29/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

@available(iOS 17.0, *)
struct GraphManagerView: View {
	@StateObject private var graph = NavigationGraph()
	@StateObject private var locationEnviroment = LocationEnvironment()
	@StateObject private var userEnvironment = UserEnvironment()
	@State private var phone = ""
	let transition: AnyTransition = .asymmetric(insertion: .move(edge: .bottom), removal: .move(edge: .top))
	
	var body: some View {
		Group {
			switch (graph.switchView) {
				case .singUp:
					SingUpScreen(phone: $phone)
						.transition(transition)
					
				case .confirmCode:
					ConfirmCodeScreen(phone: $phone)
						.transition(transition)
					
					
					
				case .finishedRegister:
					CompletedRegisterUserScreen(phone: $phone)
						.transition(transition)
					
					
				case .splashScreen:
					SplashScreen()
						.transition(transition)
					
					
				case .tabCustomView:
					TabCustomView()
						.transition(transition)
					
			}
		}
		.animation(.default,value: graph.switchView)
		.environmentObject(graph)
		.environmentObject(locationEnviroment)
		.environmentObject(userEnvironment)
		
	}
	
	
}


@available(iOS 17.0, *)
#Preview {
	GraphManagerView()
}
