//
//  CustomAsyncImage.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 12/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct CustomAsyncImage<Content: View, Placeholder: View>: View {
	
	@State var uiImage: UIImage?
	let urlRequest: String
 	let content: (Image) -> Content
	let placeholder: () -> Placeholder
	
	func getImage() async -> UIImage? {
			guard let url = URL(string: urlRequest) else {
					fatalError("Missing URL")
			}
			
			var request = URLRequest(url: url)
		request.addValue("fsq3/wGlSy43PonkmMjkBBRg4+6tzLHXGbr6zc7JhDOHIuE=", forHTTPHeaderField: "Authorization")
			
			do {
					let (data, response) = try await URLSession.shared.data(for: request)
					
					guard (response as? HTTPURLResponse)?.statusCode == 200 else { fatalError("Error while fetching attachment") }
					
					return UIImage(data: data)
			} catch {
					return nil
			}
	}
	
	var body: some View {
		if let uiImage = uiImage {
			content(Image(uiImage: uiImage))
		}else {
			placeholder()
				.task {
					self.uiImage = await getImage()
				}
		}
	}
}

