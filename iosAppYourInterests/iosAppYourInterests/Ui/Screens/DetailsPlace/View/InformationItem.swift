//
//  InformationItem.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 25/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct InformationItem<Content:View>: View {
	
	let  title: String
	let subTitle: String
	let view: () -> Content	
	var body: some View {
		VStack {
			HStack {
				view()
					.padding([.trailing],10)
				VStack(alignment: .leading) {
					Text(title)
						.font(.custom(FontsApp.semiBold, size: 18))
						.foregroundStyle(ColorsApp.blue)
					Text(subTitle)
						.font(.custom(FontsApp.regular, size: 16))
						.foregroundStyle(ColorsApp.black)
				}
			}
		}
		
	}
}

#Preview {
	InformationItem( title: "Distancia", subTitle:  "33") {
		Text("oi")
	}
}
