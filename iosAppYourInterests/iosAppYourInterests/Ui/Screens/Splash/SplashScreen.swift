//
//  SplashScreen.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 06/09/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Lottie

//https://github.com/airbnb/lottie-ios/discussions/2189
//usar a splash acima
struct SplashScreen: View {
	@StateObject private var splashState = SplashState()
	@EnvironmentObject private var graphNavigation: NavigationGraph
	
	var body: some View {
		LottieView(animation: .named("splash_animation"))
			.playing()
			.animationDidFinish { _ in
				if(splashState.loading == .success) {
					graphNavigation.switchView = .tabCustomView
					return
				}
				
				graphNavigation.switchView = .singUp
			}
		
			.task {
				await splashState.getUser()
			}
			.environmentObject(graphNavigation)
	}
}

#Preview {
	SplashScreen()
}
