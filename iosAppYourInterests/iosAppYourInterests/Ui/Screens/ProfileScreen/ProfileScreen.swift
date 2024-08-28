//
//  ProfileScreen.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 01/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ProfileScreen: View {
	@StateObject private var profileState: ProfileState = ProfileState()
	
	
    var body: some View {
			Button(action: { 
				profileState.signInUser(phone: "")
			}) {
				Text("login")
			}
    }
}

 
