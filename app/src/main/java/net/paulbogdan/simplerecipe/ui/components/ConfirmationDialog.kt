package net.paulbogdan.simplerecipe.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import net.paulbogdan.simplerecipe.R
import net.paulbogdan.simplerecipe.business.preferences.UserPrefs
import net.paulbogdan.simplerecipe.ui.theme.Georama
import net.paulbogdan.simplerecipe.ui.theme.SimpleTheme

@Composable
fun ConfirmationDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    userPrefs: UserPrefs
) {

    var checked by remember { mutableStateOf(false) }

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
                    text = stringResource(id = R.string.confirm_remove_favorite),
                    fontFamily = Georama,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = SimpleTheme.colors.almostBlack,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = checked, onCheckedChange = { checked = !checked },
                        colors = CheckboxDefaults.colors(
                            checkedColor = SimpleTheme.colors.green,
                            uncheckedColor = SimpleTheme.colors.almostBlack,
                            checkmarkColor = SimpleTheme.colors.almostBlack
                        ),
                        modifier = Modifier.size(35.dp)
                    )

                    Text(
                        text = stringResource(id = R.string.do_not_ask_again),
                        fontFamily = Georama,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = SimpleTheme.colors.almostBlack
                    )

                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(50.dp)
                ) {
                    Button(
                        onClick = {
                            if (checked) {
                                userPrefs.setShowConfirmationDialog(false)
                            } else {
                                userPrefs.setShowConfirmationDialog(true)
                            }
                            onConfirm()
                        },
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = SimpleTheme.colors.white),
                        modifier = Modifier
                            .padding(top = 8.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.remove),
                            fontFamily = FontFamily(Font(R.font.georama_regular)),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = SimpleTheme.colors.green,
                            textAlign = TextAlign.Start,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                        )
                    }

                    Button(
                        onClick = onDismissRequest,
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = SimpleTheme.colors.green),
                        modifier = Modifier
                            .padding(top = 8.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.cancel),
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
}