//
//  RedactShimmerViewModifier.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 21/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI

public struct RedactAndShimmerView: ViewModifier {
	private let condition: Bool

	init(condition: Bool) {
		self.condition = condition
	}

	public func body(content: Content) -> some View {
		if condition {
			content
				.redacted(reason: .placeholder)
				.shimmering()
		} else {
			content
		}
	}
}

public extension View {
	func redactShimmer(condition: Bool) -> some View {
		modifier(RedactAndShimmerView(condition: condition))
	}
}
