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
	
	
	func getUser() async  {
		loading = .loading
		viewModel.fetchUser(phone: nil)
		
		for await user in viewModel.insertIsSuccess {
			
			if(user.exception != nil) {
				print("error getUser: \(String(describing: user.exception))")
				loading = .failure
			}
			
			if let haveUser = user.data as? Bool {
			
				if(haveUser) {
					loading = .success
					return
				}
				
				loading = .failure
				
			}
			
		}
	  
	}
	
	
	
	
	
}
