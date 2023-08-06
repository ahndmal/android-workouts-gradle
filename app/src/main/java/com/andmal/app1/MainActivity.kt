package com.andmal.app1

import android.os.Bundle
import android.os.StrictMode
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
import com.android.volley.toolbox.Volley
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

const val workoutsUrl =
    "https://us-central1-workouts-app2.cloudfunctions.net/go_gcp_cfunc_mongo_workouts"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        setContent {
            App1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val workouts: MutableList<Workout> = mutableListOf()

                    WorkoutData(workouts = workouts)
                }
            }
        }
    }
}

@Composable
fun WorkoutData(workouts: List<Workout>, modifier: Modifier = Modifier) {
    Column(
        Modifier
            .padding(10.dp)
            .background(Color(134, 25, 126)),

        ) {
        workouts.forEach { workout ->
            Row {
                Text(
                    text = workout.id,
                    fontSize = TextUnit(11.0F, TextUnitType.Em),
                    lineHeight = 60.sp
                )
            }
        }

        Row {
            Button(onClick = {
                runBlocking {
                    launch {
                        getWorkouts()
                    }
                }

            }) {
                Text(text = "Click")
            }
        }
    }
}

private suspend fun getWorkouts(): MutableList<Workout> {
    val workouts = mutableListOf<Workout>()

    val client = HttpClient(CIO)
    val response: HttpResponse = client.get(workoutsUrl)
    Log.d(">>>>> Ktor: response", response.status.toString())


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    App1Theme {
        WorkoutData(
            workouts = listOf(
                Workout(
                    "1L", "", 2, "", "",
                    "", "", 2, 2023, "", ""
                )
            )
        )
    }
}

