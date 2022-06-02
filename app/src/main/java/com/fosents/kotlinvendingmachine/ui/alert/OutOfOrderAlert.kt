package com.fosents.kotlinvendingmachine.ui.alert

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.fosents.kotlinvendingmachine.R
import com.fosents.kotlinvendingmachine.ui.theme.Typography
import com.fosents.kotlinvendingmachine.ui.theme.backgroundColor

@Composable
fun ShowOutOfOrderAlert(onClick: () -> Unit) {
    Dialog(
        onDismissRequest = {}
    ) {
        Surface(
            shape = RoundedCornerShape(10),
            color = MaterialTheme.colors.backgroundColor
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    style = Typography.h5,
                    text = stringResource(id = R.string.alert_title_out_of_order),
                    modifier = Modifier.padding(bottom = 10.dp),
                    color = Color.White
                )
                Text(
                    style = Typography.subtitle1,
                    text = stringResource(id = R.string.alert_text_enter_maintenance),
                    modifier = Modifier.padding(bottom = 10.dp),
                    color = Color.White
                )
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Button(
                        elevation =  ButtonDefaults.elevation(
                            defaultElevation = 10.dp,
                            pressedElevation = 15.dp,
                            disabledElevation = 0.dp
                        ),
                        modifier = Modifier.padding(end = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White
                        ),
                        onClick = onClick,
                    ) {
                        Text(
                            text = "OK",
                            color = MaterialTheme.colors.backgroundColor
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewOutOfOrderAlert() {
    ShowOutOfOrderAlert {}
}
