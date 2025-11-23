package com.fatec.salafacil.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fatec.salafacil.ui.routes.AppRoutes
import com.fatec.salafacil.ui.screens.home.data.NavigationItem
import com.fatec.salafacil.ui.screens.home.tabs.BookingTab
import com.fatec.salafacil.ui.screens.home.tabs.MeetingsTab
import com.fatec.salafacil.ui.screens.home.tabs.ScheduleTab
import com.fatec.salafacil.ui.theme.Brand400
import com.fatec.salafacil.ui.theme.Brand500
import com.fatec.salafacil.ui.theme.Grey100
import com.fatec.salafacil.ui.theme.Grey200
import com.fatec.salafacil.ui.theme.Grey400
import com.fatec.salafacil.ui.theme.Grey500
import com.fatec.salafacil.ui.translations.PT

val navigationItems = listOf(
    NavigationItem(
        title = "Reuniões",
        icon = Icons.Default.Bookmark,
        route = AppRoutes.MEETINGS
    ),
    NavigationItem(
        title = "Reservar",
        icon = Icons.Outlined.AddCircle,
        route = AppRoutes.BOOKING
    ),
    NavigationItem(
        title = "Agenda",
        icon = Icons.Outlined.CalendarMonth,
        route = AppRoutes.SCHEDULE
    )
)

@Composable
fun HomeScreen(
    onAccountButtonClick: () -> Unit
) {
    val navController = rememberNavController()
    val selectedNavigationIndex = rememberSaveable { mutableIntStateOf(0) }

    // Sincroniza o índice selecionado com a rota atual
    LaunchedEffect(navController.currentBackStackEntryAsState().value?.destination?.route) {
        val currentRoute = navController.currentDestination?.route
        val index = navigationItems.indexOfFirst { it.route == currentRoute }
        if (index != -1) {
            selectedNavigationIndex.intValue = index
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(6f),
                    text = PT.home_title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Grey500
                )

                IconButton(
                    modifier = Modifier
                        .weight(1f),
                    onClick = {
                        onAccountButtonClick()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Informações da conta",
                    )
                }
            }
        },
        bottomBar = {
            NavigationBar(
                windowInsets = NavigationBarDefaults.windowInsets,
                containerColor = Grey200
            ) {
                navigationItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedNavigationIndex.intValue == index,
                        onClick = {
                            selectedNavigationIndex.intValue = index
                            navController.navigate(item.route) {
                                launchSingleTop = true
                                restoreState = true

                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title
                            )
                        },
                        label = {
                            Text(text = item.title)
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Grey100,
                            selectedTextColor = Grey500,
                            indicatorColor = Brand400,
                            unselectedIconColor = Brand500,
                            unselectedTextColor = Grey400,
                        )
                    )
                }
            }
        }
    ) { contentPadding ->
        NavHost(
            navController = navController,
            startDestination = AppRoutes.MEETINGS,
            modifier = Modifier.padding(contentPadding)
        ) {
            composable(AppRoutes.MEETINGS) {
                MeetingsTab(onViewAllClick = {
                    // TODO
                }
                )
            }
            composable(AppRoutes.BOOKING) {
                BookingTab(
                    onViewAllClick = {
                        // TODO
                    }
                )
            }
            composable(AppRoutes.SCHEDULE) {
                ScheduleTab()
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(onAccountButtonClick = {})
}