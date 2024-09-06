//
//  CompletedRegisterUserState.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 03/09/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

@MainActor
class CompletedRegisterUserState: ObservableObject  {
	@Published private var loading: LoadingState = .none
  private var viewModel = UserSapabaseViewModel()
	var isSuccessRegisterUser = false
	
	
	
	func registerUser(user: UserWithPhotoByTeArray) async {
		loading = .loading
		viewModel.insertUser(user: user)
		for await result in viewModel.insertIsSuccess {
			if(result.exception != nil ){
				loading = .failure
				print("error insert user: \(String(describing: result.exception))")
			}
			
			if let data = result.data as? Bool {
				
				if(data) {
					loading = .success
					isSuccessRegisterUser = true
				}
				
				loading = .failure
				isSuccessRegisterUser = false
			}
			
		}
	}
	
	
	
}
