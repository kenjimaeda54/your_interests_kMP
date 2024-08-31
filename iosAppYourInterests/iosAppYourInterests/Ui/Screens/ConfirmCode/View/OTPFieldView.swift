//
//  InputCode.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 29/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//



import SwiftUI
import Combine


// A SwiftUI view for entering OTP (One-Time Password).
struct OTPFieldView: View {
	  var size: CGFloat
		@FocusState private var pinFocusState: FocusPin?
		@Binding private var otp: String
		@State private var pins: [String]
		
		var numberOfFields: Int
		
		enum FocusPin: Hashable {
				case pin(Int)
		}
		
	init(numberOfFields: Int, otp: Binding<String>,size: CGFloat) {
				self.numberOfFields = numberOfFields
				self._otp = otp
				self._pins = State(initialValue: Array(repeating: "", count: numberOfFields))
		    self.size = size
		}
		
		var body: some View {
				HStack(spacing: 15) {
						ForEach(0..<numberOfFields, id: \.self) { index in
								TextField("", text: $pins[index])
								.modifier(OtpModifier(size: size, pin: $pins[index]))
										.foregroundColor(ColorsApp.black)
										.onChange(of: pins[index]) { newVal in
												if newVal.count == 1 {
														if index < numberOfFields - 1 {
																pinFocusState = FocusPin.pin(index + 1)
														} else {
															pinFocusState = nil
														}
												}
												else if newVal.count == numberOfFields, let intValue = Int(newVal) {
														otp = newVal
														updatePinsFromOTP()
														pinFocusState = FocusPin.pin(numberOfFields - 1)
												}
												else if newVal.isEmpty {
														if index > 0 {
																pinFocusState = FocusPin.pin(index - 1)
														}
												}
												updateOTPString()
										}
										.focused($pinFocusState, equals: FocusPin.pin(index))
										.onTapGesture {
												// Set focus to the current field when tapped
												pinFocusState = FocusPin.pin(index)
										}
						}
				}
				.onAppear {
						// Initialize pins based on the OTP string
						updatePinsFromOTP()
				}
		}
		
		private func updatePinsFromOTP() {
				let otpArray = Array(otp.prefix(numberOfFields))
				for (index, char) in otpArray.enumerated() {
						pins[index] = String(char)
				}
		}
		
		private func updateOTPString() {
				otp = pins.joined()
		}
}

struct OtpModifier: ViewModifier {
	let size: CGFloat
	
		@Binding var pin: String
		
		var textLimit = 1
		
		func limitText(_ upper: Int) {
				if pin.count > upper {
						self.pin = String(pin.prefix(upper))
				}
		}
		
		func body(content: Content) -> some View {
				content
						.multilineTextAlignment(.center)
						.keyboardType(.numberPad)
						.onReceive(Just(pin)) { _ in limitText(textLimit) }
						.frame(width: size, height: size)
						.font(.system(size: 14))
						.background(
								RoundedRectangle(cornerRadius: 2)
									.stroke(ColorsApp.black.opacity(0.3), lineWidth: 1)
						)
		}
}

struct OTPFieldView_Previews: PreviewProvider {
		
		static var previews: some View {
				
				VStack(alignment: .leading, spacing: 8) {
						Text("VERIFICATION CODE")
								.foregroundColor(Color.gray)
								.font(.system(size: 12))
					OTPFieldView(numberOfFields: 5, otp: .constant("54321"), size: 48)
								.previewLayout(.sizeThatFits)
				}
		}
}


