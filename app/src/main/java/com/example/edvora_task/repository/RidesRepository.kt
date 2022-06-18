package com.example.edvora_task.repository

import com.example.edvora_task.api.RetrofitInstance

class RidesRepository() {
    suspend fun getAllRides() =
        RetrofitInstance.api.getAllRides()

    suspend fun getAllUsers() =
        RetrofitInstance.api.getAllUsers()
}