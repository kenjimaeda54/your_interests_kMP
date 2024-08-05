//
//  ContentAlignCenter.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 03/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI


struct  CenterModifier: ViewModifier {
	func body(content: Content) -> some View {
		//para ficar no centro envolve com hstack e coloca spacin em volta
		HStack {
			Spacer()
			content
			Spacer()
		}
		
	}
	
}
