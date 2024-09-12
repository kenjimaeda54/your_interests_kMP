## Seus interesses
Aplicativo possibilita trazer os pontos de interesse próximo da sua localização,   consultar um ponto de interesse pela barra de pesquisa(telefone, nome ou categoria) por fim traz os dados do usuário cadastrado

## Feature
- Precisei usar bear token na API para isto implementei diretamente no ktor

```kotlin
class KtorApiImplementation() : KtorApi {
    private val apiKey = BuildConfig.API_KEY
    private val apiURl = "https://api.foursquare.com"


    override val client = HttpClient {
        defaultRequest {
            url(apiURl)
            url {
                protocol = URLProtocol.HTTPS
            }
            headers {
                append("Authorization", apiKey)
            }
        }
        //https://ktor.io/docs/client-basic-auth.html auth no ktor

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
            })
        }
    }


}

```

##
- Dentro do mapbox precisava mostrar vários pontos de interesse se usar lazyColumn com algum valor opcional ira acusar erro de LayoutNode
- Por isso optei pelo uso do forEach como abaixo
- Se for uma lista grande não ideal uso do forEach


```kotlin
 dataPlaces.forEach {

                            ViewAnnotation(options = viewAnnotationOptions {
                                allowOverlap(false)
                                geometry(
                                    Point.fromLngLat(
                                        it.places.geocode.longitude,
                                        it.places.geocode.latitude
                                    )
                                )
                            }) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(5.dp)
                                ) {
                                    if (it.places.name.isEmpty())
                                        Box(modifier = Modifier)
                                    else
                                        AsyncImage(
                                            model =
                                            ImageRequest.Builder(context = LocalContext.current)
                                                .data(it.photoPlacesModel.icon)
                                                .build(),
                                            contentDescription = "Image marker",
                                            contentScale = ContentScale.FillBounds,
                                            filterQuality = FilterQuality.High,
                                            modifier = Modifier
                                                .size(40.dp)
                                                .clip(
                                                    CircleShape
                                                )
                                        )
                                    Text(
                                        text = it.places.name,
                                        modifier = Modifier
                                            .background(
                                                MaterialTheme.colorScheme.tertiary,
                                                shape = RoundedCornerShape(corner = CornerSize(10.dp))
                                            )
                                            .padding(5.dp),
                                        color = MaterialTheme.colorScheme.onTertiary,
                                        fontFamily = fontsKulimPark,
                                        fontWeight = FontWeight.Light,
                                        fontSize = 13.sp

                                    )

                                }
                            }
                        }



```

##
- Para trabalhar com lottie usei uma lib do [airbnb](https://airbnb.io/lottie/#/android-compose)
- Esta lib ótima, porque consigo usar no IOS também
- Quando preciso navegar para as tabs posso usar mesmo conceito da stack usando navController.navigate(dai insiro a primeira rota neste caso de uso é o NearbyInterests)
- Quando se trabalha com mabpox, caso precisa alterar o estilo recomendo o uso do MapBox Studio, com essa ferramenta consegue remover os pontos de interesse que vem por padrão é altear outros recursos
- Para recuperar os dados da viewModel usando injeção dependência segue o exemplo abaixo
- As rotas stack e bottom se diferem por isso não consigo usar mesma viewModel através delas  no meu projeto precisei novamente chamar a viewModel responsável pelos user na primeira tela da bottomTab
- Depois consigo pegar os dados normalmente pelo getBackStackEntry
- Recordando que se for usar passagem de dados pelas rotas precisam ser dados simples não complexo

```kotlin
 //na camada de apresentaçao que desejo usar o dado
 val userViewModel = viewModel<UserSapabaseViewModel>()

//na camada do navgraph

composable(
                StackScreens.DetailsPlace.name + "/{fsqId}",
                arguments = listOf(navArgument("fsqId") { type = NavType.StringType })
 ) {
   val parentSearchRoute = remember(it) {
      navController.getBackStackEntry(BottomBarScreen.Search.route)
   }

   val place = viewModel<SearchPlacesByQueryViewModel>(parentSearchRoute)
   if (place.placesByQuery.value.data == null) return@composable
   val findPlace = place.placesByQuery.value.data!!.find { placeByQuery ->
                    placeByQuery.fsqId == it.arguments?.getString("fsqId")
    }
        if (findPlace == null) return@composable
           SharedDetailsPlace(
                    place = findPlace,
                    navController = navController
      )
   }

//navegar para bootomtab

 navController.navigate(BottomBarScreen.NearbyInterests.route) {
                popUpTo(StackScreens.SplashScreen.name) {
                    inclusive = true
                }
            }
```

##
- Quando precisar navegar sem o NavigatorStack, pode usar conceito abaixo ou [ler aqui](https://kevin-harijanto.medium.com/swiftui-navigation-without-navigation-view-274c50b3d143)
- Muitos casos não precisa de uma tela root, quando crio algo com navigatorstack permanece sempre uma root, porque o conceito e uma pilha, por isso stack, uma rota empilhada na outra
- Repara que o TabCustomView esta nesse GraphManager se eu precisar navegar para as tabs faço mesmo do exemplo abaixo da splashscreen só que chamo o tabCustomView

```swift

//crio um ObservableObject
class NavigationGraph: ObservableObject {
	
	enum CurrentView: Int {
		case  singUp,confirmCode,finishedRegister,splashScreen,tabCustomView
	}
	
	@Published var switchView = CurrentView.splashScreen
	
}

//crio um gerenciador dar rotas

//
//  GraphManager.swift
//  iosAppYourInterests
//
//  Created by kenjimaeda on 29/08/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

@available(iOS 17.0, *)
struct GraphManagerView: View {
	@StateObject private var graph = NavigationGraph()
	@StateObject private var locationEnviroment = LocationEnvironment()
	@StateObject private var userEnvironment = UserEnvironment()
	@State private var phone = ""
	let transition: AnyTransition = .asymmetric(insertion: .move(edge: .bottom), removal: .move(edge: .top))
	
	var body: some View {
		Group {
			switch (graph.switchView) {
				case .singUp:
					SingUpScreen(phone: $phone)
						.transition(transition)
					
				case .confirmCode:
					ConfirmCodeScreen(phone: $phone)
						.transition(transition)
					
					
					
				case .finishedRegister:
					CompletedRegisterUserScreen(phone: $phone)
						.transition(transition)
					
					
				case .splashScreen:
					SplashScreen()
						.transition(transition)
					
					
				case .tabCustomView:
					TabCustomView()
						.transition(transition)
					
			}
		}
		.animation(.default,value: graph.switchView)
		.environmentObject(graph)
		.environmentObject(locationEnviroment)
		.environmentObject(userEnvironment)
		
	}
	
	
}


@available(iOS 17.0, *)
#Preview {
	GraphManagerView()
}


//navego para onde desejo
@available(iOS 17.0, *)
struct SplashScreen: View {
	@StateObject private var splashState = SplashState()
	@EnvironmentObject private var graphNavigation: NavigationGraph
	@EnvironmentObject private var userEnvironment: UserEnvironment
	@Environment(\.scenePhase) var scenePhase

	
	var body: some View {
			LottieView(animation: .named("splash_animation"))
			.playing()
			.animationDidFinish{ _  in
				Task {
					await splashState.getUser()
				}
			}
			.onChange(of: splashState.loading) {
				if(splashState.loading == .success) {
					userEnvironment.user = splashState.user
					graphNavigation.switchView = .tabCustomView
					return
				}
			
				if(splashState.loading == .failure) { 
					graphNavigation.switchView = .singUp
				}
			}
			.environmentObject(graphNavigation)
			.environmentObject(userEnvironment)
	}
}


```
##
- Para saber se a tela está em foco posso usar o Environment que o Swiftui compartilha

```swift
@Environment(\.scenePhase) var scenePhase

.onChange(of: scenePhase) {_, newPhase in
    //para lidar com ciclo de viida sempre que retornar a tetla esse codigo e chamado	 
		if(newPhase == .active) {
		locationManager.requestLocationPermission()
      }
}
```


##
- Existem códigos comentados sobre a implementação do Mapbox, porem estava aumentando muito complexidade, dai decide usar Apple Maps, porem funciona
- Bom que reforcei um pouco do conceito do uso de representable, este recurso serve para usarmos libs que não dão suporte ao SwiftUI ou ainda implementa o antigo UiViewController
- Se querer entender mais pode [consultar aqui](https://github.com/kenjimaeda54/todo_sophisticated_swiftUi)









 
