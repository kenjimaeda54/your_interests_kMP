//
//  ButtonDefault.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 06/09/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ButtonDefault: View {
	let  action: () -> ()
	let title: String
	let isDisable: Bool
	var body: some View {
		
		Button(action: action){
			Text(title)
				.font(.custom(FontsApp.light, size: 18))
				.foregroundStyle(ColorsApp.white)
		}
		.disabled(isDisable)
		.padding([.vertical],7)
		.frame(minWidth: 0,maxWidth: .infinity)
		.background( isDisable ? ColorsApp.gray.clipShape(RoundedRectangle(cornerRadius: 10)) :	ColorsApp.black.clipShape(RoundedRectangle(cornerRadius: 10)))
	}
	
}

#Preview {
	ButtonDefault(action: {}, title: "Entrar", isDisable: true)
}
