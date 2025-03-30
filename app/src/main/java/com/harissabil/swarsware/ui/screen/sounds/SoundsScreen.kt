package com.harissabil.swarsware.ui.screen.sounds

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.harissabil.swarsware.common.component.CommonTopBar
import com.harissabil.swarsware.common.component.LargeDropdownMenu
import com.harissabil.swarsware.domain.model.Priority
import com.harissabil.swarsware.ui.screen.sounds.component.SoundItem
import com.harissabil.swarsware.ui.screen.sounds.component.SoundSearchBar
import com.harissabil.swarsware.ui.theme.spacing
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoundsScreen(
    modifier: Modifier = Modifier,
    viewModel: SoundsViewModel = koinViewModel(),
) {
    val sounds by viewModel.filteredSounds.collectAsStateWithLifecycle(initialValue = emptyList())
    val query by viewModel.query.collectAsStateWithLifecycle()
    val priorityFilter by viewModel.priorityFilter.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        canScroll = { false }
    )

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CommonTopBar(
                title = "Sounds",
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(MaterialTheme.spacing.medium),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
        ) {
            item(key = -1) {
                SoundSearchBar(
                    modifier = Modifier.fillMaxWidth(),
                    query = query,
                    onQueryChange = viewModel::setQuery
                )
            }
            item(key = 0) {
                val priorities = listOf("High", "Medium", "Low", "None")
                LargeDropdownMenu(
                    modifier = Modifier.fillMaxWidth(),
                    items = priorities,
                    selectedIndex = priorities.indexOf(priorityFilter?.displayName ?: "None"),
                    onItemSelected = { index, priority ->
                        viewModel.setPriorityFilter(
                            if (priority == "None") null else Priority.entries.find { it.displayName == priority }
                        )
                    }
                )
            }
            items(items = sounds, key = { it.id }) { sound ->
                SoundItem(
                    modifier = Modifier.animateItem(),
                    sound = sound,
                    onClick = {}
                )
            }
        }
    }
}