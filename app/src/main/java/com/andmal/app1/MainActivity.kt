package com.andmal.app1

import android.graphics.ColorSpace.Rgb
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andmal.app1.ui.theme.App1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Porkie", from = "Susl")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, from: String, modifier: Modifier = Modifier) {
    Column(
        Modifier.padding(10.dp)
            .background(Color(134,25,126)),

    ) {
        Row {
            Text(
                text = "Happy Birthday, $name!",
                fontSize = TextUnit(11.0F, TextUnitType.Em),
                lineHeight = 60.sp
            )
        }
        Row(Modifier.background(Color.Blue)) {
            Text(
                color = Color.White,
                fontSize = 35.sp,
                text = "From $from",
                modifier = modifier
            )
        }
        Row {
            Button(onClick = {

            }) {
                Text(text = "Click")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    App1Theme {
        Greeting("Android", "Test User")
    }
}