//
//  CustomListContent.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 21/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct CustomListContent<Content : View>: View {
	let viewBuilder: () -> Content
	
	
    var body: some View {
			List {
				 viewBuilder()
					.listRowInsets(EdgeInsets())
					.listRowBackground(Color.clear)
					.listRowSeparator(.hidden)
			}
			.padding([.top],30)
			.listStyle(.plain)
			.scrollContentBackground(.hidden)
			.scrollIndicators(.hidden)
			.listRowSpacing(15)
		}
}

 
