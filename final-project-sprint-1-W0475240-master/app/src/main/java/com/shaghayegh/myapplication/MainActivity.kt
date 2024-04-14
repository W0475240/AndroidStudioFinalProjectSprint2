package com.shaghayegh.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shaghayegh.myapplication.ui.viewmodel.MainViewModel
import com.shaghayegh.myapplication.ui.screens.AlertScreen
import com.shaghayegh.myapplication.ui.screens.MapScreen
import com.shaghayegh.myapplication.ui.screens.RouteScreen
import com.shaghayegh.myapplication.ui.screens.Screen
import com.shaghayegh.myapplication.ui.theme.TransitAppTheme

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.loadBusPositions()
        setContent {
            TransitAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TransitApp()
                }
            }
        }
    }
}
@Composable
fun TransitApp() {
    val items = listOf(Screen.MapScreen, Screen.RouteScreen, Screen.AlertScreen)
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation {

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painterResource(id = screen.icon),
                                contentDescription = null)
                               },
                        label = { Text(screen.label) },

                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo = navController.graph.startDestinationId
                                launchSingleTop = true
                            }
                        },
                        selected = currentRoute == screen.route,
                        selectedContentColor = Color.Yellow,
                        unselectedContentColor = Color.White// Color for unselected item
                       // unselectedContentColor = Color.White
                    )


                }


            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = Screen.MapScreen.route, Modifier.padding(innerPadding)) {
            composable(Screen.MapScreen.route) { MapScreen() }
            composable(Screen.RouteScreen.route) { RouteScreen() }
            composable(Screen.AlertScreen.route) { AlertScreen() }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TransitApp()
}