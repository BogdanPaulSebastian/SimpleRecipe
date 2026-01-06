package net.paulbogdan.simplerecipe.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.paulbogdan.simplerecipe.R
import net.paulbogdan.simplerecipe.ui.theme.Georama
import net.paulbogdan.simplerecipe.ui.theme.SimpleTheme

@Composable
fun NavigationListItem(
    @DrawableRes iconResId: Int,
    @StringRes titleResId: Int,
    @StringRes captionResId: Int? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .height(82.dp)
            .clickable(onClick = onClick, role = Role.Button)
            .padding(end = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Card(
            shape = RoundedCornerShape(20.dp),
            backgroundColor = SimpleTheme.colors.pageBackground
        ) {
            Image(
                modifier = Modifier.padding(7.dp),
                painter = painterResource(id = iconResId),
                contentDescription = null,
                colorFilter = ColorFilter.tint(SimpleTheme.colors.almostBlack)
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            Text(
                text = stringResource(id = titleResId),
                fontFamily = Georama,
                fontSize = 16.sp,
                color = SimpleTheme.colors.almostBlack
            )
            if (captionResId != null) {
                Text(
                    text = stringResource(id = captionResId),
                    fontFamily = Georama,
                    fontSize = 12.sp,
                    color = SimpleTheme.colors.deepGray
                )
            }
        }
        Box {
            Image(
                modifier = Modifier
                    .rotate(270f)
                    .size(36.dp),
                painter = painterResource(id = R.drawable.ic_arrow_circle),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(SimpleTheme.colors.green)
            )
        }
    }
}


@Composable
fun ToggleListItem(
    @DrawableRes iconResId: Int,
    @StringRes titleResId: Int,
    @StringRes captionResId: Int? = null,
    isChecked: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .height(82.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            backgroundColor = SimpleTheme.colors.pageBackground
        ) {
            Image(
                modifier = Modifier.padding(7.dp),
                painter = painterResource(id = iconResId),
                contentDescription = null,
                colorFilter = ColorFilter.tint(SimpleTheme.colors.almostBlack)
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            Text(
                text = stringResource(id = titleResId),
                fontFamily = Georama,
                fontSize = 16.sp,
                color = SimpleTheme.colors.almostBlack
            )
            if (captionResId != null) {
                Text(
                    text = stringResource(id = captionResId),
                    fontFamily = Georama,
                    fontSize = 12.sp,
                    color = SimpleTheme.colors.deepGray
                )
            }
        }
        Switch(
            checked = isChecked,
            onCheckedChange = { onClick() },
            colors = SwitchDefaults.colors(
                checkedThumbColor = SimpleTheme.colors.white,
                checkedTrackColor = SimpleTheme.colors.green,
                uncheckedThumbColor = SimpleTheme.colors.white,
                uncheckedTrackColor = SimpleTheme.colors.deepGray
            )
        )
    }

}

@Preview
@Composable
fun PreviewItem() {
    ToggleListItem(
        iconResId = R.drawable.filter_inactive_icon,
        titleResId = R.string.confirm_delete,
        isChecked = true,
        onClick = {})
}

@Preview
@Composable
fun PreviewItemTwo() {
    NavigationListItem(
        iconResId = R.drawable.filter_inactive_icon,
        titleResId = R.string.confirm_delete,
        onClick = {})
}
