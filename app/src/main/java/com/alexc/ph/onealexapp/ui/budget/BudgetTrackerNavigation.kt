package com.alexc.ph.onealexapp.ui.budget

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable data object BudgetTrackerRoute

fun NavController.navigateToBudgetTracker(navOptions: NavOptions) =
    navigate(route = BudgetTrackerRoute, navOptions)

fun NavGraphBuilder.budgetTrackerScreen(modifier: Modifier = Modifier) {
    composable<BudgetTrackerRoute> {
        BudgetTrackerScreen(modifier = modifier)
    }

}