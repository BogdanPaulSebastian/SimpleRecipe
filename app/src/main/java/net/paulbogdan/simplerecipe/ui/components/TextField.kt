package net.paulbogdan.simplerecipe.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import net.paulbogdan.simplerecipe.R
import net.paulbogdan.simplerecipe.ui.theme.Georama
import net.paulbogdan.simplerecipe.ui.theme.SimpleTheme
import net.paulbogdan.simplerecipe.ui.theme.Typography

@Composable
fun SimpleBasicTextField(
    value: String,
    topText: String,
    bottomText: String,
    onSearch: KeyboardActionScope.() -> Unit,
    onValueChange: (String) -> Unit,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(fontFamily = Georama, fontSize = 18.sp),
        decorationBox = { innerTextField ->
            if (value.isEmpty()) {
                Column(
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = topText,
                        fontFamily = FontFamily(Font(R.font.georama_regular)),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = SimpleTheme.colors.textColor,
                    )
                    Text(
                        text = bottomText,
                        style = Typography.body1,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = SimpleTheme.colors.captionColor
                    )
                }
            } else {
                innerTextField()
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(onSearch = onSearch),
    )
}

@Preview(showBackground = true)
@Composable
fun TextfieldPreview() {
    SimpleBasicTextField(
        value = "",
        topText = stringResource(id = R.string.search_recipes),
        bottomText = stringResource(id = R.string.search_ingredients),
        onSearch = {}
    ) {}
}
