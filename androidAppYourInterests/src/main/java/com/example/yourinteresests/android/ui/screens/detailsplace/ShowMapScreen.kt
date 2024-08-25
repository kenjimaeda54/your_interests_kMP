package com.example.yourinteresests.android.ui.screens.detailsplace

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.yourinteresests.android.ui.screens.view.CustomMapBox
import com.example.yourinterest.data.model.Coordinates
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ShowMapScreen(
    animatedVisibilityScope: AnimatedContentScope,
    location: Coordinates,
    sharedTransitionScope: SharedTransitionScope,
    showMap: MutableState<Boolean>,
) {


    Scaffold(

        modifier = Modifier
            .fillMaxSize(),

        topBar = {
            TopAppBar(title = { Text(text = "") }, navigationIcon = {
                Image(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .clickable {
                            showMap.value = false
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

        //precisoo animar este compontene sendo que nao petence ao shared
        //por isso usei .renderInSharedTransitionScopeOverlay(
        //                            zIndexInOverlay = 1f
        //
        //                        ),
        //https://developer.android.com/develop/ui/compose/animation/composables-modifiers?hl=pt-br
//            AnimatedVisibility(visible = showMap.value,
//
//                exit = fadeOut() + slideOutVertically {
//                    it
//                },
//                enter = fadeIn() + slideInVertically {
//                    it
//                }) {
//
//
//
//            }
        with(sharedTransitionScope) {
            Box(
                modifier = Modifier.sharedElement(
                    rememberSharedContentState(key = "image-map"),
                    animatedVisibilityScope = animatedVisibilityScope
                ),
            ) {
                CustomMapBox(
                    modifier = Modifier
                        .fillMaxSize(),
                    point = location,
                    address = ""
                )
            }

        }


    }


}