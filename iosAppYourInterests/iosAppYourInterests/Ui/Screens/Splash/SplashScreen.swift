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
@available(iOS 17.0, *)
struct SplashScreen: View {
	@StateObject private var splashState = SplashState()
	@EnvironmentObject private var graphNavigation: NavigationGraph
	@EnvironmentObject private var userEnvironment: UserEnvironment
	@Environment(\.scenePhase) var scenePhase

	
	var body: some View {
			LottieView(animation: .named("splash_animation"))
			.playing()
			.animationDidFinish{ _  in
				Task {
					await splashState.getUser()
				}
			}
			.onChange(of: splashState.loading) {
				if(splashState.loading == .success) {
					userEnvironment.user = splashState.user
					graphNavigation.switchView = .tabCustomView
					return
				}
			
				if(splashState.loading == .failure) { 
					graphNavigation.switchView = .singUp
				}
			}
			.environmentObject(graphNavigation)
			.environmentObject(userEnvironment)
	}
}

@available(iOS 17.0, *)
#Preview {
	SplashScreen()
}
