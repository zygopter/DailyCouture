package com.example.mywardrobe.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.mywardrobe.R

@Composable
fun WardrobeBottomNavigation(index: Int, navController: NavHostController) {
    BottomAppBar(
    ) {
        BottomNavigation {
            BottomNavigationItem(
                icon = { Icon(painter = painterResource(id = R.drawable.baseline_calendar_month_24), contentDescription = "Daily") },
                label = { Text("Daily") },
                selected = index == 0,
                onClick = { navController.navigate("dailyPlannerScreen") }
            )
            BottomNavigationItem(
                icon = { Icon(painter = painterResource(id = R.drawable.baseline_checkroom_24), contentDescription = "Catalog") },
                label = { Text("Catalog") },
                selected = index == 1,
                onClick = { navController.navigate("wardrobeScreen") }
            )
            BottomNavigationItem(
                icon = { Icon(painter = painterResource(id = R.drawable.baseline_diversity_1_24), contentDescription = "Community") },
                label = { Text("Community") },
                selected = index == 2,
                onClick = { /*TODO*/ }
            )
        }
    }
}