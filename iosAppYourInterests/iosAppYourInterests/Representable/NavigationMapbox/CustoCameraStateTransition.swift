//
//  CustoCameraStateTransition.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 16/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

//import Foundation
//import MapboxMaps
//import MapboxNavigationCore
//
//
//class CustomCameraStateTransition: CameraStateTransition {
//		weak var mapView: MapView?
//
//		required init(_ mapView: MapView) {
//				self.mapView = mapView
//		}
//
//		func transitionTo(_ cameraOptions: CameraOptions, completion: @escaping (() -> Void)) {
//				mapView?.camera.ease(to: cameraOptions, duration: 0.5, curve: .linear, completion: { _ in
//						completion()
//				})
//		}
//
//		func update(to cameraOptions: CameraOptions, state: NavigationCameraState) {
//				mapView?.camera.ease(to: cameraOptions, duration: 0.5, curve: .linear, completion: nil)
//		}
//
//		func cancelPendingTransition() {
//				mapView?.camera.cancelAnimations()
//		}
//}
