package com.andmal.app1.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andmal.app1.api.WorkoutRepo
import com.andmal.app1.data.Workout
import kotlinx.coroutines.launch

class WorkoutsViewModel  constructor(
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