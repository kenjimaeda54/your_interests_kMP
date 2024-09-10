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
class AuthState: ObservableObject {
	@Published  var loadingSendCode: LoadingState = .none
	@Published  var loadingVerifiCode: LoadingState = .none
	var isSuccessMessage: Bool = false
	var verifyCode: Bool = false
	private var viewModel = AuthSapabaseViewModel()
	
	
	func verifyCode(with phone: String,code: String) async {
		loadingVerifiCode = .loading
		viewModel.verifyCodeOTP(phone: phone, code: code)
		
		for await verify in viewModel.successVerifyCodeOTP {
			
			if(verify.exception != nil) {
				print("failed verify code \(String(describing: verify.exception))")
				loadingVerifiCode = .failure
			}
			
			if let data = verify.data as? Bool {
				loadingVerifiCode = .success
				verifyCode = data
				
			}
		}
	}
	
	func sendCode(with phone: String) async {
		loadingSendCode = .loading
		viewModel.sendCodeOTP(phone: phone)
		
		for await result in viewModel.successSendCodeOTP {
			
			if(result.exception != nil) {
				print("failed send code \(String(describing: result.exception))")
				loadingSendCode = .failure
				
			}
			
			
			if let resultData = result.data as? Bool {
				
				loadingSendCode = .success
				isSuccessMessage = resultData
				
				
			}
			
		}
		
	}
}
