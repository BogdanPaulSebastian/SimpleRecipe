package net.paulbogdan.simplerecipe.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.paulbogdan.simplerecipe.ui.theme.Georama
import net.paulbogdan.simplerecipe.ui.theme.SimpleTheme

@Composable
fun SimpleDivider(
    modifier: Modifier = Modifier,
    color: Color = SimpleTheme.colors.deepGray.copy(0.5f),
    thickness: Dp = 1.dp,
    startIndent: Dp = 0.dp
) {
    Divider(
        modifier = modifier,
        color = color,
        thickness = thickness,
        startIndent = startIndent
    )
}

@Composable
fun FilterDivider(
    title: String
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Start,
            fontFamily = Georama,
            modifier = Modifier.padding(start = 8.dp),
            fontSize = 18.sp,
            color = SimpleTheme.colors.almostBlack
        )
        SimpleDivider()
    }
}

@Composable
fun DragBar(color: Color) {
    Spacer(modifier = Modifier
        .width(134.dp)
        .height(3.dp)
        .padding(horizontal = 8.dp)
        .drawBehind {
            drawLine(
                color = color,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = 3.dp.value * density,
                cap = StrokeCap.Round
            )
        })
}