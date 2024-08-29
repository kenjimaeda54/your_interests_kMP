//
//  SingUpState.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 27/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared


@MainActor
class SingUpState: ObservableObject {
	@Published  var loading: LoadingState = .none
	var isSuccessMessage: Bool = false
	private var viewModel = AuthSapabaseViewModel()
	
	
	func sendCode(with phone: String) async {
		loading = .loading
		viewModel.sendCodeOTP(phone: phone)
		
		for await result in viewModel.successSendCodeOTP {
			
			if(result.exception != nil) {
				print("failed send code \(String(describing: result.exception))")
				loading = .failure
				
			}
			
			
			if let resultData = result.data as? Bool {
				
				loading = .success
				isSuccessMessage = resultData
				
				
			}
			
		}
		
	}
	
	
	
	
	
}
