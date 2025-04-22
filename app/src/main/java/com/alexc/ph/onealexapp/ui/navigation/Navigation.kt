
package com.alexc.ph.onealexapp.ui.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItemColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun OneAlexNavigationSuiteScaffold(
    modifier: Modifier = Modifier,
    navigationSuiteItems: OneAlexNavigationSuiteScope.() -> Unit,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
    content: @Composable () -> Unit
) {
    val layoutType = NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(windowAdaptiveInfo)
    val navigationSuiteItemColors = NavigationSuiteItemColors(
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = OneAlexNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = OneAlexNavigationDefaults.navigationContentColor(),
            selectedTextColor = OneAlexNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = OneAlexNavigationDefaults.navigationContentColor(),
            indicatorColor = OneAlexNavigationDefaults.navigationIndicatorColor(),
        ),
        navigationRailItemColors = NavigationRailItemDefaults.colors(
            selectedIconColor = OneAlexNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = OneAlexNavigationDefaults.navigationContentColor(),
            selectedTextColor = OneAlexNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = OneAlexNavigationDefaults.navigationContentColor(),
            indicatorColor = OneAlexNavigationDefaults.navigationIndicatorColor(),
        ),
        navigationDrawerItemColors = NavigationDrawerItemDefaults.colors(
            selectedIconColor = OneAlexNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = OneAlexNavigationDefaults.navigationContentColor(),
            selectedTextColor = OneAlexNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = OneAlexNavigationDefaults.navigationContentColor(),
        ),
    )

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            OneAlexNavigationSuiteScope(
                navigationSuiteScope = this,
                navigationSuiteItemColors = navigationSuiteItemColors,
            ).run(navigationSuiteItems)
        },
        layoutType = layoutType,
        containerColor = Color.Transparent,
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationBarContentColor = OneAlexNavigationDefaults.navigationContentColor(),
            navigationRailContainerColor = Color.Transparent,
        ),
        modifier = modifier,
    ) {
        content()
    }
}

class OneAlexNavigationSuiteScope internal constructor(
    private val navigationSuiteScope: NavigationSuiteScope,
    private val navigationSuiteItemColors: NavigationSuiteItemColors
) {
    fun item(
        selected: Boolean,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        icon: @Composable () -> Unit,
        selectedIcon: @Composable () -> Unit = icon,
        label: @Composable (() -> Unit)? = null,
    ) = navigationSuiteScope.item(
        selected = selected,
        onClick = onClick,
        icon = {
            if (selected) {
                selectedIcon()
            } else {
                icon()
            }
        },
        label = label,
        colors = navigationSuiteItemColors,
        modifier = modifier,
    )
}

object OneAlexNavigationDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.primary

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}
