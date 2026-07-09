package com.example.moodjournal.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.moodjournal.ui.screens.*
import com.example.moodjournal.ui.screens.auth.*
import com.example.moodjournal.util.CrisisSupport
import com.example.moodjournal.viewmodel.JournalViewModel
import com.example.moodjournal.ui.theme.*

private const val ROUTE_LOGIN = "login"
private const val ROUTE_SIGNUP = "signup"
private const val ROUTE_LIST = "list"
private const val ROUTE_TRENDS = "trends"
private const val ROUTE_INSIGHTS = "insights"
private const val ROUTE_NEW = "new_entry"
private const val ROUTE_SETTINGS = "settings"
private const val ROUTE_DETAIL = "entry/{entryId}"

@Composable
fun AppNavigation(viewModel: JournalViewModel) {
    val navController = rememberNavController()
    val entries by viewModel.entries.collectAsState()
    val showCrisisSupport by viewModel.showCrisisSupport.collectAsState()
    val trendInsight by viewModel.trendInsight.collectAsState()
    val isLoadingInsight by viewModel.isLoadingInsight.collectAsState()
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    val isGuestMode by viewModel.isGuestMode.collectAsState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val startDestination = if (isLoggedIn || isGuestMode) ROUTE_LIST else ROUTE_LOGIN

    if (showCrisisSupport) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissCrisisSupport() },
            title = { Text("You're not alone") },
            text = { Text(CrisisSupport.SUPPORT_MESSAGE) },
            confirmButton = {
                TextButton(onClick = { viewModel.dismissCrisisSupport() }) {
                    Text("OK")
                }
            }
        )
    }

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            if (currentDestination?.route in listOf(ROUTE_LIST, ROUTE_TRENDS, ROUTE_INSIGHTS)) {
                NavigationBar(
                    containerColor = if (isDarkMode) DarkSheetBg else LightSheetBg,
                    tonalElevation = 8.dp
                ) {
                    val items = listOf(
                        Triple(ROUTE_LIST, "Home", Icons.Default.Home),
                        Triple(ROUTE_TRENDS, "Trends", Icons.Default.ShowChart),
                        Triple(ROUTE_INSIGHTS, "Insights", Icons.Default.Lightbulb)
                    )
                    items.forEach { (route, label, icon) ->
                        val selected = currentDestination?.hierarchy?.any { it.route == route } == true
                        NavigationBarItem(
                            icon = { Icon(icon, contentDescription = label) },
                            label = { Text(label) },
                            selected = selected,
                            onClick = {
                                navController.navigate(route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = if (isDarkMode) DarkCoral else LightCoralDeep,
                                selectedTextColor = if (isDarkMode) DarkCoral else LightCoralDeep,
                                unselectedIconColor = if (isDarkMode) DarkText3 else LightText3,
                                unselectedTextColor = if (isDarkMode) DarkText3 else LightText3,
                                indicatorColor = (if (isDarkMode) DarkCoral else LightCoralDeep).copy(alpha = 0.1f)
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(ROUTE_LOGIN) {
                var error by remember { mutableStateOf<String?>(null) }
                var loading by remember { mutableStateOf(false) }
                LoginScreen(
                    onLoginClick = { u, p ->
                        loading = true
                        viewModel.login(u, p) { result ->
                            loading = false
                            if (result.isSuccess) {
                                navController.navigate(ROUTE_LIST) {
                                    popUpTo(ROUTE_LOGIN) { inclusive = true }
                                }
                            } else {
                                error = result.exceptionOrNull()?.message ?: "Login failed"
                            }
                        }
                    },
                    onContinueAsGuest = {
                        viewModel.setGuestMode(true)
                        navController.navigate(ROUTE_LIST) {
                            popUpTo(ROUTE_LOGIN) { inclusive = true }
                        }
                    },
                    onSignUpClick = { navController.navigate(ROUTE_SIGNUP) },
                    isDarkMode = isDarkMode,
                    errorMessage = error,
                    isLoading = loading
                )
            }

            composable(ROUTE_SIGNUP) {
                var error by remember { mutableStateOf<String?>(null) }
                SignupScreen(
                    onSignupSuccess = { u, p ->
                        viewModel.signup(u, p) { result ->
                            if (result.isSuccess) {
                                navController.navigate(ROUTE_LIST) {
                                    popUpTo(ROUTE_LOGIN) { inclusive = true }
                                }
                            } else {
                                error = result.exceptionOrNull()?.message ?: "Signup failed"
                            }
                        }
                    },
                    onBack = { navController.popBackStack() },
                    isDarkMode = isDarkMode
                )
            }

            composable(ROUTE_LIST) {
                JournalListScreen(
                    entries = entries,
                    onAddClick = { navController.navigate(ROUTE_NEW) },
                    onEntryClick = { entry -> navController.navigate("entry/${entry.id}") },
                    onTrendsClick = { navController.navigate(ROUTE_TRENDS) },
                    onSettingsClick = { navController.navigate(ROUTE_SETTINGS) },
                    viewModel = viewModel
                )
            }

            composable(ROUTE_TRENDS) {
                TrendsScreen(
                    entries = viewModel.recentEntriesForTrend(),
                    insight = trendInsight,
                    isLoadingInsight = isLoadingInsight,
                    onBack = { navController.popBackStack() },
                    onRequestInsight = { viewModel.requestTrendInsight() }
                )
            }

            composable(ROUTE_INSIGHTS) {
                InsightsScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(ROUTE_NEW) {
                NewEntryScreen(
                    onBack = { navController.popBackStack() },
                    onSave = { text -> viewModel.addEntry(text) }
                )
            }

            composable(
                ROUTE_DETAIL,
                arguments = listOf(navArgument("entryId") { type = NavType.LongType })
            ) { backStackEntry ->
                val entryId = backStackEntry.arguments?.getLong("entryId") ?: -1L
                val entry = entries.find { it.id == entryId }
                entry?.let {
                    EntryDetailScreen(
                        entry = it,
                        onBack = { navController.popBackStack() },
                        onDelete = { viewModel.deleteEntry(it) },
                        onRetry = { viewModel.retryAnalysis(it) }
                    )
                }
            }

            composable(ROUTE_SETTINGS) {
                SettingsScreen(
                    currentKey = viewModel.apiKey,
                    onBack = { navController.popBackStack() },
                    onSave = { key -> viewModel.apiKey = key },
                    onLogout = {
                        viewModel.logout()
                        navController.navigate(ROUTE_LOGIN) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
