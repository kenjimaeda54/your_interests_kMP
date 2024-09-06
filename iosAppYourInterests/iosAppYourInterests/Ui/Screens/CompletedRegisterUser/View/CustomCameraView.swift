//
//  CustomCameraPreview.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 04/09/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import MijickCameraView

struct CustomCameraView: MCameraView {
	var cameraManager: MijickCameraView.CameraManager
	var namespace: Namespace.ID
	var closeControllerAction: () -> ()
	@State private var cameraPositionInternal: CameraPosition = .back
	
	
	var body: some View {
		VStack {
			HStack {
				Image(systemName: "chevron.backward")
					.frame(width: 50,height: 50)
					.foregroundStyle(ColorsApp.gray)
					.onTapGesture {
						closeControllerAction()
					}
				Spacer()
				
				Image(systemName: "repeat")
					.frame(width: 50,height: 50)
					.foregroundStyle(ColorsApp.gray)
					.onTapGesture {resolveChangeCamera()}
				
			}
			.frame(maxWidth: /*@START_MENU_TOKEN@*/.infinity/*@END_MENU_TOKEN@*/,maxHeight: 80)
			createCameraView()
			VStack(alignment: .center) {
				Circle()
					.foregroundStyle(ColorsApp.gray)
					.onTapGesture {
						captureOutput()
					}
			}
			.frame(maxWidth: /*@START_MENU_TOKEN@*/.infinity/*@END_MENU_TOKEN@*/,maxHeight:  80)
			
			
		}
		.background(ColorsApp.black)
		.frame(minHeight: 0,maxHeight: .infinity)
	}
}


extension CustomCameraView  {
	
	func resolveChangeCamera()  {
		do {
			try changeCamera(cameraPosition == .back ? .front : .back)
		}catch {
			print(error)
		}
		
	}
	
}

