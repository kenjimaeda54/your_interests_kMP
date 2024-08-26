//
//  ButtonBackTop.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 25/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ButtonBackTopAppBar: View {
	  @Environment(\.dismiss) var dimiss
	
    var body: some View {
			HStack {
				Button( action: actionBackButton ) {
					Image(systemName: "chevron.left")
						.resizable()
						.frame(width: 15,height: 15)
						.foregroundColor(ColorsApp.white)
				}
				.padding([.leading],30)
				Spacer()
			}
    }
}

extension ButtonBackTopAppBar {
	func actionBackButton() {
		dimiss()
	}
}



