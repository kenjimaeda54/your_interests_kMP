
//  TabCustomView.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 01/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct TabCustomView: View {
	@StateObject var stateTagSelected = StateNavigationTabView()
	
	var body: some View {
		TabView(selection: $stateTagSelected.tagSelected){
			FavoriteScreen()
				.tabItem {
					Image(systemName: "bookmark")
				}
				.tag(0)
			
			ResearchScreen()
				.tabItem {
					Image(systemName: "magnifyingglass")
				}
				.tag(1)
			
			ProfileScreen()
				.tabItem {
					Image(systemName: "person")
				}
				.tag(2)
			
		}
		.environmentObject(stateTagSelected)
	}
	
}


