package net.paulbogdan.simplerecipe.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import net.paulbogdan.simplerecipe.extensions.semiBorder
import net.paulbogdan.simplerecipe.ui.theme.SimpleTheme
import net.paulbogdan.simplerecipe.R


@Composable
fun BottomBar(navController: NavController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Row(
        modifier = Modifier
            .background(
                SimpleTheme.colors.pageBackground,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
            .fillMaxWidth()
            .semiBorder(
                strokeWidth = 1.dp,
                color = SimpleTheme.colors.deepGray,
                20.dp,
                isTopSide = true
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        bottomBarItems.forEach { item ->
            AddItem(
                item = item,
                currentDestination = currentDestination,
                navController = navController
            )
        }

    }
}

@Composable
fun AddItem(
    item: NavigationItem,
    currentDestination: NavDestination?,
    navController: NavController
) {
    val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true
    val contentColor = if (selected) SimpleTheme.colors.green else SimpleTheme.colors.deepGray
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .height(60.dp)
            .clickable(
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                interactionSource = interactionSource,
                indication = null
            )
    ) {
        Row(
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(id = item.icon),
                contentDescription = stringResource(id = R.string.bottom_navigation_item),
                tint = contentColor
            )
        }
    }
}

@Composable
@Preview
fun BottomNavPreview() {
    val navController = rememberNavController()
    BottomBar(navController = navController)
}