package com.golpo.ai.`in`.qpl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.golpo.ai.`in`.qpl.foundation.theme.QPLTheme
import com.golpo.ai.`in`.qpl.presentation.screens.HomeScreen
import com.golpo.ai.`in`.qpl.presentation.screens.LeaderBoardScreen
import com.golpo.ai.`in`.qpl.presentation.screens.LoginScreen
import com.golpo.ai.`in`.qpl.presentation.screens.ProfileScreen
import com.golpo.ai.`in`.qpl.presentation.screens.SplashScreen
import com.golpo.ai.`in`.qpl.presentation.util.BottomNavigation
import com.golpo.ai.`in`.qpl.presentation.util.Destination

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QPLTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route ?: ""

                val currentRouteTrimmed by remember(currentRoute) {
                    derivedStateOf { currentRoute.substringBefore("?") }
                }

                val showBottomBar = currentRouteTrimmed != Destination.SplashScreen::class.qualifiedName
                        && currentRouteTrimmed != Destination.LoginScreen::class.qualifiedName
                        && currentRouteTrimmed != Destination.SignUpScreen::class.qualifiedName

                Scaffold (
                    bottomBar = {
                        if(showBottomBar){
                            BottomAppBar {
                                BottomNavigation.entries.forEach { bottomNavigation ->
                                    val selected = currentRouteTrimmed == bottomNavigation.route::class.qualifiedName
                                    NavigationBarItem(
                                        selected = selected,
                                        onClick = {
                                            navController.navigate(bottomNavigation.route){
                                                launchSingleTop = true
                                                restoreState = true
                                                popUpTo(navController.graph.startDestinationId){
                                                    saveState = true
                                                }
                                            }
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = bottomNavigation.icon,
                                                contentDescription = "Nav Icon"
                                            )
                                        },
                                        label = {
                                            Text(
                                                text = bottomNavigation.label
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                ){innerPadding->
                    NavHost(
                        modifier = Modifier
                            .padding(innerPadding),
                        navController = navController,
                        startDestination = Destination.NavigationGraph
                    ){
                        //SplashScreen
                        composable <Destination.SplashScreen>{
                            SplashScreen {
                                val isLoggedIn = true
                                if(isLoggedIn){
                                    navController.navigate(Destination.NavigationGraph){
                                        popUpTo(Destination.SplashScreen){
                                            inclusive = true
                                        }
                                    }
                                }else{
                                    navController.navigate(Destination.LoginScreen){
                                        popUpTo(Destination.SplashScreen){
                                            inclusive = true
                                        }
                                    }
                                }
                            }
                        }

                        //Login Screen
                        composable <Destination.LoginScreen>{
                            LoginScreen(
                                onLoginSuccess = {
                                    navController.navigate(Destination.NavigationGraph){
                                        popUpTo(Destination.LoginScreen){
                                            inclusive = true
                                        }
                                    }
                                },
                                onCreateAccountClick = {
                                    navController.navigate(Destination.SignUpScreen)
                                }
                            )
                        }

                        // Navigation Graph with bottom nav
                        navigation<Destination.NavigationGraph>(
                            startDestination = Destination.HomeScreen
                        ){
                            // Home Screen
                            composable<Destination.HomeScreen> {
                                HomeScreen()
                            }

                            // LeaderBoard
                            composable <Destination.LeaderBoardScreen>{
                                LeaderBoardScreen()
                            }

                            // Profile
                            composable <Destination.ProfileScreen> {
                                ProfileScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}
