package net.paulbogdan.simplerecipe.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.paulbogdan.simplerecipe.R
import net.paulbogdan.simplerecipe.ui.theme.SimpleTheme

@Composable
fun HealthLabel(
    label: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(
            color = SimpleTheme.colors.green,
            shape = RoundedCornerShape(50.dp)
        ),
        contentAlignment = Alignment.Center

        ) {
        Text(
            text = label,
            fontFamily = FontFamily(Font(R.font.georama_regular)),
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = SimpleTheme.colors.white,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Preview
@Composable
fun HealthLabelRoundPreview(){
    HealthLabel(label = "Vegetarian")
}