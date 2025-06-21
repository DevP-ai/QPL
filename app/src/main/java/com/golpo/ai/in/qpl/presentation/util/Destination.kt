package com.golpo.ai.`in`.qpl.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.golpo.ai.`in`.qpl.presentation.screens.HomeScreen
import kotlinx.serialization.Serializable

sealed class Destination{
    @Serializable
    data object  SplashScreen: Destination()

    @Serializable
    data object LoginScreen: Destination()

    @Serializable
    data object SignUpScreen: Destination()

    @Serializable
    data object NavigationGraph: Destination()

    @Serializable
    data object HomeScreen: Destination()

    @Serializable
    data object LeaderBoardScreen: Destination()

    @Serializable
    data object ProfileScreen: Destination()
}

enum class BottomNavigation(val label: String, val icon: ImageVector, val route: Destination){
    HOME(label = "Home", icon = Icons.Filled.Home, route = Destination.HomeScreen),
    LEADERBOARD(label = "LeaderBoard", icon = Icons.Filled.KeyboardArrowUp, route = Destination.LeaderBoardScreen),
    PROFILE(label = "Profile", icon = Icons.Filled.AccountCircle, route = Destination.ProfileScreen)
}
