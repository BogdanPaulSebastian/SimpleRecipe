package net.paulbogdan.simplerecipe.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.paulbogdan.simplerecipe.R
import net.paulbogdan.simplerecipe.ui.theme.SimpleTheme

@Composable
fun SearchBarWithFilter(
    text: String,
    hasFilters: Boolean,
    onSearch: KeyboardActionScope.() -> Unit,
    onFilterClick: () -> Unit,
    onTextChanged: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = SimpleTheme.colors.lightGray, shape = RoundedCornerShape(40.dp)),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SimpleSearchBar(
                text = text,
                topText = stringResource(id = R.string.search_recipes),
                bottomText = stringResource(id = R.string.search_ingredients),
                hasFilters = hasFilters,
                onSearch = onSearch,
                onFilterClick = onFilterClick,
                onTextChanged = onTextChanged,
                canFilter = true
            )
        }
    }
}

@Composable
fun SimpleSearchBar(
    text: String,
    topText: String,
    bottomText: String,
    hasFilters: Boolean = false,
    onSearch: KeyboardActionScope.() -> Unit,
    onFilterClick: () -> Unit = {},
    onClearClick: () -> Unit = {},
    onTextChanged: (String) -> Unit,
    canFilter: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = SimpleTheme.colors.lightGray, shape = RoundedCornerShape(40.dp))
            .border(width = 1.dp, color = SimpleTheme.colors.lightGray, shape = RoundedCornerShape(40.dp))
            .padding(4.dp),

        ) {

        Box(modifier = Modifier.align(Alignment.CenterStart)) {
            Row {
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    painter = painterResource(id = R.drawable.search_icon),
                    contentDescription = stringResource(id = R.string.searchbar_icon),
                    Modifier.requiredSize(20.dp),
                    tint = SimpleTheme.colors.textColor
                )
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(start = 30.dp, end = 50.dp)
        ) {
            SimpleBasicTextField(
                value = text,
                topText = topText,
                bottomText = bottomText,
                onSearch = onSearch,
                onValueChange = onTextChanged
            )
        }
        if (canFilter) {
            Box(modifier = Modifier.align(Alignment.CenterEnd)) {
                IconButton(
                    onClick = { onFilterClick() }
                ) {
                    Icon(
                        painter = if (!hasFilters) painterResource(id = R.drawable.filter_inactive_icon) else painterResource(
                            id = R.drawable.filter_active_icon
                        ),
                        contentDescription = stringResource(id = R.string.filter_search_parameters),
                        Modifier.requiredSize(40.dp),
                        tint = SimpleTheme.colors.textColor

                    )
                }
            }
        } else {
            if (text.isNotBlank()) {

                Box(modifier = Modifier.align(Alignment.CenterEnd)) {
                    IconButton(
                        onClick = { onClearClick() }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_clear),
                            contentDescription = stringResource(id = R.string.filter_search_parameters),
                            Modifier.requiredSize(30.dp),
                            tint = SimpleTheme.colors.textColor
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .requiredSize(48.dp)
                ) {
                }
            }
        }
    }
}

@Preview(name = "With Text")
@Composable
fun TopBarPreview() {
    SearchBarWithFilter(
        text = "Potato cheese lettuce",
        hasFilters = false,
        onSearch = {},
        onFilterClick = {},
        onTextChanged = {}
    )
}

@Preview(name = "Empty")
@Composable
fun TopBarPreviewEmpty() {
    SimpleSearchBar(
        text = "",
        topText = stringResource(id = R.string.search_recipes),
        bottomText = stringResource(id = R.string.search_ingredients),
        hasFilters = false,
        onSearch = {},
        onFilterClick = {},
        onTextChanged = {},
        canFilter = false
    )
}
