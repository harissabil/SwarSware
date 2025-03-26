package com.harissabil.swarsware.common.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun BottomNavBar(
    items: List<BottomNavItem>,
    selected: Int,
    onItemClick: (Int) -> Unit,
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = index == selected,
                onClick = { onItemClick(index) },
                icon = {
                    Icon(
                        painter = painterResource(if (index == selected) item.selectedIcon else item.unselectedIcon),
                        contentDescription = item.text,
                    )
                },
                alwaysShowLabel = true,
                label = { item.text?.let { Text(it) } },
            )
        }
    }
}

data class BottomNavItem(
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val text: String?,
)