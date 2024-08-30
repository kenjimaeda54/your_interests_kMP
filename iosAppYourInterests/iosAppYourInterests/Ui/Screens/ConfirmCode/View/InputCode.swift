//
//  InputCode.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 29/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

@available(iOS 17.0, *)
struct InputCode: View {
	 
	@Binding   var text: String
	let width: CGFloat
	let height: CGFloat
	
	
	var body: some View {
		
		TextField("", text: $text)
			.font(.custom(FontsApp.regular, size: 17))
			.foregroundStyle(ColorsApp.black)
			.multilineTextAlignment(.center)
			.frame(width:  width,height:  height,alignment: .center)
			.background(
				RoundedRectangle(cornerRadius: 5, style: .continuous)
					.stroke(ColorsApp.black,lineWidth: 0.5)
				
			)
			 
		
	}
}

@available(iOS 17.0, *)
#Preview {
	InputCode(text: .constant(""),  width: 60, height: 60) 
}
