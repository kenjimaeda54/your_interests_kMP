//
//  NavigationViewRepresentable.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 15/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//
/*
 This code example is part of the Mapbox Navigation SDK for iOS demo app,
 which you can build and run: https://github.com/mapbox/mapbox-navigation-ios-examples
 To learn more about each example in this app, including descriptions and links
 to documentation, see our docs: https://docs.mapbox.com/ios/navigation/examples/basic
 */

//import Foundation
//import UIKit
//import MapboxDirections
//import MapboxNavigationCore
//import CoreLocation
//import MapboxNavigationUIKit
//import SwiftUI
// 
//
//class NavigationMapControllerContent: UIViewController {
//	var destination: CLLocationCoordinate2D = CLLocationCoordinate2D(latitude: 0.0, longitude: 0.0)
//	var origin: CLLocationCoordinate2D = CLLocationCoordinate2D(latitude: 0.0, longitude: 0.0)
//	let mapboxNavigationProvider = MapboxNavigationProvider(coreConfig: .init())
//	lazy var mapboxNavigation = mapboxNavigationProvider.mapboxNavigation
//	
//	
//	var navigationMapView: NavigationMapView! {
//		didSet {
//			if oldValue != nil {
//				oldValue.removeFromSuperview()
//			}
//			navigationMapView.translatesAutoresizingMaskIntoConstraints = false
//			navigationMapView.routeAnnotationSelectedColor = UIColor(ColorsApp.green)
//			navigationMapView.showsAlternatives = true
//			navigationMapView.delegate = self
//			
//			view.insertSubview(navigationMapView, at: 0)
//			
//			NSLayoutConstraint.activate([
//				navigationMapView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
//				navigationMapView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
//				navigationMapView.topAnchor.constraint(equalTo: view.topAnchor),
//				navigationMapView.bottomAnchor.constraint(equalTo: view.bottomAnchor),
//			])
//		}
//	
//	}
//	
//	var startNavigationButton: UIButton!
//
//		var navigationRoutes: NavigationRoutes? {
//				didSet {
//						showCurrentRoute()
//				}
//		}
//
//		func showCurrentRoute() {
//				guard let navigationRoutes else {
//						navigationMapView.removeRoutes()
//						return
//				}
//				navigationMapView.showcase(navigationRoutes)
//		}
//	
//	
//	override func viewDidLoad() {
//		super.viewDidLoad()
//		setupNavigationView()
//		trackerLocation()
//		setupStartNavigationButton()
//		mapboxNavigation.tripSession().startFreeDrive()
//	}
//	
//	func setupStartNavigationButton() {
//				startNavigationButton = UIButton()
//				startNavigationButton.setTitle("Start Navigation", for: .normal)
//				startNavigationButton.translatesAutoresizingMaskIntoConstraints = false
//				startNavigationButton.backgroundColor = .blue
//				startNavigationButton.contentEdgeInsets = UIEdgeInsets(top: 10, left: 20, bottom: 10, right: 20)
//				startNavigationButton.addTarget(self, action: #selector(startNavigationButtonPressed(_:)), for: .touchUpInside)
//				startNavigationButton.isHidden = true
//
//				view.addSubview(startNavigationButton)
//
//				startNavigationButton.bottomAnchor.constraint(equalTo: view.safeAreaLayoutGuide.bottomAnchor, constant: -20)
//						.isActive = true
//				startNavigationButton.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
//		}
//
//		@objc
//		func startNavigationButtonPressed(_ sender: UIButton) {
//				guard let navigationRoutes else { return }
//
//				let navigationOptions = NavigationOptions(
//						mapboxNavigation: mapboxNavigation,
//						voiceController: mapboxNavigationProvider.routeVoiceController,
//						eventsManager: mapboxNavigationProvider.eventsManager(),
//						predictiveCacheManager: mapboxNavigationProvider.predictiveCacheManager,
//						// Replace default `NavigationMapView` instance with instance that is used in preview mode.
//						navigationMapView: navigationMapView
//				)
//				let navigationViewController = NavigationViewController(
//						navigationRoutes: navigationRoutes,
//						navigationOptions: navigationOptions
//				)
//				navigationViewController.delegate = self
//				navigationViewController.modalPresentationStyle = .fullScreen
//
//				startNavigationButton.isHidden = true
//				present(navigationViewController, animated: true, completion: nil)
//		}
//	
//	
//	override func viewDidLayoutSubviews() {
//				super.viewDidLayoutSubviews()
//
//				startNavigationButton.layer.cornerRadius = startNavigationButton.bounds.midY
//				startNavigationButton.clipsToBounds = true
//				startNavigationButton.setNeedsDisplay()
//		}
//
//
//		func setupNavigationMapView() {
//				navigationMapView = .init(
//						location: mapboxNavigation.navigation().locationMatching.map(\.enhancedLocation)
//								.eraseToAnyPublisher(),
//						routeProgress: mapboxNavigation.navigation().routeProgress.map(\.?.routeProgress).eraseToAnyPublisher(),
//						predictiveCacheManager: mapboxNavigationProvider.predictiveCacheManager
//				)
//
//		
//				let navigationCamera = navigationMapView.navigationCamera
//				navigationCamera.viewportDataSource = CustomViewportDataSource(navigationMapView.mapView)
//				navigationCamera.cameraStateTransition = CustomCameraStateTransition(navigationMapView.mapView)
//		}
//	
//	
//	func trackerLocation() {
//		let origin = origin
//		let destination = destination
//		let options = NavigationRouteOptions(coordinates: [origin, destination])
//	let request = mapboxNavigation.routingProvider().calculateRoutes(options: options)
//	Task {
//		switch await request.result {
//			case .failure(let error):
//					 print("error navigation,\(error)")
//				
//				
//			case .success(let navigationRoutes):
//				let navigationOptions = NavigationOptions(mapboxNavigation: mapboxNavigation, voiceController: mapboxNavigationProvider.routeVoiceController, eventsManager: mapboxNavigationProvider.eventsManager(),
//				styles: [CustomStandardStyle()]
//				
//				)
//				let navigationViewController = NavigationViewController(navigationRoutes: navigationRoutes, navigationOptions: navigationOptions)
//				navigationViewController.modalPresentationStyle = .overFullScreen
//				 
//				view.addSubview(navigationViewController.view)
//		}
//		
//	}
//		
//		
//	}
//	
//	
//	func setupNavigationView() {
//		navigationMapView = .init(
//								location: mapboxNavigation.navigation().locationMatching.map(\.enhancedLocation)
//										.eraseToAnyPublisher(),
//								routeProgress: mapboxNavigation.navigation().routeProgress.map(\.?.routeProgress).eraseToAnyPublisher(),
//								predictiveCacheManager: mapboxNavigationProvider.predictiveCacheManager
//						)
//
//						// Modify default `NavigationViewportDataSource` and `NavigationCameraStateTransition` to change
//						// `NavigationCamera` behavior during free drive and when locations are provided by Maps SDK directly.
//						let navigationCamera = navigationMapView.navigationCamera
//						navigationCamera.viewportDataSource = CustomViewportDataSource(navigationMapView.mapView)
//		        navigationCamera.cameraStateTransition = CustomCameraStateTransition(navigationMapView.mapView)
//	}
//}
//
//
//
//extension NavigationMapControllerContent: NavigationMapViewDelegate {
//		func navigationMapView(_ navigationMapView: NavigationMapView, userDidLongTap mapPoint: MapPoint) {
//			
//		}
//}
//
//extension NavigationMapControllerContent: NavigationViewControllerDelegate {
//		func navigationViewControllerDidDismiss(
//				_ navigationViewController: NavigationViewController,
//				byCanceling canceled: Bool
//		) {
//				navigationViewController.dismiss(animated: false) { [weak self] in
//						guard let self else { return }
//
//						navigationMapView = navigationViewController.navigationMapView
//						navigationMapView.removeRoutes()
//						let ornaments = navigationMapView.mapView.ornaments
//						ornaments?.options.logo.margins = .init(x: 8.0, y: 8.0)
//						ornaments?.options.attributionButton.margins = .init(x: 8.0, y: 8.0)
//				}
//		}
//}
