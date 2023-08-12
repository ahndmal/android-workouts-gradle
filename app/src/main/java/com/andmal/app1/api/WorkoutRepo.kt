package com.andmal.app1.api

import android.util.Log
import com.andmal.app1.data.Workout
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import org.json.JSONArray
import javax.inject.Inject

class WorkoutRepo @Inject constructor() {
    private val BASE_URL =
        "https://us-central1-workouts-app2.cloudfunctions.net/go_gcp_cfunc_mongo_workouts"
    suspend fun getWorkouts(): MutableList<Workout> {
        val workouts: MutableList<Workout> = mutableListOf<Workout>()

        val client = HttpClient(CIO)
        val response: HttpResponse = client.get(BASE_URL)
        Log.d(">>>>> Ktor: response", response.status.toString())

        val body = response.bodyAsText()

        val workoutsJsonData = JSONArray(body)

        for (a in 0 until workoutsJsonData.length()) {
            val workoutJson = workoutsJsonData.getJSONObject(a)
            val workout = Workout(
                workoutJson.getString("_id"),
                workoutJson.getString("record"),
                workoutJson.getInt("sets"),
                workoutJson.getString("comments"),
                workoutJson.getString("creation_date"),
                workoutJson.getString("day"),
                workoutJson.getString("month"),
                workoutJson.getInt("week"),
                workoutJson.getInt("year"),
                workoutJson.getString("workout_type"),
                workoutJson.getString("workout_date"),
            )
            workouts.add(workout)
        }

        return workouts
    }
}