package com.fosents.kotlinvendingmachine.ui.alert

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.fosents.kotlinvendingmachine.R
import com.fosents.kotlinvendingmachine.ui.theme.BackgroundColor
import com.fosents.kotlinvendingmachine.ui.theme.Typography

@Composable
fun NoConnectionAlert(onClick: () -> Unit) {
    AnimatedAlert {
        ContentNoConn(it, onClick)
    }
}

@Composable
fun ContentNoConn(anims: List<Float>, onClick: () -> Unit) {
    Dialog(
        onDismissRequest = {}
    ) {
        Surface(
            modifier = Modifier
                .alpha(alpha = anims[0]),
            shape = RoundedCornerShape(10),
            color = BackgroundColor
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    style = Typography.headlineSmall,
                    text = stringResource(id = R.string.no_internet),
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .alpha(alpha = anims[1]),
                    color = Color.White
                )
                Text(
                    style = Typography.bodyLarge,
                    text = stringResource(id = R.string.check_connection),
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .alpha(alpha = anims[2]),
                    color = Color.White
                )
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Button(
                        elevation =  ButtonDefaults.buttonElevation(
                            defaultElevation = 10.dp,
                            pressedElevation = 15.dp,
                            disabledElevation = 0.dp
                        ),
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .alpha(alpha = anims[3]),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        ),
                        onClick = onClick,
                    ) {
                        Text(
                            text = "RETRY",
                            color = BackgroundColor
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewNoConnectionAlert() {
    ContentNoConn(listOf(1f, 1f, 1f, 1f)) {}
}
