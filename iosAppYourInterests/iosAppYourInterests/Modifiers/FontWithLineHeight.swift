//
//  FontWithLineHeight.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 02/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct FontWithLineHeight: ViewModifier  {
	let font: UIFont
	let lineHeight: CGFloat
	
	func body(content: Content) -> some View {
		content
			.font(Font(font))
			.lineSpacing(lineHeight - font.lineHeight)
			.padding(.vertical,(lineHeight - font.lineHeight) / 2)
	}
}

extension View {
	func fontWithLineHeight(font: UIFont,lineHeight: CGFloat) -> some View {
		ModifiedContent(content: self, modifier: FontWithLineHeight(font: font,lineHeight: lineHeight))
	}
}


