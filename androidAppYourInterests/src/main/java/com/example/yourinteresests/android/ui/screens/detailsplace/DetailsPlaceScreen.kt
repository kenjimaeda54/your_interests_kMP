package com.example.yourinteresests.android.ui.screens.detailsplace

import Your_Interesests.androidAppYourInterests.BuildConfig
import android.annotation.SuppressLint
import android.provider.CalendarContract.Colors
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.ArcMode
import androidx.compose.animation.core.ExperimentalAnimationSpecApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.yourinteresests.android.theme.fontsKulimPark
import com.example.yourinteresests.android.ui.screens.detailsplace.view.ItemIconInformation
import com.example.yourinteresests.android.utils.BottomBarScreen
import com.example.yourinterest.data.model.Coordinates
import com.example.yourinterest.data.model.photosrelationswithplace.PhotosPlacesWithRelationNearbyModel


@OptIn(
    ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class,
    ExperimentalAnimationSpecApi::class
)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsPlace(
    place: PhotosPlacesWithRelationNearbyModel,
    animatedVisibilityScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
    showMap: MutableState<Boolean>,
    navController: NavHostController
) {
    val configuration = LocalConfiguration.current
    val accessToken = BuildConfig.ACCESS_TOKEN_MAP
    fun shouldReturnDistance(distance: Int): String {
        if (distance > 1000) {
            return String.format("%.2f", distance.toFloat() / 1000) + " km"
        }
        return "$distance m"

    }

    //referencia
    //https://developer.android.com/develop/ui/compose/animation/shared-elements/customize?hl=pt-br

    //usando keyframes para animar
//    val boundsTransform = BoundsTransform { initialBounds, targetBounds ->
//        keyframes {
//            durationMillis = 500
//            initialBounds at 0 using ArcMode.ArcBelow using FastOutSlowInEasing
//            targetBounds at 500
//        }
//    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text(text = "") }, navigationIcon = {
                Image(
                    modifier = Modifier.clickable {
                        navController.navigate(route = BottomBarScreen.NearbyInterests.route)
                    },
                    imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                    contentDescription = "Back Icon",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )
            }, colors = TopAppBarColors(
                containerColor = Color.Transparent,
                actionIconContentColor = Color.Transparent,
                navigationIconContentColor = MaterialTheme.colorScheme.primary,
                titleContentColor = Color.Transparent,
                scrolledContainerColor = Color.Transparent,
            )

            )
        }
    ) {

        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(configuration.screenHeightDp.dp * 0.3f)
                    .fillMaxWidth(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(place.photoPlacesModel.icon).build(),
                contentDescription = "Image Place",
                contentScale = ContentScale.FillBounds,
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 13.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = place.places.name,
                    style = TextStyle(
                        fontFamily = fontsKulimPark,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.primaryContainer
                    )
                )
                HorizontalDivider(modifier = Modifier.padding(top = 30.dp, bottom = 20.dp))
                ItemIconInformation(subtitle = place.places.address, information = "Endereço") {
                    Image(
                        modifier = Modifier.size(30.dp),
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Pin locaiton",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                    )
                }
                ItemIconInformation(
                    subtitle = shouldReturnDistance(place.places.distance),
                    information = "Distancia"
                ) {
                    Image(
                        modifier = Modifier.size(33.dp),
                        painter = painterResource(id = com.example.yourinteresests.android.R.drawable.distance),
                        contentDescription = "Distance Icon",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                    )
                }
                with(sharedTransitionScope) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(13.dp))
                            .clickable {
                                showMap.value = true
                            }
                            .sharedElement(
                                rememberSharedContentState(key = "image-map"),
                                animatedVisibilityScope = animatedVisibilityScope,
                                //boundsTransform = boundsTransform
                            ),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://api.mapbox.com/styles/v1/mapbox/streets-v10/static/pin-s-l+3C6E71(${place.places.geocode.longitude},${place.places.geocode.latitude})/${place.places.geocode.longitude},${place.places.geocode.latitude},14/600x600?access_token=${accessToken}")
                            .build(),
                        contentDescription = "Image map",
                        contentScale = ContentScale.FillWidth,
                    )
                }


            }
        }
    }
}

