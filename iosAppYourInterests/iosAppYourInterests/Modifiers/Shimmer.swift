//
//  Shimmer.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 21/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI


public struct Shimmer: ViewModifier {
	@State private var phase: CGFloat = 0
	var duration = 1.5
	var bounce = false

	public func body(content: Content) -> some View {
		content
			.modifier(AnimatedMask(phase: phase).animation(
				Animation.linear(duration: duration)
					.repeatForever(autoreverses: bounce)
			))
			.onAppear { phase = 0.8 }
	}

	/// An animatable modifier to interpolate between `phase` values.
	struct AnimatedMask: AnimatableModifier {
		var phase: CGFloat = 0

		var animatableData: CGFloat {
			get { phase }
			set { phase = newValue }
		}

		func body(content: Content) -> some View {
			content
				.mask(GradientMask(phase: phase).scaleEffect(3))
		}
	}

	/// A slanted, animatable gradient between transparent and opaque to use as mask.
	/// The `phase` parameter shifts the gradient, moving the opaque band.
	struct GradientMask: View {
		let phase: CGFloat
		let centerColor = ColorsApp.gray
		let edgeColor = ColorsApp.black.opacity(0.7)

		var body: some View {
			LinearGradient(
				gradient:
				Gradient(stops: [
					.init(color: edgeColor, location: phase),
					.init(color: centerColor, location: phase + 0.1),
					.init(color: edgeColor, location: phase + 0.2)
				]),
				startPoint: .topLeading,
				endPoint: .bottomTrailing
			)
		}
	}
}

public extension View {

	@ViewBuilder func shimmering(
		active: Bool = true, duration: Double = 1.5, bounce: Bool = false
	) -> some View {
		if active {
			modifier(Shimmer(duration: duration, bounce: bounce))
		} else {
			self
		}
	}
}

#if DEBUG
struct Shimmer_Previews: PreviewProvider {
	static var previews: some View {
		Group {
			Text("SwiftUI Shimmer")
			if #available(iOS 14.0, macOS 11.0, tvOS 14.0, watchOS 7.0, *) {
				Text("SwiftUI Shimmer").preferredColorScheme(.light)
				Text("SwiftUI Shimmer").preferredColorScheme(.dark)
				VStack(alignment: .leading) {
					Text("Loading...").font(.title)
					Text(String(repeating: "Shimmer", count: 12))
						.redacted(reason: .placeholder)
				}.frame(maxWidth: 200)
			}
		}
		.padding()
		.shimmering()
		.previewLayout(.sizeThatFits)
	}
}
#endif
