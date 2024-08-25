package com.example.yourinteresests.android.ui.screens.detailsplace

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.IntSize
import androidx.navigation.NavHostController
import com.example.yourinterest.data.model.Coordinates
import com.example.yourinterest.data.model.photosrelationswithplace.PhotosPlacesWithRelationNearbyModel


@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalAnimationApi::class)
@Composable
fun SharedDetailsPlace(
    place: PhotosPlacesWithRelationNearbyModel,
    navController: NavHostController
) {
    val showMap = remember {
        mutableStateOf(false)
    }



    SharedTransitionLayout {
        AnimatedContent(
            targetState = showMap, label = "animation_map",
            transitionSpec = {
                expandVertically(animationSpec = tween(300, 300)) togetherWith
                        fadeOut(animationSpec = tween(300)) using
                        SizeTransform { initialSize, targetSize ->
                            if (showMap.value) {
                                keyframes {
                                    // Expand horizontally first.
                                    IntSize(targetSize.width, initialSize.height) at 300
                                    durationMillis = 300
                                }
                            } else {
                                keyframes {
                                    // Shrink vertically first.
                                    IntSize(initialSize.width, targetSize.height) at 300
                                    durationMillis = 300
                                }
                            }
                        }
            }
        ) {
            if (it.value) {

                ShowMapScreen(
                    location = Coordinates(
                        longitude = place.places.geocode.longitude,
                        latitude = place.places.geocode.latitude
                    ),
                    animatedVisibilityScope = this@AnimatedContent,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    showMap = showMap,
                )

            } else {

                DetailsPlace(
                    animatedVisibilityScope = this@AnimatedContent,
                    sharedTransitionScope = this@SharedTransitionLayout, place = place,
                    showMap = showMap,
                    navController = navController
                )

            }
        }
    }


}