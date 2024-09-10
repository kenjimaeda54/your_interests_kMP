//
//  SplashState.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 09/09/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

@MainActor
class SplashState: ObservableObject {
	@Published var loading: LoadingState = .none
	private let  viewModel = UserSapabaseViewModel()
	var user: UserModel? = nil
	
	func getUser() async  {
		loading = .loading
		viewModel.fetchUser(phone: nil)
		
		for await user in viewModel.user {
			
			if(user.exception != nil) {
				print("error getUser: \(String(describing: user.exception))")
				loading = .failure
			}
			
			if let user = user.data  {
					loading = .success
					self.user = user
			}
			
		}
	  
	}
	
	
	
	
	
}
