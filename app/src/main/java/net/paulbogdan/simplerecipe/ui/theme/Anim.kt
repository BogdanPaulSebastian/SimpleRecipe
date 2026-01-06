package net.paulbogdan.simplerecipe.ui.theme

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

private const val TransitionDurationInMs = 700

@OptIn(ExperimentalAnimationApi::class)
fun enterTransition(direction: AnimatedContentScope.SlideDirection): AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition? =
    {
        slideIntoContainer(
            animationSpec = tween(TransitionDurationInMs),
            towards = direction
        )
    }

@OptIn(ExperimentalAnimationApi::class)
fun exitTransition(direction: AnimatedContentScope.SlideDirection): AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition? =
    {
        slideOutOfContainer(animationSpec = tween(TransitionDurationInMs), towards = direction)
    }