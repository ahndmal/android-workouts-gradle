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
import androidx.compose.foundation.Image
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andmal.app1.ui.theme.App1Theme
import kotlinx.coroutines.launch


const val workoutsUrl =
    "https://us-central1-workouts-app2.cloudfunctions.net/go_gcp_cfunc_mongo_workouts"

class MainActivity : ComponentActivity() {
    private val workoutRepo: WorkoutRepo = WorkoutRepo()
    private val workouts = mutableListOf<Workout>()

    override fun onStart() {
        super.onStart()
        Log.d(">> start", "start")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val model: MainViewModel by viewModels()

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
                                "1L", "", 2, "", "",
                                "", "", 2, 2023, "", ""
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
                    Text(
                        text = workout.id,
                        fontSize = TextUnit(3.0F, TextUnitType.Em),
                        lineHeight = 20.sp
                    )

                    Text(
                        text = workout.day.toString(),
                        fontSize = TextUnit(3.0F, TextUnitType.Em),
                        lineHeight = 20.sp
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
                        "1L", "", 2, "", "",
                        "", "", 2, 2023, "", ""
                    )
                )
            )
        }
    }
}

class MainViewModel constructor(
    savedStateHandle: SavedStateHandle,
    workoutRepo: WorkoutRepo
) : ViewModel() {
    private val workoutId: String =
        savedStateHandle["wid"] ?: throw IllegalArgumentException("Missing workout id")

    private val _refresh = MutableLiveData<Boolean>()
    val refresh = _refresh as LiveData<Boolean>

    private val _workouts = MutableLiveData<MutableList<Workout>>()
    val workouts = _workouts as LiveData<MutableList<Workout>>

    init {
        viewModelScope.launch {
            try {
                _refresh.value = true
                val workouts = workoutRepo.getWorkouts()

                Log.d(">>>>>> workouts", workouts.toString())

                _workouts.value = workouts
            } catch (error: Exception) {
                // Show error message to user
            }

        }
    }

    fun refresh() {
        viewModelScope.launch {
            if (_refresh.value == true) _refresh.value = false else _refresh.value = true
        }
    }

}

