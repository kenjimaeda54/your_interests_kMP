
//  TabCustomView.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 01/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

@available(iOS 17.0, *)
struct TabCustomView: View {
	@StateObject var stateTagSelected = StateNavigationTabView()
	
	
	
	
	var body: some View {
		TabView(selection: $stateTagSelected.tagSelected){
			NearbyInterestScreen()
				.tabItem {
					Image(systemName: "bookmark")
						
				}
  				.tag(0)
				
				
			
			ResearchScreen()
				.tabItem {
					Image(systemName: "magnifyingglass")
						.resizable()
						
					}
				.tag(1)
			
			ProfileScreen()
				.tabItem {
					Image(systemName: "person")
						.resizable()
									.aspectRatio(contentMode: .fit)
									.frame(width: 30, height: 30) 			
				}
				.tag(2)
			
			  //da para fazer assm porem sem tranparente
				//.toolbarBackground(ColorsApp.white, for: .tabBar)
				//.toolbarBackground(.visible, for: .tabBar)
				//.toolbarColorScheme(.none, for: .tabBar)
			
		}
		.tint(ColorsApp.blue)
		.environmentObject(stateTagSelected)
 		.onAppear {
			UITabBar.appearance().unselectedItemTintColor = UIColor(ColorsApp.black)
			let standardAppearance = UITabBarAppearance()
			standardAppearance.configureWithDefaultBackground()
			standardAppearance.backgroundColor = UIColor(ColorsApp.white)
			
			UITabBar.appearance().standardAppearance = standardAppearance
			let scrollEdgeAppearance = UITabBarAppearance()
			scrollEdgeAppearance.configureWithTransparentBackground() //transparente
			UITabBar.appearance().scrollEdgeAppearance = scrollEdgeAppearance
			scrollEdgeAppearance.backgroundColor = UIColor(ColorsApp.white)
			
		}
	}
		
	
}


