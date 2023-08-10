package com.andmal.app1

import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andmal.app1.api.WorkoutRepo
import com.andmal.app1.data.Workout
import com.andmal.app1.ui.theme.App1Theme
import com.andmal.app1.viewmodels.WorkoutsViewModel
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private val workoutRepo: WorkoutRepo = WorkoutRepo()
    private val workouts = mutableListOf<Workout>()

    val viewModel: WorkoutsViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        val state = lifecycle.currentState
        Log.d(">> start", "start")
        Log.d(">> currentState", state.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        setContent {
            App1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    WorkoutData(
                        workouts = listOf(
                            Workout(
                                "1L", "", 2,
                                "lorem ipsum...",
                                "",
                                "Monday",
                                "", 2, 2023, "", ""
                            )
                        )
                    )
                }
            }
        }

        val workObserver = Observer<MutableList<Workout>> { nWorkouts ->
            workouts.removeAll(nWorkouts)
            workouts.addAll(nWorkouts)
        }

//        model.workouts.observe(this, workObserver)

    }

    @Composable
    fun WorkoutData(workouts: List<Workout>, modifier: Modifier = Modifier) {
        Column(
            Modifier
                .padding(10.dp)
                .background(Color(160, 221, 196, 255)),

            ) {

            Row {
                Text(
                    text = "Workouts",
                    fontSize = TextUnit(7.0F, TextUnitType.Em),
                    modifier = Modifier.padding(7.dp)
                )
            }

            workouts.forEach { workout ->
                Row(Modifier.padding(8.dp)) {
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .width(200.dp)
                            .height(100.dp)
                            .background(Color.Blue)
                    ) {
                        Text(
                            text = workout.id,
                            fontSize = TextUnit(3.0F, TextUnitType.Em),
                            lineHeight = 20.sp
                        )
                        Text(
                            text = workout.comments.toString(),
                            fontSize = TextUnit(3.0F, TextUnitType.Em),
                            lineHeight = 20.sp
                        )
                    }

                    Text(
                        text = "Text after box",
                        fontSize = TextUnit(4.0F, TextUnitType.Em),
                        lineHeight = 18.sp
                    )
                }
            }

            Row {
                Button(onClick = {

                    //

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
            WorkoutData(
                workouts = listOf(
                    Workout(
                        "1L", "12",
                        2,
                        "lorem ipsum...",
                        "",
                        "Monday", "", 2, 2023, "", ""
                    )
                )
            )
        }
    }
}


class MyObserver : DefaultLifecycleObserver {
    override fun onResume(owner: LifecycleOwner) {
        Log.d("onResume", "onResume")
    }

    override fun onStart(owner: LifecycleOwner) {
        Log.d("onStart", "onStart")
    }

    override fun onPause(owner: LifecycleOwner) {
        Log.d("onPause", "onPause")
    }
}