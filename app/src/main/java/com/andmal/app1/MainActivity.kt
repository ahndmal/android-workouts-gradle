package com.andmal.app1

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andmal.app1.ui.theme.App1Theme
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL


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
                    val workoutsUrl =
                        "https://us-central1-workouts-app2.cloudfunctions.net/go_gcp_cfunc_mongo_workouts"

                    val volleyQueue = Volley.newRequestQueue(this)

                    val jsonObjectRequest = JsonArrayRequest(
                        Request.Method.GET,
                        workoutsUrl,
                        null,
                        { response ->
                            val workout = response.get(0)
                            Log.d("workout", workout.toString())
                        },

                        { error ->
                            Toast.makeText(this, "Some error occurred! Cannot fetch dog image", Toast.LENGTH_LONG).show()
                            // log the error message in the error stream
                            Log.e("MainActivity", "loadDogImage error: ${error.localizedMessage}")
                        }
                    )

                    volleyQueue.add(jsonObjectRequest)

                    Greeting(workouts = listOf<String>("content", "bbb"))
                }
            }
        }
    }
}

@Composable
fun Greeting(workouts: List<String>, modifier: Modifier = Modifier) {
    Column(
        Modifier
            .padding(10.dp)
            .background(Color(134, 25, 126)),

        ) {
        workouts.forEach { workout ->
            Row {
                Text(
                    text = "$workout",
                    fontSize = TextUnit(11.0F, TextUnitType.Em),
                    lineHeight = 60.sp
                )
            }
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
        Greeting(workouts = listOf("aaa", "bbb"))
    }
}
