package com.example.edvora_task.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edvora_task.model.Rides
import com.example.edvora_task.repository.RidesRepository
import com.example.edvora_task.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class RidesViewModel(
    val ridesRepository: RidesRepository
) : ViewModel(){
    val rides:MutableLiveData<Resource<Rides>> = MutableLiveData()

    init {
        getAllRides()
    }

    fun getAllRides()=viewModelScope.launch {
        rides.postValue(Resource.Loading())
        val response = ridesRepository.getAllRides()
        rides.postValue(handleRidesResponse(response))
    }
    fun handleRidesResponse(response: Response<Rides>): Resource<Rides>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}