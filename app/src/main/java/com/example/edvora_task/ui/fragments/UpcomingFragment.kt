package com.example.edvora_task.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.edvora_task.R
import com.example.edvora_task.adapter.RidesAdapter
import com.example.edvora_task.model.Rides
import com.example.edvora_task.ui.MainActivity
import com.example.edvora_task.ui.RidesViewModel
import com.example.edvora_task.util.Resource
import kotlinx.android.synthetic.main.fragment_nearest.*

class UpcomingFragment : Fragment(R.layout.fragment_upcoming) {

    lateinit var viewModel: RidesViewModel
    lateinit var ridesAdapter: RidesAdapter
    val TAG = "UpcomingFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as MainActivity).viewModel
        setUpRecyclerView()

        viewModel.rides.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Success->{
                    hideProgressBar()
                    response.data?.let { ridesResponse ->
                        val ridesResponseNew=getRidesResponse(ridesResponse)
                        ridesAdapter.differ.submitList(ridesResponseNew)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG,"An error occurred : $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }
    private fun getRidesResponse(rides: Rides):Rides{
//        val current = LocalDate.now()
//        val formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
//        val formatted = current.format(formatter)
        val formatted = 20220619
        val myList = Rides()
        Log.e("Here", formatted.toString())
        for(i in rides.iterator()){
            val new = i.date!!.substring(0,10)
            val n = new.split('/')
            val str = n[2]+n[0]+n[1]
            val num = str.toInt()
            if(num > formatted){
                myList.add(i)
            }
        }
        return myList
    }
    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }
    private fun setUpRecyclerView(){
        ridesAdapter = RidesAdapter()
        rvRides.apply{
            adapter=ridesAdapter
            layoutManager= LinearLayoutManager(activity)
        }
    }

}