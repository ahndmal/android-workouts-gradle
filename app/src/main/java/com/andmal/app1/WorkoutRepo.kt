package com.andmal.app1

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import org.json.JSONArray

class WorkoutRepo {
    suspend fun getWorkouts(): MutableList<Workout> {
        val workouts: MutableList<Workout> = mutableListOf<Workout>()

        val client = HttpClient(CIO)
        val response: HttpResponse = client.get(workoutsUrl)
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