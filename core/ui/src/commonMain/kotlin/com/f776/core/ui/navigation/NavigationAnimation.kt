package com.f776.core.ui.navigation

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
    val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition,
    val popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = enterTransition,
    val popExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = exitTransition
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
    SLIDE_FADE(
        enterTransition = {
            fadeIn(animationSpec = tween(300)) +
                    slideIntoContainer(
                        animationSpec = tween(300),
                        towards = SlideDirection.Start
                    )
        },
        exitTransition = {
            fadeOut(animationSpec = tween(300)) +
                    slideOutOfContainer(
                        animationSpec = tween(300),
                        towards = SlideDirection.Start
                    )
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(300)) +
                    slideIntoContainer(
                        animationSpec = tween(300),
                        towards = SlideDirection.End
                    )
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(300)) +
                    slideOutOfContainer(
                        animationSpec = tween(300),
                        towards = SlideDirection.End
                    )
        }
    ),

    ZOOM(
        enterTransition = {
            scaleIn(
                initialScale = 0.95f,
                animationSpec = tween(durationMillis = 300)
            ) + fadeIn(animationSpec = tween(300))
        },
        exitTransition = {
            scaleOut(
                targetScale = 1.05f,
                animationSpec = tween(durationMillis = 300)
            ) + fadeOut(animationSpec = tween(300))
        }
    ),

    SHARED_AXIS_X(
        enterTransition = {
            fadeIn(animationSpec = tween(210, delayMillis = 90)) +
                    slideIntoContainer(
                        animationSpec = tween(300),
                        towards = SlideDirection.Start
                    )
        },
        exitTransition = {
            fadeOut(animationSpec = tween(90)) +
                    slideOutOfContainer(
                        animationSpec = tween(300),
                        towards = SlideDirection.Start
                    )
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(210, delayMillis = 90)) +
                    slideIntoContainer(
                        animationSpec = tween(300),
                        towards = SlideDirection.End
                    )
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(90)) +
                    slideOutOfContainer(
                        animationSpec = tween(300),
                        towards = SlideDirection.End
                    )
        }
    ),

    SHARED_AXIS_Y(
        enterTransition = {
            fadeIn(animationSpec = tween(210, delayMillis = 90)) +
                    slideIntoContainer(
                        animationSpec = tween(300),
                        towards = SlideDirection.Up
                    )
        },
        exitTransition = {
            fadeOut(animationSpec = tween(90)) +
                    slideOutOfContainer(
                        animationSpec = tween(300),
                        towards = SlideDirection.Up
                    )
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(210, delayMillis = 90)) +
                    slideIntoContainer(
                        animationSpec = tween(300),
                        towards = SlideDirection.Down
                    )
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(90)) +
                    slideOutOfContainer(
                        animationSpec = tween(300),
                        towards = SlideDirection.Down
                    )
        }
    ),

    NONE(
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    )
}