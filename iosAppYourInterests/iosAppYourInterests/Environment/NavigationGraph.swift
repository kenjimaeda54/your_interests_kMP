//
//  NavigationGraphStack.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 28/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation


//navegar sem navigation stack
//https://kevin-harijanto.medium.com/swiftui-navigation-without-navigation-view-274c50b3d143
class NavigationGraph: ObservableObject {
	
	enum CurrentView: Int {
		case  singUp,confirmCode,finishedRegister,splashScreen,tabCustomView
	}
	
	@Published var switchView = CurrentView.splashScreen
	
}
