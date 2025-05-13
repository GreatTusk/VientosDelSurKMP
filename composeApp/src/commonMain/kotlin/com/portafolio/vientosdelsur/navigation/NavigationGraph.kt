package com.portafolio.vientosdelsur.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.portafolio.vientosdelsur.foryou.navigation.ForYou
import com.portafolio.vientosdelsur.foryou.navigation.forYouGraph
import com.portafolio.vientosdelsur.room.navigation.ForYouHousekeeper
import com.portafolio.vientosdelsur.room.navigation.housekeeperForYou
import com.portafolio.vientosdelsur.room.navigation.supervisorForYou

@Composable
fun NavigationGraph(modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()) {
    // Hard coded for now, may replace later
    NavHost(modifier = modifier, navController = navController, startDestination = ForYouHousekeeper) {
        housekeeperForYou()
        supervisorForYou()
        forYouGraph()
    }
}