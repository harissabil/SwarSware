package com.harissabil.swarsware.ui.screen.sounds.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextOverflow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoundSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    DockedSearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        ),
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {
            keyboardController?.hide()
        },
        active = false,
        onActiveChange = { },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        placeholder = {
            Text(
                text = "Search sounds",
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.alpha(0.75f)
            )
        },
        content = {},
    )
}