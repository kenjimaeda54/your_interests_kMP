import SwiftUI

@available(iOS 17.0, *)
@main
struct iOSApp: App {
	var body: some Scene {
		
 
	
		
		WindowGroup {
			 TabCustomView()
				.environmentObject(StateNavigationTabView())
		}
 
	}
}
