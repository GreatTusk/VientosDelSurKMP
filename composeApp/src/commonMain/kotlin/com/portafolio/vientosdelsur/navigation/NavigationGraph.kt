package com.portafolio.vientosdelsur.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.portafolio.vientosdelsur.feature.hotel.navigation.hotelGraph
import com.portafolio.vientosdelsur.foryou.navigation.ForYou
import com.portafolio.vientosdelsur.foryou.navigation.forYouGraph

@Composable
fun NavigationGraph(modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()) {
    NavHost(modifier = modifier, navController = navController, startDestination = ForYou) {
        forYouGraph()
        hotelGraph()
    }
}