package com.f776.japanesedictionary.core.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.navigation.NavBackStackEntry

enum class TransitionAnimation(
    val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition,
    val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition
) {
    FADE_SLIDE_RTL(
        enterTransition = {
            fadeIn(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideIntoContainer(
                animationSpec = tween(300, easing = EaseIn),
                towards = SlideDirection.Start
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideOutOfContainer(
                animationSpec = tween(300, easing = EaseOut),
                towards = SlideDirection.End
            )
        }
    ),
    FADE_SLIDE_LTR(
        enterTransition = {
            fadeIn(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideIntoContainer(
                animationSpec = tween(300, easing = EaseIn),
                towards = SlideDirection.End
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideOutOfContainer(
                animationSpec = tween(300, easing = EaseOut),
                towards = SlideDirection.Start
            )
        }
    ),
    SCALE_FADE(
        enterTransition = {
            scaleIn(
                initialScale = 0.8f,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = EaseIn
                )
            ) + fadeIn(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearEasing
                )
            )
        },
        exitTransition = {
            scaleOut(
                targetScale = 1.2f,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = EaseOut
                )
            ) + fadeOut(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearEasing
                )
            )
        }
    )
}