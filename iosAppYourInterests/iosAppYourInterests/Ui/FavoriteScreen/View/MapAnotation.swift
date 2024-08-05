//
//  MapAnotation.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 02/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct MapAnotation: View {
	var anotationMap: AnotationMap
	var geometryProxy: GeometryProxy
	
	var body: some View {
		//precisa de ser ZStack para anotacao ficar mais proxima do ponto de origem
		ZStack {
			Image("pin")
				.resizable()
				.frame(width: 30,height: 30)
				.rotation3DEffect(.degrees(3), axis: (x: 0, y: 0, z: 50))
				.offset(x: 0, y: -10)
				.padding([.bottom],geometryProxy.size.height * 0.07)
 			Text("\(anotationMap.street),\(anotationMap.streetNumber)")
				.font(.custom(FontsApp.light, size: 13))
				.foregroundStyle(ColorsApp.white)
				.padding([.horizontal],13)
				.padding([.vertical],5)
				.background(
					ColorsApp.green
						.clipShape(RoundedRectangle(cornerRadius: 10))
				)
		}
		.frame(minWidth: geometryProxy.size.width)
	}
}


