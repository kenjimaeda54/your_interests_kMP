//
//  ProfileState.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 26/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

@MainActor
class ProfileState: ObservableObject {
	@Published var loading: LoadingState = .loading
	private let  viewModel = AuthSapabaseViewModel()
	
	
	
	func signInUser(phone: String) {
		viewModel.sendCodeOTP(phone: "+5511949961845")
	}
	 
	
	
}
