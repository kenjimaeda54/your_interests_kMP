//
//  Loading.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 03/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct Loading: View {
	@State private var degree:Int = 270
	@State private var spinnerLength = 0.6
 	var body: some View {
		ZStack{
			VStack{
				
				Circle()
					.trim(from: 0.0,to: spinnerLength)
					.stroke(LinearGradient(colors: [ColorsApp.gray,ColorsApp.blue], startPoint: .topLeading, endPoint: .bottomTrailing),style: StrokeStyle(lineWidth: 8.0,lineCap: .round,lineJoin:.round))
					.frame(width: 30,height: 30)
					.animation(Animation.easeIn(duration: 1.5).repeatForever(autoreverses: true),value: UUID() )
					.rotationEffect(Angle(degrees: Double(degree)))
					.animation(Animation.linear(duration: 1).repeatForever(autoreverses: false),value: UUID() )
					.onAppear{
						degree = 270 + 360
						spinnerLength = 0
					}
				
			}
		}
	}
}


