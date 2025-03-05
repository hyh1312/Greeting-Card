package com.example.greetingcard


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
object MyRoute

@Serializable
object AddRoute
//
//@Serializable
//data class InfoPage(val query: String)
//
//@Serializable
//object HistoryPage

@Composable
fun AppEntry() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { paddingValues ->
        NavHost(
            navController,
            startDestination = HomeRoute,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable<HomeRoute> {
                HomePage()
            }
            composable<MyRoute> {
                MyPage()
            }
            composable<AddRoute> {
                AddPage() { navController.navigateUp() }
            }
//            composable<InfoPage> { backStackEntry ->
//                val infoPage: InfoPage = backStackEntry.toRoute()
//                InfoPage(infoPage.query) { navController.navigateUp() }
//            }
//            composable<HistoryPage> {
//                HistoryPage(toDetail = { query -> navController.navigate(route = InfoPage(query)) }
//                ) { navController.navigateUp() }
//            }
        }
    }
}

data class BottomNavItem<T : Any>(val label: String, val route: T, val icon: ImageVector)

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("首页", HomeRoute, ImageVector.vectorResource(id = R.drawable.home)),
        BottomNavItem("我的", MyRoute, ImageVector.vectorResource(id = R.drawable.me))
    )
    Box(
        Modifier
            .fillMaxWidth()
            .height(69.dp)
            .padding(horizontal = 62.dp)
            .background(Color.Transparent)
    ) {
        Spacer(
            Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(55.dp)
                .padding(start = 4.dp, top = 4.dp)
                .background(color = Color(0xFF8ECEDF), shape = RoundedCornerShape(size = 4.dp))
                .border(
                    width = 1.dp,
                    color = Color(0xFF000000),
                    shape = RoundedCornerShape(size = 4.dp)
                )
        )
        Spacer(
            Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(55.dp)
                .padding(end = 4.dp, bottom = 4.dp)
                .background(color = Color(0xFFFEFADE), shape = RoundedCornerShape(size = 4.dp))
                .border(
                    width = 1.dp,
                    color = Color(0xFF000000),
                    shape = RoundedCornerShape(size = 4.dp)
                )
        )
        BottomAppBar(
            containerColor = Color.Transparent,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .width(265.dp)
                .height(55.dp)
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            items.forEach { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            item.icon,
                            contentDescription = item.label,
                            tint = Color.Unspecified
                        )
                    },
                    label = { Text(item.label) },
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(HomeRoute) { inclusive = false }
                        }
                    }
                )
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .clip(CircleShape)
                .size(50.dp)
                .align(Alignment.TopCenter),
            onClick = { navController.navigate(AddRoute) },
            shape = CircleShape,
            containerColor = Color.Transparent,
        ) {
            Icon(
                ImageVector.vectorResource(id = R.drawable.add),
                contentDescription = "Add",
                tint = Color.Unspecified
            )
        }
    }
}