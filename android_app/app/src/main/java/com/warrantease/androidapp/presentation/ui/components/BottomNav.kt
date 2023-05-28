package com.warrantease.androidapp.presentation.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.utils.currentDestinationAsState
import com.ramcosta.composedestinations.utils.startDestination
import com.warrantease.androidapp.R
import com.warrantease.androidapp.presentation.ui.NavGraphs
import com.warrantease.androidapp.presentation.ui.destinations.HomeScreenDestination
import com.warrantease.androidapp.presentation.ui.destinations.SettingsScreenDestination
import com.warrantease.androidapp.presentation.ui.destinations.WarrantiesScreenDestination
import com.warrantease.androidapp.presentation.ui.theme.AppTheme

enum class BottomBarDestination(
    val direction: Direction,
    @DrawableRes val icon: Int,
    @StringRes val label: Int,
) {
    Home(HomeScreenDestination, R.drawable.outline_home_24, R.string.nav_home_label),
    Warranties(
        WarrantiesScreenDestination(),
        R.drawable.baseline_format_list_bulleted_24,
        R.string.nav_warranties_label
    ),
    Settings(
        SettingsScreenDestination,
        R.drawable.outline_settings_24,
        R.string.nav_settings_label
    ),
}

@Composable
fun BottomNav(
    navController: NavController,
) {
    val currentDestination = navController.currentDestinationAsState().value
        ?: NavGraphs.root.startDestination

    BottomAppBar(
        containerColor = AppTheme.white,
        modifier = Modifier.shadow(elevation = 20.dp)
    ) {
        BottomBarDestination.values().forEach { destination ->
            val destinationBaseRoute = destination.direction.route.split("?")[0]

            NavigationBarItem(
                selected = currentDestination.baseRoute == destinationBaseRoute,
                onClick = { navController.navigate(destination.direction) },
                icon = {
                    Icon(
                        painter = painterResource(id = destination.icon),
                        contentDescription = stringResource(id = destination.label)
                    )
                },
                label = { Text(text = stringResource(id = destination.label)) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = AppTheme.primary100,
                    selectedIconColor = AppTheme.neutral600,
                    selectedTextColor = AppTheme.neutral600,
                    unselectedIconColor = AppTheme.neutral600
                )
            )
        }
    }
}
