//
//  RowItemShimmer.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 21/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct RowItemShimmer: View {
    var body: some View {
			HStack(spacing: 15) {
				AsyncImage(url: URL(string: ""))
					.clipShape(RoundedRectangle(cornerRadius: 10))
					.frame(width: 40,height: 40)
					.redactShimmer(condition: true)
				
				Text("Shimmer place holder")
					.redactShimmer(condition: true)
			}
			.frame(minWidth: 0,maxWidth: .infinity, minHeight: 0,maxHeight: .infinity,alignment: .leading)
    }
}

#Preview {
    RowItemShimmer()
}
