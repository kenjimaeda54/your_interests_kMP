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
	@State private var image: Image?
	@State private var photo: Data = Data()
	@EnvironmentObject private var grapNavigation: NavigationGraph
	@ObservedObject private var cameraManager:  CameraManager = .init(
		outputType: .photo,
		cameraPosition: .front,
		flashMode: .auto
	)
	@StateObject private var stateRegisterUser: CompletedRegisterUserState = CompletedRegisterUserState()
	//para transformar bytearray in swift criei uma extensao
	private var user: UserWithPhotoByTeArray {
		return UserWithPhotoByTeArray( name: userName, phone: "+55\(phone)", photo: KotlinByteArray.from(data: photo)
		)}
	
	var body: some View {
		NavigationStack {
			VStack {
				if(isShowCamera) {
					MCameraController(manager: cameraManager)
						.onImageCaptured { image in
							if let data = image.pngData() {
								photo = data
								
							}
							self.image = Image(uiImage: image)
							isShowCamera = false
							
						}
						.cameraScreen(CustomCameraView.init)
						.onCloseController {
							isShowCamera = false
						}
				}else {
					Spacer()
					Button(action: {
						isShowCamera = true
					}, label: {
						
						returnUserImage()
						
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
								Text(userName.isEmpty ?  "Clique aqui para inserir nome" : userName)
									.font(.custom(FontsApp.light, size: 18))
									.foregroundStyle(ColorsApp.black.opacity(0.8))
								
							})
							
						}
						HStack {
							Text("Telefone:")
								.font(.custom(FontsApp.regular, size: 20))
								.foregroundStyle(ColorsApp.black)
							
							
							Text("+55\(phone)")
								.font(.custom(FontsApp.bold,  size: 20))
								.foregroundStyle(ColorsApp.black)
							
							
						}
						
					}
					.padding([.top],30)
					.padding(.horizontal, 20)
					Spacer()
					if(stateRegisterUser.loading == .loading) {
						ProgressView()
							.tint(ColorsApp.black)
							.frame(width: 100,height: 100)
							.controlSize(.large)
					}else {
						ButtonDefault(
							action: {
								Task {
									await stateRegisterUser.registerUser(user: user)
								}
							}, title: "Confirmar", isDisable: userName.isEmpty || photo.isEmpty
						)
						.padding([.horizontal], 13)
						.padding(.bottom,20)
						
					}
				}
				
			}
			
			
		}
		.onChange(of: stateRegisterUser.isSuccessRegisterUser) { _,newValue in
			if(newValue) {
				grapNavigation.switchView = .tabCustomView
			}
		}
		.frame(minWidth: /*@START_MENU_TOKEN@*/0/*@END_MENU_TOKEN@*/,maxWidth: .infinity, minHeight: 0,maxHeight: .infinity)
		.background(ColorsApp.gray.opacity(0.7))
		.sheet(isPresented: $showSheet){
			TextField("", text: $userName, prompt: Text("ex: Joao da Silva")
				.font(.custom(FontsApp.light, size: 18))
				.foregroundStyle(ColorsApp.black.opacity(0.7))
			)
			.submitLabel(.done)
			.onSubmit {
				focus = false
				showSheet = false
			}
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
	
	func returnUserImage() ->  some View {
		
		if let image = image {
			image
				.resizable()
				.frame(width: 80,height: 80)
				.clipShape(/*@START_MENU_TOKEN@*/Circle()/*@END_MENU_TOKEN@*/)
			
			
		} else {
			Image("photo_user")
				.resizable()
				.frame(width: 80,height: 80)
				.clipShape(/*@START_MENU_TOKEN@*/Circle()/*@END_MENU_TOKEN@*/)
			
		}
		
	}
	
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
