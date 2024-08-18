//
//  NavigationViewController.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 16/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import CoreLocation
import MapboxMaps
import MapboxNavigationUIKit

struct NavigationMapControllerRepresentable: UIViewControllerRepresentable  {
	//ao colocar viewcontroller os outros mebmros ja vemm com retorno desse tipo
	//incluse no updateViewController jja vem com o tipo tambem
	//https://github.com/kenjimaeda54/concepts-swift

	typealias UIViewControllerType = UIViewController
	
	
	@Binding var origin: CLLocationCoordinate2D
	@Binding var destination: CLLocationCoordinate2D

	func makeUIViewController(context: Context) -> UIViewController {
		let viewController = NavigationMapControllerContent()
		viewController.destination = destination
		viewController.origin = origin
		
		return viewController
	}
	
	func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
		
	}

}

