//
//  ConfirmCode.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 28/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ConfirmCodeScreen: View {
	@EnvironmentObject private var navigationGrap: NavigationGraph
	 
    var body: some View {
        Text("Confirm Code")
				.environmentObject(navigationGrap)
    }
		
}

#Preview {
	ConfirmCodeScreen()  
		.environmentObject(NavigationGraph())
}
