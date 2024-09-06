//
//  CompletedRegisterUserScreen.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 28/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Combine
import MijickCameraView
import shared

@available(iOS 17.0, *)
struct CompletedRegisterUserScreen: View {
	@Binding var phone: String
	@State private var showSheet = false
	@State private var userName = ""
	@FocusState private var focus: Bool
	@State private var isShowCamera = false
	@State private var photo: Data = Data()
	@ObservedObject private var cameraManager:  CameraManager = .init(
		outputType: .photo,
		cameraPosition: .front,
		flashMode: .auto
	)
	@StateObject private var stateRegisterUser: CompletedRegisterUserState = CompletedRegisterUserState()
	//para transformar bytearray in swift criei uma extensao
	private var user: UserWithPhotoByTeArray {
		return UserWithPhotoByTeArray( name: userName, phone: phone, photo: KotlinByteArray.from(data: photo)
		)}
	
	var body: some View {
		VStack {
			if(isShowCamera) {
				MCameraController(manager: cameraManager)
					.onImageCaptured { image in
						if let data = image.pngData() {
							photo = data
							Task {
								await stateRegisterUser.registerUser(user: user)
							}
							
						}
						isShowCamera = false
						
					}
					.cameraScreen(CustomCameraView.init)
					.onCloseController {
						isShowCamera = false
					}
			}else {
				Button(action: {
					isShowCamera = true
				}, label: {
					Image("photo_user")
						.resizable()
						.frame(width: 80,height: 80)
						.clipShape(/*@START_MENU_TOKEN@*/Circle()/*@END_MENU_TOKEN@*/)
					
				})
				Text("Clique acima para trocar a foto")
					.font(.custom(FontsApp.light, size: 18))
					.foregroundStyle(ColorsApp.black)
				
				
				VStack(alignment: .leading,spacing: 5) {
					HStack {
						Text("Nome:")
							.font(.custom(FontsApp.regular, size: 20))
							.foregroundStyle(ColorsApp.black)
						
						Button(action: {
							showSheet = true
							focus = true
						}, label: {
							Text("Clique aqui para inserir nome")
								.font(.custom(FontsApp.light, size: 18))
								.foregroundStyle(ColorsApp.black.opacity(0.8))
							
						})
						
					}
					HStack {
						Text("Telefone:")
							.font(.custom(FontsApp.regular, size: 20))
							.foregroundStyle(ColorsApp.black)
						
						
						Text(phone)
							.font(.custom(FontsApp.bold,  size: 20))
							.foregroundStyle(ColorsApp.black)
						
						
					}
				}
				.padding([.top],30)
				.padding(.horizontal, 20)
				
			}
		}
		.frame(minWidth: /*@START_MENU_TOKEN@*/0/*@END_MENU_TOKEN@*/,maxWidth: .infinity, minHeight: 0,maxHeight: .infinity)
		.background(ColorsApp.gray.opacity(0.7))
		.sheet(isPresented: $showSheet){
			TextField("", text: $userName, prompt: Text("ex: Joao da Silva")
				.font(.custom(FontsApp.light, size: 18))
				.foregroundStyle(ColorsApp.black.opacity(0.7))
			)
			.focused($focus)
			.font(.custom(FontsApp.regular, size: 18))
			.foregroundStyle(ColorsApp.black)
			.padding(EdgeInsets(top: 7,leading: 10,bottom: 7,trailing: 10))
			.background(
				RoundedRectangle(cornerRadius: 7)
					.stroke(ColorsApp.black.opacity(0.7),lineWidth: 1)
			)
			.presentationDetents([.fraction(0.15)])
			.presentationDragIndicator(.hidden)
			.padding([.leading,.trailing],20)
			.onReceive(Just(userName), perform: { _ in
				limitText()
			})
		}
	}
}


@available(iOS 17.0, *)
extension CompletedRegisterUserScreen {
	
	func limitText()  {
		if userName.count > 10 {
			userName = String(userName.prefix(9))
		}
	}
	
	
}

@available(iOS 17.0, *)
#Preview {
	CompletedRegisterUserScreen(phone: .constant("+5535999733136"))
}
