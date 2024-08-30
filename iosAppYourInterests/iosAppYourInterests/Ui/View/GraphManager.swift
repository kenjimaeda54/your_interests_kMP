//
//  GraphManager.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 29/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

@available(iOS 17.0, *)
struct GraphManager: View {
	@StateObject private var graph = NavigationGraph()
	@State private var phone = ""
	let transition: AnyTransition = .asymmetric(insertion: .move(edge: .bottom), removal: .move(edge: .top))
	
	var body: some View {
		Group {
			switch (graph.switchView) {
				case .singUp:
					SingUpScreen(phone: $phone)
						.environmentObject(graph)
						.transition(transition)
					
				case .confirmCode:
					ConfirmCodeScreen(phone: $phone)
						.environmentObject(graph)
						.transition(transition)
					
				case .finishedRegister:
					CompletedRegisterUserScreen()
						.environmentObject(graph)
						.transition(transition)
			}
		}
		.animation(.default,value: graph.switchView)
	}
	
	
}


@available(iOS 17.0, *)
#Preview {
	GraphManager()
}
