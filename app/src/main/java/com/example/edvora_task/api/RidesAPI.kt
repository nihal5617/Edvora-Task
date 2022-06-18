package com.example.edvora_task.api

import com.example.edvora_task.model.Rides
import com.example.edvora_task.model.User
import retrofit2.Response
import retrofit2.http.GET

interface RidesAPI {
    @GET("/rides")
    suspend fun getAllRides() : Response<Rides>

    @GET("/user")
    suspend fun getAllUsers() : Response<User>
}