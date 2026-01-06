package net.paulbogdan.simplerecipe.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import net.paulbogdan.simplerecipe.R
import net.paulbogdan.simplerecipe.ui.theme.Georama
import net.paulbogdan.simplerecipe.ui.theme.SimpleTheme

@Composable
fun DisclaimerDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Card(
            shape = RoundedCornerShape(15.dp),
            backgroundColor = SimpleTheme.colors.pageBackground
        ) {
            Column(
                Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.disclaimer),
                    fontFamily = Georama,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = SimpleTheme.colors.almostBlack,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = stringResource(id = R.string.disclaimer_body),
                    fontFamily = Georama,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = SimpleTheme.colors.almostBlack,
                    modifier = Modifier.padding(bottom = 8.dp),
                    textAlign = TextAlign.Center
                )

                Button(
                    onClick = {
                        onConfirm()
                    },
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = SimpleTheme.colors.green),
                    modifier = Modifier
                        .padding(top = 8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.i_understand),
                        fontFamily = FontFamily(Font(R.font.georama_regular)),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = SimpleTheme.colors.white,
                        textAlign = TextAlign.Start,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                    )
                }

            }
        }
    }
}

@Preview
@Composable
fun DisclaimerPreview(){
    DisclaimerDialog(onDismissRequest = { /*TODO*/ }) {
        
    }
}